package com.example.betrimex.service.implementation;

import com.example.betrimex.mapper.QrDataMapper;
import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.QrDataRequest;
import com.example.betrimex.model.dto.response.BTMQrDataResponse;
import com.example.betrimex.model.dto.response.QrDataResponse;
import com.example.betrimex.repository.QrDataRepository;
import com.example.betrimex.service.AuditLogService;
import com.example.betrimex.service.QrDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.betrimex.model.QrData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.betrimex.model.Constants.*;


@Service
@RequiredArgsConstructor
@Log4j2
public class QrDataServiceImp implements QrDataService {

    private final AuditLogService auditLogService;
    private final QrDataRepository qrDataRepository;
    private final QrDataMapper qrDataMapper;

    @Value("${upload.qr.dir}")
    private String uploadDir;

    @Value("https://dev.it-cpi002-rt.cfapps.ap10.hana.ondemand.com/http/api/shipments")
    private String betrimex_api_curl;

    @Value("c2ItNzdiYTBhODQtYzJiOS00ZDExLTg4ZWYtNDFjYmY2ZjI4ODE5IWIzODh8aXQtcnQtZGV2IWI4MDpBNGFrNWpTOWFBRnpmRS93UXlrenkrRnU5RXM9")
    private String betrimex_basic_auth;

    @Override
    public Page<QrDataResponse> getQrDataByParams(QrDataRequest request, Pageable pageable) {
        Page<QrData> qrDataList = qrDataRepository.getQrDataByCreatedAtBetweenOrderByCreatedAtDesc(
             pageable, request.getFromDate(), request.getToDate()
        );

        List<QrDataResponse> auditTrailResponses = qrDataList.stream()
                .map(qrDataMapper::toQrDataResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(auditTrailResponses, pageable, qrDataList.getTotalElements());

    }

    @Override
    public void saveQrData(String qrTextJson) throws IOException, WriterException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, AUTHORIZATION_BASIC + betrimex_basic_auth);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String apiUrl = betrimex_api_curl + "?qrcode=" + qrTextJson.trim();

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException e) {
            auditLogService.saveAudit(
                    "GET_QR_DATA", "QR", "HTTP error when get QR API. QR: " + qrTextJson + ", " + e.getStatusCode() + ", " + e.getResponseBodyAsString(),
                    "ERROR", "", "", "", "", "");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to retrieve data");
        }

        String responseBody = response.getBody();
        log.info("Response from Betrimex: {}", responseBody);

        BTMQrDataResponse btmQrDataResponse = parseJsonToQrUploadDataRequest(responseBody);
        QrData qrData = parseJsonToQrData(btmQrDataResponse);
        qrData.setQrScanner(qrTextJson);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        BitMatrix bitMatrix = qrCodeWriter.encode(qrTextJson, BarcodeFormat.QR_CODE, width, height, hints);
        String newFileName = UUID.randomUUID() + ".png";
        Path qrFilePath = Paths.get(uploadDir).resolve(newFileName).toAbsolutePath();
        Files.createDirectories(qrFilePath.getParent());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrFilePath);

        qrData.setData(responseBody);
        qrData.setImagePath(qrFilePath.toString());
        qrData = qrDataRepository.save(qrData);

        auditLogService.saveAudit("GET_QR_DATA", "QR", "Get QR Data from FRM Server through API", "SUCCESS", "", qrTextJson, response.getBody(), qrData.getFactory(), "");
    }


    private BTMQrDataResponse parseJsonToQrUploadDataRequest(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, BTMQrDataResponse.class);
    }

    private QrData parseJsonToQrData(BTMQrDataResponse response) {
        QrData qrData = new QrData();
        qrData.setVehicleCard(response.getVehicleCard());
        qrData.setFactory(response.getFactory());
        qrData.setSupplierNameBTM(response.getSupplierNameBTM());
        qrData.setSupplierNameHTX(response.getSupplierNameHTX());
        qrData.setProductCode(response.getProductCode());
        qrData.setProductName(response.getProductName());
        qrData.setLotId(response.getLotId());
        qrData.setLotIdDetail(response.getLotIdDetail());
        qrData.setLotNumber(response.getLotNumber());
        qrData.setDriver1(response.getDriver1());
        qrData.setDriver2(response.getDriver2());
        qrData.setDriver3(response.getDriver3());
        qrData.setCitizenId1(response.getCitizenId1());
        qrData.setCitizenId2(response.getCitizenId2());
        qrData.setCitizenId3(response.getCitizenId3());
        qrData.setLicensePlate(response.getLicensePlate());
        qrData.setEstimatedArrivalTime(response.getEstimatedArrivalTime());
        qrData.setNote(response.getNote());
        qrData.setStatusCheckin(response.isStatusCheckin());
        qrData.setStatus(response.getStatus());

        return qrData;
    }

    @Override
    public BaseResponse getQrDataByVehicleCard(String qrText) {
        BaseResponse response = new BaseResponse();
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, AUTHORIZATION_BASIC + betrimex_basic_auth);
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            String apiUrl = betrimex_api_curl + "?qrcode=" + qrText.trim();

            ResponseEntity<JsonNode> restResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JsonNode.class);
            JsonNode rootNode = restResponse.getBody();

            if (rootNode == null) {
                response.setStatusCode(500);
                response.setMessage("Không nhận được dữ liệu JSON từ FRM");
                response.setData(null);
                return response;
            }

            if (rootNode.has("code") && rootNode.get("code").asInt() != 0) {
                String message = rootNode.has("message") ? rootNode.get("message").asText() : "Không rõ lỗi";
                response.setStatusCode(404);
                response.setMessage("Lỗi từ FRM: " + message);
                response.setData(null);
                return response;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            BTMQrDataResponse request = objectMapper.treeToValue(rootNode, BTMQrDataResponse.class);
            QrData qrData = parseJsonToQrData(request);
            qrData.setQrScanner(qrText);
            qrData.setData(objectMapper.writeValueAsString(rootNode));

            qrDataRepository.save(qrData);
            QrDataResponse qrDataResponse = qrDataMapper.toQrDataResponse(qrData);

            response.setStatusCode(200);
            response.setMessage("Thành công");
            response.setData(qrDataResponse);


            return response;

        } catch (Exception ex) {
            response.setStatusCode(500);
            response.setMessage("Lỗi xử lý: " + ex.getMessage());
            response.setData(null);
            return response;
        }
    }
}

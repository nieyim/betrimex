package com.example.betrimex.service.implementation;

import com.example.betrimex.mapper.ProductMapper;
import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.Product;
import com.example.betrimex.model.QrData;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.response.*;
import com.example.betrimex.repository.ProductRepository;
import com.example.betrimex.repository.QrDataRepository;
import com.example.betrimex.service.AuditLogService;
import com.example.betrimex.service.ConfigService;
import com.example.betrimex.service.ProductService;
import com.example.betrimex.utils.ConvertDateTime;
import com.example.betrimex.utils.GenerateLotCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.example.betrimex.model.Constants.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final QrDataRepository qrDataRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final AuditLogService auditLogService;
    private final ConfigService configService;

    @Override
    public BaseResponse saveProduct(CreateProductRequest request) {
        BaseResponse response = new BaseResponse();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION_HEADER, AUTHORIZATION_BASIC + configService.getValueByKey("BETRIMEX_BASIC_AUTH"));

        QrData qrData = qrDataRepository.findById(request.getQrData()).orElseThrow(() -> new RuntimeException("QR not found with id: " + request.getQrData()));;

        Product product = productMapper.map(request);
        product.setId(UUID.randomUUID().toString());
        product.setCompany(qrData.getFactory());

        String jsonBody = "";

        try {
            if (product.getLotId() != null) {
                product.setCountType(configService.getValueByKey("AI_COUNT"));

                RetrieveCoconutCountResponse dto = new RetrieveCoconutCountResponse();
                dto.setId(UUID.randomUUID().toString());
                dto.setMachineId(configService.getValueByKey("MC_MACHINE_NAME"));
                dto.setLotId(qrData.getLotId());
                dto.setLotIdDetail(qrData.getLotIdDetail());
                dto.setQuantity(request.getQuantity());
                dto.setStartTime(ConvertDateTime.toIso8601Z(request.getStartTime()));
                dto.setEndTime(ConvertDateTime.toIso8601Z(request.getEndTime()));
                dto.setCreatedAt(ConvertDateTime.toIso8601Z(LocalDateTime.now()));
                dto.setCountType(product.getCountType());

                ObjectMapper mapper = new ObjectMapper();
                jsonBody = mapper.writeValueAsString(dto);
                HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

//                ResponseEntity<String> restResponse = restTemplate.exchange(
//                        betrimex_api_curl,
//                        HttpMethod.POST,
//                        entity,
//                        String.class
//                );
//
//                log.info("✅ Gửi thành công, phản hồi: {}", restResponse.getBody());
//                auditLogService.saveAudit("SEND_DATA_TO_FRM", "COCONUT_LOT", "", "SUCCESS", "current", jsonBody, "", product.getCompany(), product.getMachineId());

                product.setIsSync(true);
            } else {
                long countToday = productRepository.countRedCardLotToday();
                product.setRedCardLot(GenerateLotCode.generateRedCardLot(countToday));
                product.setCountType(configService.getValueByKey("MANUAL_INPUT"));

//                Boolean isSendToCloud = cloudInsertService.insertCoconutRedTagBatch(product);

//                if (isSendToCloud) {
//                    product.setIsSendToCloud(true);
//                    product.setCountType(configService.getValueByKey("MANUAL_INPUT"));
//                }
            }

            productRepository.save(product);

            response.setStatusCode(200);
            response.setMessage("Tạo sản phẩm thành công");
            response.setData(product.getId());

        } catch (HttpStatusCodeException ex) {
            log.error("❌ Gửi thất bại: " + ex.getStatusCode());
            log.error("Lỗi chi tiết: " + ex.getResponseBodyAsString());
            product.setIsSync(false);
            auditLogService.saveAudit("SEND_DATA_TO_FRM", "PRODUCT", "", "FAILED", "current", jsonBody, "", product.getCompany(), product.getMachineId());

            response.setStatusCode(500);
            response.setMessage("Lỗi gửi dữ liệu sang BTM: " + ex.getMessage());
            response.setData(null);
        } catch (Exception e) {
            e.printStackTrace();
            product.setIsSync(false);
            auditLogService.saveAudit("SEND_DATA_TO_FRM", "PRODUCT", "", "FAILED", "current", jsonBody, "", product.getCompany(), product.getMachineId());

            response.setStatusCode(500);
            response.setMessage("Lỗi hệ thống: " + e.getMessage());
            response.setData(null);
        }

        return response;
    }

    @Override
    public ProductResponse findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse findByLotId(String id) {
        Product product = productRepository.findByLotId(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductInfoByWeekResponse getProductInfoByWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        LocalDateTime from = startOfWeek.atStartOfDay();
        LocalDateTime to = endOfWeek.atTime(LocalTime.MAX);

        List<Product> products = productRepository.getProductsByCreatedAtBetweenOrderByCreatedAt(from, to);
        int totalProduct = products.size();

        Map<DayOfWeek, Double> weightByDay = new EnumMap<>(DayOfWeek.class);

        for (Product product : products) {
            DayOfWeek dayOfWeek = product.getCreatedAt().getDayOfWeek();
            double weight = product.getQuantity() != null ? product.getQuantity() : 0.0;
            weightByDay.put(dayOfWeek, weightByDay.getOrDefault(dayOfWeek, 0.0) + weight);
        }

        List<DayOfWeek> weekDays = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        List<String> labels = weekDays.stream()
                .map(DayOfWeek::name)
                .map(String::toLowerCase)
                .map(label -> label.substring(0, 1).toUpperCase() + label.substring(1))
                .toList();

        List<Double> data = weekDays.stream()
                .map(day -> weightByDay.getOrDefault(day, 0.0))
                .toList();

        ProductInfoByWeekResponse response = new ProductInfoByWeekResponse();
        response.setTotalProduct(totalProduct);
        response.setLabels(labels);
        response.setData(data);

        return response;
    }

    @Override
    public ProductInfoByYearResponse getProductInfoByYear() {
        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);
        LocalDate endOfYear = now.withDayOfYear(now.lengthOfYear());

        LocalDateTime from = startOfYear.atStartOfDay();
        LocalDateTime to = endOfYear.atTime(LocalTime.MAX);

        List<Product> products = productRepository.getProductsByCreatedAtBetweenOrderByCreatedAt(from, to);

        Map<Integer, Double> monthlyWeightMap = new HashMap<>();
        Map<Integer, Integer> monthlyProductCountMap = new HashMap<>();

        for (Product product : products) {
            int month = product.getCreatedAt().getMonthValue(); // 1 - 12
            double weight = 0;
            try {
                weight = product.getQuantity();
            } catch (Exception ignored) {
            }

            monthlyWeightMap.put(month, monthlyWeightMap.getOrDefault(month, 0.0) + weight);
            monthlyProductCountMap.put(month, monthlyProductCountMap.getOrDefault(month, 0) + 1);
        }

        List<String> labels = List.of(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        List<Double> weightData = new ArrayList<>();
        int totalProduct = 0;

        for (int month = 1; month <= 12; month++) {
            weightData.add(monthlyWeightMap.getOrDefault(month, 0.0));
            totalProduct += monthlyProductCountMap.getOrDefault(month, 0);
        }

        ProductInfoByYearResponse response = new ProductInfoByYearResponse();
        response.setTotalProduct(totalProduct);
        response.setLabels(labels);
        response.setData(weightData);

        return response;
    }

    @Override
    public ProductInfoByMonthResponse getProductInfoByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

        LocalDateTime from = firstDay.atStartOfDay();
        LocalDateTime to = lastDay.atTime(LocalTime.MAX);

        List<Product> products = productRepository.getProductsByCreatedAtBetweenOrderByCreatedAt(from, to);

        Map<Integer, Double> weightByDay = new HashMap<>();
        Map<Integer, Integer> productCountByDay = new HashMap<>();

        for (Product product : products) {
            int day = product.getCreatedAt().getDayOfMonth();
            double weight = 0;
            try {
                weight = product.getQuantity();
            } catch (Exception ignored) {
            }

            weightByDay.put(day, weightByDay.getOrDefault(day, 0.0) + weight);
            productCountByDay.put(day, productCountByDay.getOrDefault(day, 0) + 1);
        }

        List<String> labels = new ArrayList<>();
        List<Double> weightData = new ArrayList<>();
        int totalProduct = 0;

        int daysInMonth = now.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            labels.add(String.format("%02d", day));
            weightData.add(weightByDay.getOrDefault(day, 0.0));
            totalProduct += productCountByDay.getOrDefault(day, 0);
        }

        ProductInfoByMonthResponse response = new ProductInfoByMonthResponse();
        response.setLabels(labels);
        response.setData(weightData);
        response.setTotalProduct(totalProduct);

        return response;
    }
}

package com.example.betrimex.controller;

import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.QrDataRequest;
import com.example.betrimex.model.dto.response.QrDataResponse;
import com.example.betrimex.service.QrDataService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/qr")
@RequiredArgsConstructor
public class QrDataController {
    private final QrDataService qrDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> getQrData(
            @RequestParam("qrTextJson") String qrTextJson) throws IOException, WriterException {
        qrDataService.saveQrData(qrTextJson);
        return ResponseEntity.ok("Upload thành công");
    }

    @PostMapping("/search-params")
    public Page<QrDataResponse> findByParams(@RequestBody QrDataRequest request, Pageable pageable) {
        return qrDataService.getQrDataByParams(request, pageable);
    }

    // visualize
    @PostMapping("/upload-visualization")
    public ResponseEntity<BaseResponse> getQrDataVisualization(@RequestParam("qrTextJson") String qrTextJson) {
        BaseResponse response = qrDataService.getQrDataByVehicleCard(qrTextJson);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

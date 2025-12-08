package com.example.betrimex.service;
import com.example.betrimex.model.dto.request.QrDataRequest;
import com.example.betrimex.model.dto.response.QrDataResponse;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.betrimex.model.BaseResponse;

import java.io.IOException;

public interface QrDataService {
    void saveQrData(String qrTextJson) throws IOException, WriterException;
    Page<QrDataResponse> getQrDataByParams(QrDataRequest request, Pageable pageable);
    BaseResponse getQrDataByVehicleCard(String qrData) ;
}

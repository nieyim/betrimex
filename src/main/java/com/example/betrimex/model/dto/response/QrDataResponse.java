package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QrDataResponse {
    private Long id;

    private String vehicleCard;

    private String qrScanner;

    private String supplierNameBTM;

    private String supplierNameHTX;

    private String productCode;

    private String productName;

    private String lotId;

    private String lotIdDetail;

    private String lotNumber;

    private String driver1;

    private String driver2;

    private String driver3;

    private String citizenId1;

    private String citizenId2;

    private String citizenId3;

    private String vehicleId;

    private String licensePlate;

    private String estimatedArrivalTime;

    private String note;

    private boolean statusCheckin;

    private String status;

    private boolean isSendAI;

    protected LocalDateTime createdAt;
}

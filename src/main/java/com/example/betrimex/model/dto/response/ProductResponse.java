package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {

    private String id;

    private String company;

    private String machineId;

    private String lotId;

    private String lotIdDetail;

    private String lotNumber;

    private String redCardLot;

    private String vehiclePlate;

    private Integer quantity;

    private String countType;

    private Boolean isSync = false;

    private String videoPath;

    private String videoTransferPath;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String warehouseStaff;

    private String status;

    private Boolean isSendToCloud = false;

    private QrDataResponse qrData;

    protected LocalDateTime createdAt;
}

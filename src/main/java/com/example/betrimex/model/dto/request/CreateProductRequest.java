package com.example.betrimex.model.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateProductRequest {

    private String machineId;

    private String lotId;

    private String factory;

    private String lotIdDetail;

    private String lotNumber;

    private String redCardLot;

    private String vehiclePlate;

    private Integer quantity;

    private String videoPath;

    private String videoTransferPath;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private String warehouseStaff;

    private Long qrData;
}

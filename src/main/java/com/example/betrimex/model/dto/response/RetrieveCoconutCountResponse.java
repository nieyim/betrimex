package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RetrieveCoconutCountResponse {

    private String id;

    private String machineId;

    private String lotId;

    private String lotIdDetail;

    private Integer quantity;

    private String startTime;

    private String endTime;

    private String createdAt;

    private String countType;
}

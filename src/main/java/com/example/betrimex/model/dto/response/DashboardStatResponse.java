package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStatResponse {
    private Integer totalProductToday;

    private Integer totalProductQuantity;

    private Integer totalQRScan;

    private Integer totalUser;

}

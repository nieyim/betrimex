package com.example.betrimex.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BTMQrDataResponse {

    @JsonProperty("msm_ma")
    private String vehicleCard;

    @JsonProperty("msm_nhamay")
    private String factory;

    @JsonProperty("msm_vendoridname")
    private String supplierNameBTM;

    @JsonProperty("msm_vendorhtxidname")
    private String supplierNameHTX;

    @JsonProperty("msm_masp")
    private String productCode;

    @JsonProperty("msm_sanphamidname")
    private String productName;

    @JsonProperty("_msm_phieugiaonhanid_value")
    private String lotId;

    @JsonProperty("_msm_chitietphieugiaonhanid_value")
    private String lotIdDetail;

    @JsonProperty("msm_maphieugiaonhan")
    private String lotNumber;

    @JsonProperty("msm_taixe1")
    private String driver1;

    @JsonProperty("msm_taixe2")
    private String driver2;

    @JsonProperty("msm_taixe3")
    private String driver3;

    @JsonProperty("msm_cccd1")
    private String citizenId1;

    @JsonProperty("msm_cccd2")
    private String citizenId2;

    @JsonProperty("msm_cccd3")
    private String citizenId3;

    @JsonProperty("msm_biensoxe")
    private String licensePlate;

    @JsonProperty("msm_ngayvanchuyen")
    private String estimatedArrivalTime;

    @JsonProperty("msm_ghichu")
    private String note;

    @Column(name = "msm_dacheckin")
    private boolean statusCheckin;

    @JsonProperty("statuscode")
    private String status;
}
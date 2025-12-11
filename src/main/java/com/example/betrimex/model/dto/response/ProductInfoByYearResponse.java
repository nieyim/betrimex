package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductInfoByYearResponse {

    private int totalProduct;

    private List<String> labels;

    private List<Double> data;
}


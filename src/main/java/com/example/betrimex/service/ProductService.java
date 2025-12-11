package com.example.betrimex.service;

import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.response.ProductInfoByMonthResponse;
import com.example.betrimex.model.dto.response.ProductInfoByWeekResponse;
import com.example.betrimex.model.dto.response.ProductInfoByYearResponse;
import com.example.betrimex.model.dto.response.ProductResponse;

public interface ProductService {
    BaseResponse saveProduct(CreateProductRequest request);

    ProductResponse findById(String id);

    ProductResponse findByLotId(String id);

    ProductInfoByWeekResponse getProductInfoByWeek();

    ProductInfoByYearResponse getProductInfoByYear();

    ProductInfoByMonthResponse getProductInfoByMonth();
}

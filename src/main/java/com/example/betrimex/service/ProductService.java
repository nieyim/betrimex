package com.example.betrimex.service;

import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.request.ProductRequest;
import com.example.betrimex.model.dto.response.ProductInfoByMonthResponse;
import com.example.betrimex.model.dto.response.ProductInfoByWeekResponse;
import com.example.betrimex.model.dto.response.ProductInfoByYearResponse;
import com.example.betrimex.model.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

public interface ProductService {
    BaseResponse saveProduct(CreateProductRequest request);

    Page<ProductResponse> getProductByParams(ProductRequest request, Pageable pageable);

    ProductResponse findById(String id);

    ProductResponse findByLotId(String id);

    ProductInfoByWeekResponse getProductInfoByWeek();

    ProductInfoByYearResponse getProductInfoByYear();

    ProductInfoByMonthResponse getProductInfoByMonth();

    Map<String, Object> loadDataPDF(String id);

    Map<String, Object> loadData(LocalDateTime startDate, LocalDateTime endDate);
}

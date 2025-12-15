package com.example.betrimex.controller;


import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.request.ProductRequest;
import com.example.betrimex.model.dto.response.ProductInfoByMonthResponse;
import com.example.betrimex.model.dto.response.ProductInfoByWeekResponse;
import com.example.betrimex.model.dto.response.ProductInfoByYearResponse;
import com.example.betrimex.model.dto.response.ProductResponse;
import com.example.betrimex.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/search-params")
    public Page<ProductResponse> findByParams(@RequestBody ProductRequest request, Pageable pageable) {
        return productService.getProductByParams(request, pageable);
    }


    @PostMapping
    public ResponseEntity<BaseResponse> createProduct(@RequestBody CreateProductRequest request) {
        BaseResponse response = productService.saveProduct(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/lots/{id}")
    public ResponseEntity<ProductResponse> getProductByLotId(@PathVariable String id) {
        return ResponseEntity.ok(productService.findByLotId(id));
    }

    @PostMapping("/get-product-by-week")
    public ProductInfoByWeekResponse getProductInfoByWeek() {
        return productService.getProductInfoByWeek();
    }

    @PostMapping("/get-product-by-year")
    public ProductInfoByYearResponse getProductInfoByYear() {
        return productService.getProductInfoByYear();
    }

    @PostMapping("/get-product-by-month")
    public ProductInfoByMonthResponse getProductInfoByMonth() {
        return productService.getProductInfoByMonth();
    }
}

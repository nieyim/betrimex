package com.example.betrimex.controller;


import com.example.betrimex.model.BaseResponse;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.request.ProductRequest;
import com.example.betrimex.model.dto.response.ProductInfoByMonthResponse;
import com.example.betrimex.model.dto.response.ProductInfoByWeekResponse;
import com.example.betrimex.model.dto.response.ProductInfoByYearResponse;
import com.example.betrimex.model.dto.response.ProductResponse;
import com.example.betrimex.service.ExportFileService;
import com.example.betrimex.service.ProductService;
import com.example.betrimex.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static com.example.betrimex.model.Constants.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ConfigService configService;
    private final ProductService productService;
    private final ExportFileService exportFileService;

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

    @GetMapping("/printPDF/{id}")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable String id) {
        var parameters = productService.loadDataPDF(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename(PDF_REPORT)
                        .build()
        );
        var pdfBytes = exportFileService.exportPdfReport(configService.getValueByKey("HISTORY_DETAIL_LOT_TEMPLATE_PATH"), parameters);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/printExcel")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody ProductRequest request) {
        var parameters = productService.loadData(request.getFromDate(), request.getToDate());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename(EXCEL_REPORT)
                        .build()
        );

        var excelBytes = exportFileService.exportExcelReport(configService.getValueByKey("HISTORY_LOT_TEMPLATE_PATH"), parameters);

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
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

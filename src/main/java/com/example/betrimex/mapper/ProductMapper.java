package com.example.betrimex.mapper;

import com.example.betrimex.model.Product;
import com.example.betrimex.model.dto.request.CreateProductRequest;
import com.example.betrimex.model.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    @Mapping(source = "qrData", target = "qrData.id")
    Product map(CreateProductRequest request);
}

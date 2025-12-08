package com.example.betrimex.mapper;


import com.example.betrimex.model.QrData;
import com.example.betrimex.model.dto.response.QrDataResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QrDataMapper {
    QrDataResponse toQrDataResponse(QrData product);

//    QrData toQrData(CreateQrDataRequest request);

    QrData mapQrData(Long id);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void update(UpdateQrDataRequest userInfoRequest, @MappingTarget QrData user);
}

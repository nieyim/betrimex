package com.example.betrimex.mapper;

import com.example.betrimex.model.AuditLog;
import com.example.betrimex.model.dto.response.AuditLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuditLogMapper {
    AuditLogResponse toAuditLogResponse(AuditLog auditLog);
}
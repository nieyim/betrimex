package com.example.betrimex.service;
import com.example.betrimex.model.dto.response.AuditLogResponse;
import com.example.betrimex.model.dto.request.AuditLogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AuditLogService {
    Page<AuditLogResponse> getAuditLogByParams(AuditLogRequest request, Pageable pageable);

    Integer getAuditLogInfo(AuditLogRequest request);

    List<AuditLogResponse> getLatestAuditLogs();

    String buildDetails(String method, String controller, String status, String error);

    void saveAudit(String action, String resource, String details, String status, String currentJson, String newJson,String externalResponseJson, String company, String machineId);

    String getUsername();
}

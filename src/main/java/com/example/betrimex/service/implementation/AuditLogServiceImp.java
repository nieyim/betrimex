package com.example.betrimex.service.implementation;

import com.example.betrimex.model.AuditLog;
import com.example.betrimex.model.dto.request.AuditLogRequest;
import com.example.betrimex.model.dto.response.AuditLogResponse;
import com.example.betrimex.repository.AuditLogRepository;
import com.example.betrimex.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImp implements AuditLogService {

    private final AuditLogRepository auditLogRepository;


    @Override
    public Page<AuditLogResponse> getAuditLogByParams(AuditLogRequest request, Pageable pageable) {
        return null;
    }

    @Override
    public Integer getAuditLogInfo(AuditLogRequest request) {
        return 0;
    }

    @Override
    public List<AuditLogResponse> getLatestAuditLogs() {
        return List.of();
    }

    @Override
    public String buildDetails(String method, String controller, String status, String error) {
        return "";
    }

    @Override
    public void saveAudit(String action, String resource, String details, String status, String currentJson, String newJson, String externalResponseJson, String company, String machineId) {
        String username = getUsername();
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setResource(resource);
        log.setStatus(status);
        log.setDetails(details);
        log.setCurrentJson(currentJson);
        log.setNewJson(newJson);
        log.setExternalResponseJson(externalResponseJson);

        auditLogRepository.save(log);
    }

    @Override
    public String getUsername() {
        return "";
    }
}

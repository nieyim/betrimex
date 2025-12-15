package com.example.betrimex.controller;

import com.example.betrimex.model.dto.request.AuditLogRequest;
import com.example.betrimex.model.dto.response.AuditLogResponse;
import com.example.betrimex.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @PostMapping("/search-params")
    public Page<AuditLogResponse> searchParams(@RequestBody AuditLogRequest request, Pageable pageable) {
        return auditLogService.getAuditLogByParams(request, pageable);
    }
}

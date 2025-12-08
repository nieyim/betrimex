package com.example.betrimex.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponse {
    private Long id;

    private String username;

    private String action;

    private String resource;

    private String details;

    private String status;

    private String currentJson;

    private String newJson;

    protected LocalDateTime createdAt;
}

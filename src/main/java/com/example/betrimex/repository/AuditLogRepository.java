package com.example.betrimex.repository;

import com.example.betrimex.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

    Page<AuditLog> getAuditLogsByCreatedAtBetween(Pageable pageable, LocalDateTime from, LocalDateTime to);

    Integer countAuditLogByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<AuditLog> findTop6ByOrderByCreatedAtDesc();
}

package com.example.betrimex.repository;

import com.example.betrimex.model.QrData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface QrDataRepository extends JpaRepository<QrData, Long>, JpaSpecificationExecutor<QrData> {

    Page<QrData> getQrDataByCreatedAtBetweenOrderByCreatedAtDesc(Pageable pageable, LocalDateTime from, LocalDateTime to);

    long  countQrDataByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT COUNT(q) FROM QrData q WHERE q.createdAt BETWEEN :startOfDay AND :endOfDay AND q.isSendAI = true")
    long countRegisterSend(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(q) FROM QrData q WHERE q.createdAt BETWEEN :startOfDay AND :endOfDay AND q.isSendAI = false")
    long countRegisterNotSend(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}

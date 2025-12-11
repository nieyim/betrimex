package com.example.betrimex.repository;

import com.example.betrimex.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(String id);

    @Query("""
    SELECT p FROM Product p
    WHERE p.createdAt BETWEEN :from AND :to
    AND (:isSync IS NULL OR p.isSync = :isSync)
    ORDER BY p.createdAt DESC
""")
    Page<Product> getProductsByCreatedAtBetweenAndIsSyncOrderByCreatedAtDesc(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("isSync") Boolean isSync,
            Pageable pageable
    );

    List<Product> getProductsByCreatedAtBetweenOrderByCreatedAt(LocalDateTime from, LocalDateTime to);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.redCardLot IS NOT NULL AND DATE(p.createdAt) = CURRENT_DATE")
    long countRedCardLotToday();

    Optional<Product> findByLotId(String lot_id);

    @Query("SELECT COUNT(p) FROM Product p WHERE DATE(p.createdAt) = CURRENT_DATE")
    long countProductsCreatedToday();

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p")
    long sumAllQuantity();
}

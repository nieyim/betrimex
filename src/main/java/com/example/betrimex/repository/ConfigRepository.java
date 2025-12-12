package com.example.betrimex.repository;

import com.example.betrimex.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long>, JpaSpecificationExecutor<Config> {
    Optional<Config> findTopByConfigKeyOrderByIdAsc(String configKey);
}

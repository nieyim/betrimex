package com.example.betrimex.service.implementation;

import com.example.betrimex.model.Config;
import com.example.betrimex.repository.ConfigRepository;
import com.example.betrimex.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConfigServiceImp implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    public String getValueByKey(String key) {
        return configRepository.findTopByConfigKeyOrderByIdAsc(key)
                .map(Config::getConfigValue)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy config: " + key));
    }
}

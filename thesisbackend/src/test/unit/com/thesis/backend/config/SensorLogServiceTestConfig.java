package com.thesis.backend.config;

import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.SensorLogService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorLogServiceTestConfig {
    @MockBean
    private SensorLogRepository sensorLogRepository;

    @Bean
    public SensorLogService sensorLogService() {
        return new SensorLogService(sensorLogRepository);
    }

}

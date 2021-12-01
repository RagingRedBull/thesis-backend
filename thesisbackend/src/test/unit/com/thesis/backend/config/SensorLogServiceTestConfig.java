package com.thesis.backend.config;

import com.thesis.backend.model.util.factory.SensorLogFactory;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.SensorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorLogServiceTestConfig {
    @MockBean
    private SensorLogRepository sensorLogRepository;
    @Autowired
    private SensorLogFactory sensorLogFactory;

    @Bean
    public SensorLogService sensorLogService() {
        return new SensorLogService(sensorLogRepository, sensorLogFactory);
    }
    @Bean
    public SensorLogFactory sensorLogFactory() {
        return new SensorLogFactory();
    }
}

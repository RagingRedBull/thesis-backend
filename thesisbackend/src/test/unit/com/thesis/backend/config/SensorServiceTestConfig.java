package com.thesis.backend.config;

import com.thesis.backend.repository.SensorRepository;
import com.thesis.backend.service.SensorService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorServiceTestConfig {
    @MockBean
    private SensorRepository sensorRepository;
    @Bean
    public SensorService sensorService() {
        return new SensorService(sensorRepository);
    }

}

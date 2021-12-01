package com.thesis.backend.config;

import com.thesis.backend.model.util.factory.SensorUpdateFactory;
import com.thesis.backend.repository.SensorRepository;
import com.thesis.backend.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorServiceTestConfig {
    @MockBean
    private SensorRepository sensorRepository;
    @Autowired
    private SensorUpdateFactory sensorUpdateFactory;
    @Bean
    public SensorService sensorService() {
        return new SensorService(sensorRepository, sensorUpdateFactory);
    }
    @Bean
    public SensorUpdateFactory sensorUpdateFactory() {
        return new SensorUpdateFactory();
    }
}

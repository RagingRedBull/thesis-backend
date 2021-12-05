package com.thesis.backend.config;

import com.thesis.backend.repository.SensorRepository;
import com.thesis.backend.service.SensorService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class SensorServiceTestConfig {
    @MockBean
    private SensorRepository sensorRepository;
    public SensorService sensorService() {
        return new SensorService(sensorRepository);
    }
}

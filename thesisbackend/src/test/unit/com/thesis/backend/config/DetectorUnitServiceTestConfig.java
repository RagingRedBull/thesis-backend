package com.thesis.backend.config;

import com.thesis.backend.repository.DetectorUnitRepository;
import com.thesis.backend.repository.SensorRepository;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class DetectorUnitServiceTestConfig {
    @MockBean
    private DetectorUnitRepository detectorUnitRepository;
    @MockBean
    private SensorRepository sensorRepository;
    @Autowired
    private SensorService sensorService;

    public DetectorUnitService detectorUnitService() {
        return new DetectorUnitService(detectorUnitRepository, sensorService);
    }

    public SensorService sensorService() {
        return new SensorService(sensorRepository);
    }
}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.util.factory.SensorUpdateFactory;
import com.thesis.backend.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private final Logger logger = LoggerFactory.getLogger(SensorService.class);
    private final SensorRepository sensorRepository;
    private final SensorUpdateFactory sensorUpdateFactory;

    public SensorService(SensorRepository sensorRepository, SensorUpdateFactory sensorUpdateFactory) {
        this.sensorRepository = sensorRepository;
        this.sensorUpdateFactory = sensorUpdateFactory;
    }

    public Set<SensorUpdateDto> buildSensorSetUpdateDto(Set<Sensor> sensorSet, boolean toEnable) {
        Set<SensorUpdateDto> sensorUpdateDtoSet = sensorSet.stream()
                .map(sensor -> sensorUpdateFactory.mapSensorUpdateToDto(sensor, toEnable))
                .collect(Collectors.toSet());
        logger.info(sensorUpdateDtoSet.toString());
        return sensorUpdateDtoSet;
    }
    public Set<Sensor> getAllSensorsInList(List<Integer> sensorIdLIst){
       return sensorRepository.findByIdIn(sensorIdLIst);
    }
}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.util.factory.SensorUpdateFactory;
import com.thesis.backend.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Set<SensorUpdateDto> builSensorSetUpdateDto(Set<Sensor> sensorSet, boolean toEnable) {
        return sensorSet.stream()
                .map(sensor -> SensorUpdateFactory.mapSensorUpdateToDto(sensor, toEnable))
                .collect(Collectors.toSet());
    }
    public Set<Sensor> getAllSensorsInList(List<Integer> sensorIdLIst){
       return sensorRepository.findByIdIn(sensorIdLIst);
    }
}

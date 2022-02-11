package com.thesis.backend.service;

import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public Set<SensorUpdateDto> buildSensorSetUpdateDto(Set<Sensor> sensorSet, boolean toEnable) {
        Set<SensorUpdateDto> sensorUpdateDtoSet = sensorSet.stream()
                .map(sensor -> new SensorUpdateDto(sensor.getId(), toEnable))
                .collect(Collectors.toSet());
        log.info(sensorUpdateDtoSet.toString());
        return sensorUpdateDtoSet;
    }
    public Set<Sensor> getAllSensorsInList(List<Integer> sensorIdLIst){
       return sensorRepository.findByIdIn(sensorIdLIst);
    }
}

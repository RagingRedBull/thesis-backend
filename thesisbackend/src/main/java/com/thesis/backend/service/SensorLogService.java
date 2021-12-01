package com.thesis.backend.service;

import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.factory.SensorLogFactory;
import com.thesis.backend.repository.SensorLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorLogService {
    private final Logger logger = LoggerFactory.getLogger(SensorLogService.class);
    private final SensorLogRepository sensorLogRepository;
    private final SensorLogFactory sensorLogFactory;

    public SensorLogService(SensorLogRepository sensorLogRepository, SensorLogFactory sensorLogFactory) {
        this.sensorLogRepository = sensorLogRepository;
        this.sensorLogFactory = sensorLogFactory;
    }

    public List<SensorLog> findLogsByDetectorLogId(long id) {
        return sensorLogRepository.findByDetectorUnitLog(id);
    }
    public Set<SensorLog> mapSensorLogDtoEntitySet(Set<SensorLogDto> sensorLogDtoSet, DetectorUnitLog detectorUnitLog){
        return sensorLogDtoSet.stream()
                .map(sensorLogDto -> sensorLogFactory.mapDtoToEntity(sensorLogDto, detectorUnitLog))
                .collect(Collectors.toSet());
    }

    public Set<SensorLogDto> mapSensorLogEntityToDto(List<SensorLog> sensorLogSet) {
        return sensorLogSet.stream()
                .map(sensorLogFactory::mapEntityToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}

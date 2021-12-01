package com.thesis.backend.service;

import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
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

    public SensorLogService(SensorLogRepository sensorLogRepository) {
        this.sensorLogRepository = sensorLogRepository;
    }

    public List<SensorLog> findLogsByDetectorLogId(long id) {
        return sensorLogRepository.findByDetectorUnitLog(id);
    }
    public Set<SensorLog> mapSensorLogDtoEntitySet(Set<SensorLogDto> sensorLogDtoSet, DetectorUnitLog detectorUnitLog){
        SensorLogMapper mapper = new SensorLogMapper();
        return sensorLogDtoSet.stream()
                .map(sensorLogDto -> {
                    SensorLog log = mapper.mapToEntity(sensorLogDto);
                    log.setDetectorUnitLog(detectorUnitLog);
                    return log;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<SensorLogDto> mapSensorLogEntityToDto(List<SensorLog> sensorLogSet) {
        SensorLogMapper mapper = new SensorLogMapper();
        return sensorLogSet.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}

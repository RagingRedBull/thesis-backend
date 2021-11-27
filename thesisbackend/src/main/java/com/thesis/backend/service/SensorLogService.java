package com.thesis.backend.service;

import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.factory.SensorLogFactory;
import com.thesis.backend.repository.SensorLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorLogService {

    private final SensorLogRepository sensorLogRepository;

    public SensorLogService(SensorLogRepository sensorLogRepository) {
        this.sensorLogRepository = sensorLogRepository;
    }

    public List<SensorLog> findLogsByDetectorLogId(long id) {
        return sensorLogRepository.findByDetectorUnitLog(id);
    }
    public Set<SensorLog> mapSensorLogDtoEntitySet(Set<SensorLogDto> sensorLogDtoList, DetectorUnitLog detectorUnitLog){
        return sensorLogDtoList.stream()
                .map(sensorLogDto -> SensorLogFactory.mapDtoToEntity(sensorLogDto, detectorUnitLog))
                .collect(Collectors.toSet());
    }


}

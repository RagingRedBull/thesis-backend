package com.thesis.backend.service;

import com.thesis.backend.model.dto.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.factory.SensorLogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorLogService {
    public List<SensorLog> mapSensorLogDtoList(List<SensorLogDto> sensorLogDtoList, DetectorUnitLog detectorUnitLog){
        return sensorLogDtoList.stream()
                .map(sensorLogDto -> SensorLogFactory.mapDtoToEntity(sensorLogDto, detectorUnitLog))
                .collect(Collectors.toList());
    }
}

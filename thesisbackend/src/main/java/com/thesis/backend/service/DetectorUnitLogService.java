package com.thesis.backend.service;

import com.thesis.backend.model.dto.DetectorUnitLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DetectorUnitLogService {
    private DetectorUnitLogRepository detectorUnitLogRepository;
    private SensorLogService sensorLogService;

    public DetectorUnitLogService(DetectorUnitLogRepository detectorUnitLogRepository,
                                  SensorLogService sensorLogService) {
        this.detectorUnitLogRepository = detectorUnitLogRepository;
        this.sensorLogService = sensorLogService;
    }

    public void persist(DetectorUnitLogDto detectorUnitLogDto) {
        DetectorUnitLog detectorUnitLog = new DetectorUnitLog(detectorUnitLogDto);
        detectorUnitLog.setTimeRecorded(LocalDateTime.now());
        detectorUnitLog.setSensorLogList(sensorLogService.mapSensorLogDtoList(detectorUnitLogDto.getSensorLogList(), detectorUnitLog));
        detectorUnitLogRepository.save(detectorUnitLog);
    }
}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DetectorUnitLogService {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitLog.class);
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

        //Debug purporse
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.info("INSERTING NEW LOG");
        logger.info("Mac Address: " + detectorUnitLog.getMacAddress());
        logger.info("Date Recorded: " + detectorUnitLog.getTimeRecorded().format(formatter));

        detectorUnitLogRepository.save(detectorUnitLog);
    }
}

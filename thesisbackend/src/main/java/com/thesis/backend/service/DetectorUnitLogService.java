package com.thesis.backend.service;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.util.factory.SensorLogFactory;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Page<DetectorUnitLogDto> findDetectorLogsByPage(Pageable page) {
        return detectorUnitLogRepository.findAll(page).map(detectorUnitLog -> {
            DetectorUnitLogDto dto = new DetectorUnitLogDto(detectorUnitLog);
            Set<SensorLogDto> sensorLogDtoSet =
                    sensorLogService.findLogsByDetectorLogId(detectorUnitLog.getId()).stream()
                    .map(SensorLogFactory::mapEntityToDto)
                    .collect(Collectors.toSet());
            dto.setSensorLogSet(sensorLogDtoSet);
            return dto;
        });
    }

    public void persist(DetectorUnitLogDto detectorUnitLogDto) {
        DetectorUnitLog detectorUnitLog = new DetectorUnitLog(detectorUnitLogDto);
        detectorUnitLog.setTimeRecorded(LocalDateTime.now());
        detectorUnitLog.setSensorLogSet(sensorLogService.mapSensorLogDtoEntitySet(detectorUnitLogDto.getSensorLogSet(),
                detectorUnitLog));

        //Debug purporse
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.info("INSERTING NEW LOG");
        logger.info("Mac Address: " + detectorUnitLog.getMacAddress());
        logger.info("Date Recorded: " + detectorUnitLog.getTimeRecorded().format(formatter));

        detectorUnitLogRepository.save(detectorUnitLog);
    }
}

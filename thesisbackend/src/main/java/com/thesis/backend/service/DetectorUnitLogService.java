package com.thesis.backend.service;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.util.mapper.DetectorUnitLogEntityMapper;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DetectorUnitLogService {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitLog.class);
    private final DetectorUnitLogRepository detectorUnitLogRepository;
    private final SensorLogService sensorLogService;

    public DetectorUnitLogService(DetectorUnitLogRepository detectorUnitLogRepository,
                                  SensorLogService sensorLogService) {
        this.detectorUnitLogRepository = detectorUnitLogRepository;
        this.sensorLogService = sensorLogService;
    }

    public Page<DetectorUnitLogDto> findDetectorLogsByPage(Pageable page) {
        DetectorUnitLogEntityMapper mapper = new DetectorUnitLogEntityMapper();
        return detectorUnitLogRepository.findAll(page).map(mapper::mapToDto);
    }

    public DetectorUnitLog saveOne(DetectorUnitLogDto detectorUnitLogDto) {
        DetectorUnitLog detectorUnitLog = new DetectorUnitLog(detectorUnitLogDto);
        detectorUnitLog.setTimeRecorded(LocalDateTime.now());
        detectorUnitLog.setSensorLogSet(sensorLogService.mapSensorLogDtoEntitySet(detectorUnitLogDto.getSensorLogSet(),
                detectorUnitLog));

        //Debug purporse
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.info("INSERTING NEW LOG");
        logger.info("Mac Address: " + detectorUnitLog.getMacAddress());
        logger.info("Date Recorded: " + detectorUnitLog.getTimeRecorded().format(formatter));
        detectorUnitLog = detectorUnitLogRepository.save(detectorUnitLog);
        logger.info("ROW ID: " + detectorUnitLog.getId());
        return detectorUnitLog;
    }
}

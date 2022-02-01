package com.thesis.backend.service;

import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.util.mapper.DetectorUnitLogMapper;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DetectorUnitLogService implements EntityService<DetectorUnitLog, DetectorUnitLogDto, Long> {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitLog.class);
    private final DetectorUnitLogRepository detectorUnitLogRepository;
    private final SensorLogService sensorLogService;

    public DetectorUnitLogService(DetectorUnitLogRepository detectorUnitLogRepository,
                                  SensorLogService sensorLogService) {
        this.detectorUnitLogRepository = detectorUnitLogRepository;
        this.sensorLogService = sensorLogService;
    }

    @Override
    public DetectorUnitLog findOneByPrimaryKey(Long primaryKey) {
        Optional<DetectorUnitLog> wrapper = detectorUnitLogRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            logger.error("No Detector Unit Log with ID: " + primaryKey);
            throw new EntityNotFoundException("No Detector Unit Log with ID: " + primaryKey);
        } else {
            return wrapper.get();
        }
    }

    @Override
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

    public Page<DetectorUnitLogDto> findDetectorLogsByPage(Pageable page) {
        DetectorUnitLogMapper mapper = new DetectorUnitLogMapper();
        return detectorUnitLogRepository.findAll(page).map(mapper::mapToDto);
    }
}

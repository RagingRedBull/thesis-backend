package com.thesis.backend.service;

import com.thesis.backend.config.AppConfig;
import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.entity.ml.MachineLearningInput;
import com.thesis.backend.model.util.mapper.DetectorUnitLogMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.repository.DetectorUnitLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class DetectorUnitLogService implements EntityService<DetectorUnitLog, DetectorUnitLogDto, Long> {
    private final DetectorUnitService detectorUnitService;
    private final MachineLearningInputService machineLearningInputService;
    private final DetectorUnitLogRepository detectorUnitLogRepository;
    private final SensorLogService sensorLogService;
    private final AppConfig appConfig;

    @Override
    public DetectorUnitLog findOneByPrimaryKey(Long primaryKey) {
        Optional<DetectorUnitLog> wrapper = detectorUnitLogRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            log.error("No Detector Unit Log with ID: " + primaryKey);
            throw new PrmtsEntityNotFoundException(DetectorUnit.class, primaryKey);
        } else {
            return wrapper.get();
        }
    }

    @Override
    public DetectorUnitLog saveOne(DetectorUnitLogDto detectorUnitLogDto) {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> mapper = new DetectorUnitLogMapper();
        DetectorUnitLog detectorUnitLog = mapper.mapToEntity(detectorUnitLogDto);
        detectorUnitLog.setTimeRecorded(LocalDateTime.now());
        detectorUnitLog.setSensorLogSet(sensorLogService.mapSensorLogDtoEntitySet(detectorUnitLogDto.getSensorLogSet(),
                detectorUnitLog));

        //Debug purporse
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("INSERTING NEW LOG");
        log.info("Mac Address: " + detectorUnitLog.getMacAddress());
        log.info("Date Recorded: " + detectorUnitLog.getTimeRecorded().format(formatter));
        log.info("SENSOR LOG SET: " + detectorUnitLog.getSensorLogSet());
        detectorUnitLog = detectorUnitLogRepository.save(detectorUnitLog);
        return detectorUnitLog;
    }

    @Override
    public void deleteOne(Long primaryKey) {
        detectorUnitLogRepository.deleteById(primaryKey);
    }

    @Override
    public DetectorUnitLog updateOne(DetectorUnitLogDto detectorUnitLogDto) {
        return null;
    }

    public void checkReadings(DetectorUnitLog detectorUnitLog) {
        if (sensorLogService.hasAbnormalSensorValue(detectorUnitLog.getSensorLogSet())
                && !appConfig.isEnabledAlarmingMode()) {
            log.info("Found abnormal readings with log id: " + detectorUnitLog.getId());
            appConfig.setEnabledAlarmingMode(true);
            DetectorUnit detectorUnit = detectorUnitService.findOneByPrimaryKey(detectorUnitLog.getMacAddress());
            MachineLearningInput machineLearningInput = new MachineLearningInput();
            machineLearningInput.setXOrigin(detectorUnit.getCompartment().getXDimension());
            machineLearningInput.setYOrigin(detectorUnit.getCompartment().getYDimension());
            machineLearningInput.setFloorOrigin(detectorUnit.getCompartment().getFloor().getOrder());
            machineLearningInput.setTimeRecorded(LocalDateTime.now());
            machineLearningInputService.saveOne(machineLearningInput);
        }
    }

    public DetectorUnitLog findLatestLog(String macAddress) {
        return detectorUnitLogRepository.findLatestLog(macAddress);
    }

    public Set<DetectorUnitLog> findDetectorLogsByDetectorUnitId(Set<String> detectorUnits, Sort sort) {
        return detectorUnitLogRepository.findFirstByMacAddressIn(detectorUnits, sort);
    }

    public Page<DetectorUnitLogDto> findDetectorLogsByPage(Pageable page) {
        DetectorUnitLogMapper mapper = new DetectorUnitLogMapper();
        return detectorUnitLogRepository.findAll(page).map(mapper::mapToDto);
    }

    public DetectorUnitLogDto buildDetectorUnitLogDto(DetectorUnitLog log) {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> detectorLogMapper = new DetectorUnitLogMapper();
        EntityMapper<SensorLog, SensorLogDto> sensorMapper = new SensorLogMapper();
        DetectorUnitLogDto dto = detectorLogMapper.mapToDto(log);
        dto.setSensorLogSet(log.getSensorLogSet().stream()
                .map(sensorMapper::mapToDto)
                .collect(Collectors.toSet()));
        return dto;
    }
}

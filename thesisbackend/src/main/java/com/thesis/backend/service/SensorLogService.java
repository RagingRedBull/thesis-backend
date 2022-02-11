package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SensorLogService implements EntityService<SensorLog, SensorLogDto, Long> {
    private final Logger log = LoggerFactory.getLogger(SensorLogService.class);
    private final SensorLogRepository sensorLogRepository;


    @Override
    public SensorLog findOneByPrimaryKey(Long primaryKey) {
        Optional<SensorLog> wrapper = sensorLogRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            log.error("No Sensor Log with ID: " + primaryKey);
            throw new PrmtsEntityNotFoundException(SensorLog.class, primaryKey);
        } else {
            return wrapper.get();
        }
    }

    @Override
    public SensorLog saveOne(SensorLogDto sensorLogDto) {
        EntityMapper<SensorLog, SensorLogDto> mapper = new SensorLogMapper();
        return sensorLogRepository.save(mapper.mapToEntity(sensorLogDto));
    }

    @Override
    public void deleteOne(Long primaryKey) {
        sensorLogRepository.deleteById(primaryKey);
    }

    @Override
    public SensorLog updateOne(SensorLogDto sensorLogDto, Long primaryKey) {
        return null;
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

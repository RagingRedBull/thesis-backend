package com.thesis.backend.service;

import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogEntityMapper;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorLogService implements EntityService<SensorLog, SensorLogDto, Long> {
    private final Logger logger = LoggerFactory.getLogger(SensorLogService.class);
    private final SensorLogRepository sensorLogRepository;

    public SensorLogService(SensorLogRepository sensorLogRepository) {
        this.sensorLogRepository = sensorLogRepository;
    }

    @Override
    public SensorLog findOneByPrimaryKey(Long primaryKey) {
        Optional<SensorLog> wrapper = sensorLogRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return wrapper.get();
        }
    }

    @Override
    public SensorLog saveOne(SensorLogDto sensorLogDto) {
        EntityMapper<SensorLog, SensorLogDto> mapper = new SensorLogEntityMapper();
        return sensorLogRepository.save(mapper.mapToEntity(sensorLogDto));
    }

    public List<SensorLog> findLogsByDetectorLogId(long id) {
        return sensorLogRepository.findByDetectorUnitLog(id);
    }
    public Set<SensorLog> mapSensorLogDtoEntitySet(Set<SensorLogDto> sensorLogDtoSet, DetectorUnitLog detectorUnitLog){
        SensorLogEntityMapper mapper = new SensorLogEntityMapper();
        return sensorLogDtoSet.stream()
                .map(sensorLogDto -> {
                    SensorLog log = mapper.mapToEntity(sensorLogDto);
                    log.setDetectorUnitLog(detectorUnitLog);
                    return log;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<SensorLogDto> mapSensorLogEntityToDto(List<SensorLog> sensorLogSet) {
        SensorLogEntityMapper mapper = new SensorLogEntityMapper();
        return sensorLogSet.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

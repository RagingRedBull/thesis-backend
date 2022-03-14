package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.SensorStatusReportLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.logs.*;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SensorLogService implements EntityService<SensorLog, SensorLogDto, Long> {
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

    @Transactional
    @Override
    public SensorLog saveOne(SensorLogDto sensorLogDto) {
        EntityMapper<SensorLog, SensorLogDto> mapper = new SensorLogMapper();
        return sensorLogRepository.save(mapper.mapToEntity(sensorLogDto));
    }

    @Transactional
    @Override
    public void deleteOne(Long primaryKey) {
        sensorLogRepository.deleteById(primaryKey);
    }

    @Override
    public SensorLog updateOne(SensorLogDto sensorLogDto) {
        return null;
    }

    @Transactional
    public List<SensorLog> saveAll(Iterable<SensorLog> sensorLogCollection) {
        return sensorLogRepository.saveAllAndFlush(sensorLogCollection);
    }
    public List<SensorLog> findLogsByDetectorLogId(long id) {
        return sensorLogRepository.findByDetectorUnitLog(id);
    }

    public Set<SensorLog> mapSensorLogDtoEntitySet(Set<SensorLogDto> sensorLogDtoSet, DetectorUnitLog detectorUnitLog) {
        SensorLogMapper mapper = new SensorLogMapper();
        return sensorLogDtoSet.stream()
                .map(sensorLogDto -> {
                    SensorLog log = mapper.mapToEntity(sensorLogDto);
                    log.setDetectorUnitLog(detectorUnitLog);
                    return log;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public boolean hasAbnormalSensorValue(Set<SensorLog> sensorLogSet) {
        boolean isAbnormal = false;
        for (SensorLog log : sensorLogSet) {
            if (log instanceof DhtSensorLog) {
                if (((DhtSensorLog) log).getTemperature() >= 35) {
                    isAbnormal = true;
                    break;
                }
            } else if (log instanceof MqSensorLog) {
                if (log.getName() == SensorName.MQ2) {
                    if (((MqSensorLog) log).getMqValue() >= 165) {
                        isAbnormal = true;
                        break;
                    }
                } else if (log.getName() == SensorName.MQ5) {
                    if (((MqSensorLog) log).getMqValue() >= 455) {
                        isAbnormal = true;
                        break;
                    }
                } else if (log.getName() == SensorName.MQ7) {
                    if (((MqSensorLog) log).getMqValue() >= 190) {
                        isAbnormal = true;
                        break;
                    }
                } else if (log.getName() == SensorName.MQ135) {
                    if (((MqSensorLog) log).getMqValue() >= 210) {
                        isAbnormal = true;
                        break;
                    }
                }
            } else if (log instanceof FireSensorLog) {
                if (((FireSensorLog) log).getSensorValue() > 120) {
                    isAbnormal = true;
                    break;
                }
            }
        }
        return isAbnormal;
    }

    public Set<SensorLogDto> mapSensorLogEntityToDto(List<SensorLog> sensorLogSet) {
        SensorLogMapper mapper = new SensorLogMapper();
        return sensorLogSet.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public SensorStatusReportLogDto getSensorStatusByMacAddressBetweenStartEndDate(String macAddress, LocalDateTime start,
                                                                                   LocalDateTime end, SensorName sensorName,
                                                                                   SensorType sensorType) {
        SensorStatusReportLogDto dto = null;
        if (sensorType == SensorType.DHT) {
            dto = sensorLogRepository.getDhtStatusReportBetweenDate(macAddress, start, end, sensorName);
        } else if (sensorType == SensorType.MQ) {
            dto = sensorLogRepository.getMqStatusReportBetweenDate(macAddress,start,end,sensorName);
        } else if (sensorType == SensorType.FIRE) {
            dto = sensorLogRepository.getFireStatusReportBetweenDate(macAddress,start,end,sensorName);
        } else if (sensorType == SensorType.SOUND) {
            dto = sensorLogRepository.getSoundStatusReportBetweenDate(macAddress,start,end,sensorName);
        }
        dto.setSensorName(sensorName);
        dto.setSensorType(sensorType);
        return dto;
    }

    public List<SensorLog> getAbnormalReading(Set<SensorLog> sensorLogSet) {
        List<SensorLog> abnormalSensorLogs = new ArrayList<>();
        for (SensorLog sensorLog : sensorLogSet) {
            if(sensorLog.getType() == SensorType.DHT) {
                if(((DhtSensorLog) sensorLog).getTemperature() > 35) {
                    abnormalSensorLogs.add(sensorLog);
                }
            } else if (sensorLog.getType() == SensorType.MQ) {
                if (sensorLog.getName() == SensorName.MQ2 && ((MqSensorLog)sensorLog).getMqValue() > 165) {
                    abnormalSensorLogs.add(sensorLog);
                } else if (sensorLog.getName() == SensorName.MQ5 && ((MqSensorLog)sensorLog).getMqValue() > 455) {
                    abnormalSensorLogs.add(sensorLog);
                } else if (sensorLog.getName() == SensorName.MQ7 && ((MqSensorLog)sensorLog).getMqValue() > 190) {
                    abnormalSensorLogs.add(sensorLog);
                } else if (sensorLog.getName() == SensorName.MQ135 && ((MqSensorLog) sensorLog).getMqValue() > 210) {
                    abnormalSensorLogs.add(sensorLog);
                }
            } else if (sensorLog.getType() == SensorType.FIRE) {
                if (((FireSensorLog)sensorLog).getSensorValue() > 120){
                    abnormalSensorLogs.add(sensorLog);
                }
            }
        }
        return abnormalSensorLogs;
    }
}

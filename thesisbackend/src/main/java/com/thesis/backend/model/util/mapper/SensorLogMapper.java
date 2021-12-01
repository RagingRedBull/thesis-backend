package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.sensor.DhtSensorLogDto;
import com.thesis.backend.model.dto.sensor.MqSensorLogDto;
import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DhtSensorLog;
import com.thesis.backend.model.entity.logs.MqSensorLog;
import com.thesis.backend.model.entity.logs.SensorLog;

public class SensorLogMapper implements Mapper<SensorLog, SensorLogDto> {
    @Override
    public SensorLogDto mapToDto(SensorLog entity) {
        SensorLogDto dto = null;
        if (entity instanceof DhtSensorLog) {
            DhtSensorLogDto dhtSensorLogDto = new DhtSensorLogDto(entity.getId(), entity.getType(), entity.getName());
            dhtSensorLogDto.setHumidity(((DhtSensorLog) entity).getHumidity());
            dhtSensorLogDto.setTemperature(((DhtSensorLog) entity).getTemperature());
            dto = dhtSensorLogDto;
        } else if (entity instanceof MqSensorLog) {
            MqSensorLogDto mqSensorLogDto = new MqSensorLogDto(entity.getId(),entity.getType(),entity.getName());
            mqSensorLogDto.setMqValue((((MqSensorLog) entity).getMqValue()));
            dto = mqSensorLogDto;
        }
        return dto;
    }

    @Override
    public SensorLog mapToEntity(SensorLogDto dto) {
        SensorLog entity = null;
        if (dto instanceof DhtSensorLogDto) {
            DhtSensorLog dhtSensorLog = new DhtSensorLog(dto.getId(), dto.getType(),dto.getName());
            dhtSensorLog.setTemperature(((DhtSensorLogDto) dto).getTemperature());
            dhtSensorLog.setHumidity(((DhtSensorLogDto) dto).getHumidity());
            entity = dhtSensorLog;
        } else if (dto instanceof MqSensorLogDto) {
            MqSensorLog mqSensorLog = new MqSensorLog(dto.getId(), dto.getType(), dto.getName());
            mqSensorLog.setMqValue(((MqSensorLogDto) dto).getMqValue());
            entity = mqSensorLog;
        }
        return entity;
    }
}

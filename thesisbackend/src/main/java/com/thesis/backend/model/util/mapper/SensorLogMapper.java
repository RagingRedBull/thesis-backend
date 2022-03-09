package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.logs.*;
import com.thesis.backend.model.entity.logs.*;

public class SensorLogMapper implements EntityMapper<SensorLog, SensorLogDto> {
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
        } else if (entity instanceof FireSensorLog) {
            FireSensorLogDto fireSensorLogDto = new FireSensorLogDto(entity.getId(),entity.getType(),entity.getName());
            fireSensorLogDto.setSensorValue((((FireSensorLog) entity).getSensorValue()));
            dto = fireSensorLogDto;
        } else if (entity instanceof SoundSensorLog) {
            SoundSensorLogDto soundSensorLogDto = new SoundSensorLogDto(entity.getId(),entity.getType(),entity.getName());
            soundSensorLogDto.setSound(((SoundSensorLog) entity).getSound());
            dto = soundSensorLogDto;
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
        } else if (dto instanceof FireSensorLogDto) {
            FireSensorLog fireSensorLog = new FireSensorLog(dto.getId(), dto.getType(), dto.getName());
            fireSensorLog.setSensorValue(((FireSensorLogDto) dto).getSensorValue());
            entity = fireSensorLog;
        } else if (dto instanceof SoundSensorLogDto) {
            SoundSensorLog soundSensorLog = new SoundSensorLog(dto.getId(), dto.getType(), dto.getName());
            soundSensorLog.setSound(((SoundSensorLogDto) dto).getSound());
            entity = soundSensorLog;
        }
        return entity;
    }
}

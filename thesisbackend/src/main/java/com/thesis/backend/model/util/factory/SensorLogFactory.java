package com.thesis.backend.model.util.factory;

import com.thesis.backend.model.dto.sensor.DhtSensorLogDto;
import com.thesis.backend.model.dto.sensor.MqSensorLogDto;
import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.DhtSensorLog;
import com.thesis.backend.model.entity.logs.MqSensorLog;
import com.thesis.backend.model.entity.logs.SensorLog;

public class SensorLogFactory {
    public static SensorLog mapDtoToEntity(SensorLogDto dto, DetectorUnitLog detectorUnitLog){
        SensorLog log = null;
        if(dto instanceof DhtSensorLogDto){
            DhtSensorLog dhtSensorLog = new DhtSensorLog(dto, detectorUnitLog);
            dhtSensorLog.setTemperature(((DhtSensorLogDto) dto).getTemperature());
            dhtSensorLog.setHumidity(((DhtSensorLogDto) dto).getHumidity());
            log = dhtSensorLog;
        } else if (dto instanceof MqSensorLogDto){
            MqSensorLog mqSensorLog = new MqSensorLog((MqSensorLogDto) dto);
            mqSensorLog.setMqValue(((MqSensorLogDto)dto).getMqValue());
            log = mqSensorLog;
        }
        return log;
    }

    public static SensorLogDto mapEntityToDto(SensorLog log) {
        SensorLogDto dto = null;
        if(log instanceof DhtSensorLog) {
            DhtSensorLogDto dhtSensorLogDto = new DhtSensorLogDto(log);
            dhtSensorLogDto.setHumidity(((DhtSensorLog) log).getHumidity());
            dhtSensorLogDto.setTemperature(((DhtSensorLog) log).getTemperature());
            dto = dhtSensorLogDto;
        }
        else if(log instanceof MqSensorLog) {
            MqSensorLogDto mqSensorLogDto = new MqSensorLogDto(log);
            mqSensorLogDto.setMqValue((((MqSensorLog) log).getMqValue()));
            dto = mqSensorLogDto;
        }
        return dto;
    }
}

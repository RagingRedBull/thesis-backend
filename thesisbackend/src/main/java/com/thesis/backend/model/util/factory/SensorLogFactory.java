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
            log = new DhtSensorLog((DhtSensorLogDto) dto, detectorUnitLog);
        } else if (dto instanceof MqSensorLogDto){
            log = new MqSensorLog((MqSensorLogDto) dto, detectorUnitLog);
        }
        return log;
    }
}

package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.dto.MqSensorLogDto;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MQ")
public class MqSensorLog extends SensorLog{
    @Column(name = "ppm_reading")
    private int ppmReading;

    public MqSensorLog(MqSensorLogDto dto, DetectorUnitLog detectorUnitLog) {
        super.setType(dto.getType());
        super.setName(dto.getName());
        super.setDetectorUnitLog(detectorUnitLog);
        this.ppmReading = dto.getPpmReading();
    }


}

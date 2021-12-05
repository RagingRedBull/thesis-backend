package com.thesis.backend.model.converter;

import com.thesis.backend.model.enums.SensorName;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class SensorNameConverter implements AttributeConverter<SensorName, String> {
    @Override
    public String convertToDatabaseColumn(SensorName attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getName();
    }

    @Override
    public SensorName convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        return Stream.of(SensorName.values())
                .filter(sensorType -> sensorType.getName().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

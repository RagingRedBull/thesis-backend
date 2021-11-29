package com.thesis.backend.model.converter;

import com.thesis.backend.model.enums.SensorType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class SensorTypeConverter implements AttributeConverter<SensorType, String> {
    @Override
    public String convertToDatabaseColumn(SensorType attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getType();
    }

    @Override
    public SensorType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(SensorType.values())
                .filter(sensorType -> sensorType.getType().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

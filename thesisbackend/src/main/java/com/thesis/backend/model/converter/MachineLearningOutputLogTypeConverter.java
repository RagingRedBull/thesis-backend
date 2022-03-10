package com.thesis.backend.model.converter;

import com.thesis.backend.model.entity.ml.LogType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class MachineLearningOutputLogTypeConverter implements AttributeConverter<LogType, String > {
    @Override
    public String convertToDatabaseColumn(LogType logType) {
        if(logType == null){
            return null;
        }
        return logType.getType();
    }

    @Override
    public LogType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(LogType.values())
                .filter(logType -> logType.getType().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

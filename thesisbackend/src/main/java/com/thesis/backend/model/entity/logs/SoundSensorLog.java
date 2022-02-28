package com.thesis.backend.model.entity.logs;


import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@DiscriminatorValue(value = "SOUND")
public class SoundSensorLog extends SensorLog{
    private float sound;
    @PersistenceConstructor
    public SoundSensorLog(long id, SensorType type, SensorName name) {
        super(id, name, type);
    }
}

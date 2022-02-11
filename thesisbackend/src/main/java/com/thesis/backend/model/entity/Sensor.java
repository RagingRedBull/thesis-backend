package com.thesis.backend.model.entity;

import com.thesis.backend.model.converter.SensorNameConverter;
import com.thesis.backend.model.converter.SensorTypeConverter;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 30)
    @Convert(converter = SensorNameConverter.class)
    private SensorName name;
    @Column(name = "type", length = 50)
    @Convert(converter = SensorTypeConverter.class)
    private SensorType type;
    @Column(name = "description", length = 255)
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "associatedSensorSet")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DetectorUnit> associatedDetectorUnitSet;
}

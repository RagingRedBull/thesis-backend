package com.thesis.backend.model.entity;

import com.thesis.backend.model.converter.SensorNameConverter;
import com.thesis.backend.model.converter.SensorTypeConverter;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    private Set<DetectorUnit> associatedDetectorUnitSet;

    private Sensor() {
        /// Default Empty
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SensorName getName() {
        return name;
    }

    public void setName(SensorName name) {
        this.name = name;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DetectorUnit> getAssociatedDetectorUnitSet() {
        return associatedDetectorUnitSet;
    }

    public void setAssociatedDetectorUnitSet(Set<DetectorUnit> associatedDetectorUnitSet) {
        this.associatedDetectorUnitSet = associatedDetectorUnitSet;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description +
                '}';
    }
}

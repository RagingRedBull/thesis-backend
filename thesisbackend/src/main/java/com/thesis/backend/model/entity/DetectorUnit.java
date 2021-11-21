package com.thesis.backend.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "detector_units")
public class DetectorUnit {
    private int id;
    private String name;
    private List<Sensor> attachedSensorList;
    private Floor associatedFloor;
}

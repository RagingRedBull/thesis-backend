package com.thesis.backend.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "floors")
public class Floor {
    private int id;
    private String name;
    private List<DetectorUnit> associatedDetectorUnitList;
}

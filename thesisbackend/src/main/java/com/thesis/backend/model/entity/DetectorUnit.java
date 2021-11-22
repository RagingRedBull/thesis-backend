package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "detector_units")
public class DetectorUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mac_address")
    private String macAddress;
    @Column(name = "name")
    private String name;
    @Column(name = "x_loc")
    private int xpos;
    @Column(name = "y_loc")
    private int ypos;
    @ManyToOne(fetch = FetchType.EAGER)
    private Floor associatedFloor;
    @ManyToMany
    @JoinTable(
            name = "sensors_join_detector_units",
            joinColumns = @JoinColumn(name = "detector_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private List<Sensor> associatedSensorList;

    public DetectorUnit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public Floor getAssociatedFloor() {
        return associatedFloor;
    }

    public void setAssociatedFloor(Floor associatedFloor) {
        this.associatedFloor = associatedFloor;
    }

    public List<Sensor> getAssociatedSensorList() {
        return associatedSensorList;
    }

    public void setAssociatedSensorList(List<Sensor> associatedSensorList) {
        this.associatedSensorList = associatedSensorList;
    }
}

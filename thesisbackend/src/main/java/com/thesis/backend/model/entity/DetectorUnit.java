package com.thesis.backend.model.entity;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "detector_unit")
public class DetectorUnit implements Persistable<String>, Serializable {
    @Id
    @Column(name = "mac_address", length = 17, nullable = false)
    private String macAddress;
    @Column(name = "ipv4", length = 15, nullable = false)
    private String ipV4;
    @Column(name = "name")
    private String name;
    @Column(name = "x_loc")
    private int xpos;
    @Column(name = "y_loc")
    private int ypos;
    @ManyToMany
    @JoinTable(
            name = "sensors_join_detector_unit",
            joinColumns = @JoinColumn(name = "detector_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private Set<Sensor> associatedSensorSet;
    @ManyToOne
    @JoinColumn(name = "compartment_id")
    private Compartment compartment;

    @Transient
    private boolean isUpdate;

    public DetectorUnit() {
    }

    public DetectorUnit(String macAddress, String ipv4) {
        this.macAddress = macAddress;
        this.ipV4 = ipv4;
    }

    @Override
    public String getId() {
        return macAddress;
    }

    @Override
    public boolean isNew() {
        return !isUpdate;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpV4() {
        return ipV4;
    }

    public void setIpV4(String ipV4) {
        this.ipV4 = ipV4;
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

    public Set<Sensor> getAssociatedSensorSet() {
        return associatedSensorSet;
    }

    public void setAssociatedSensorSet(Set<Sensor> associatedSensorSet) {
        this.associatedSensorSet = associatedSensorSet;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    @PrePersist
    @PostLoad
    void markAsUpdate() {
        this.isUpdate = true;
    }
    @Override
    public String toString() {
        return "DetectorUnit{" +
                "macAddress='" + macAddress + '\'' +
                ", ipV4='" + ipV4 + '\'' +
                ", name='" + name + '\'' +
                ", xpos=" + xpos +
                ", ypos=" + ypos +
                ", associatedSensorSet=" + associatedSensorSet +
                ", compartment=" + compartment +
                '}';
    }
}

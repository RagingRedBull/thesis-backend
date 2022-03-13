package com.thesis.backend.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "detector_unit")
@Data
public class DetectorUnit {
    @Id
    @Column(name = "mac_address", length = 17, nullable = false)
    private String macAddress;
    @Column(name = "ipv4", length = 15, nullable = false)
    private String ipV4;
    @Column(name = "name")
    private String name;
    @Column(name = "x_loc")
    private Integer xpos;
    @Column(name = "y_loc")
    private Integer ypos;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sensors_join_detector_unit",
            joinColumns = @JoinColumn(name = "detector_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Sensor> associatedSensorSet;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compartment_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Compartment compartment;
}

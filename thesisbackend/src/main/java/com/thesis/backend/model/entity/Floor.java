package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "description", length = 20)
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "associatedFloor")
    private Set<DetectorUnit> associatedDetectorUnitSet;

    public Floor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Floor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", associatedDetectorUnitSet=" + associatedDetectorUnitSet +
                '}';
    }
}

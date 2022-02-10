package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "floors")
public class Floor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "description", length = 20)
    private String description;
    @Column(name = "image_name", unique = true)
    private String imageName;
    @Column(name = "order_position")
    private int order;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "floor")
    private Set<Compartment> compartments;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Set<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(Set<Compartment> compartments) {
        this.compartments = compartments;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

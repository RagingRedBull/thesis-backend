package com.thesis.backend.model.entity.ml;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ml_input")
public class MachineLearningInput {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    @Column(name = "x_origin")
    private int xOrigin;
    @Column(name = "y_origin")
    private int yOrigin;
    @Column(name = "floor_origin")
    private int floorOrigin;
    @Column(name = "time_recorded")
    private LocalDateTime timeRecorded;
}

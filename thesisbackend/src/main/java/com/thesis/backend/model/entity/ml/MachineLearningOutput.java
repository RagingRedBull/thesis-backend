package com.thesis.backend.model.entity.ml;

import com.thesis.backend.model.converter.MachineLearningOutputLogTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ml_output")
public class MachineLearningOutput {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "x_start")
    private int xStart;
    @Column(name = "y_start")
    private int yStart;
    @Column(name = "floor_start")
    private int floorStart;
    @Column(name = "x_end")
    private int xEnd;
    @Column(name = "y_end")
    private int yEnd;
    @Column(name = "floor_end")
    private int floorEnd;
    @Column(name = "type")
    @Convert(converter = MachineLearningOutputLogTypeConverter.class)
    private LogType logType;
}

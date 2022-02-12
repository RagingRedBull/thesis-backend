package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.converter.SensorNameConverter;
import com.thesis.backend.model.converter.SensorTypeConverter;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",
        discriminatorType = DiscriminatorType.STRING)
@Table(name = "sensor_log")
public abstract class SensorLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected long id;
    @Column(name = "name", length = 15)
    @Convert(converter = SensorNameConverter.class)
    protected SensorName name;
    @Column(name = "type", insertable = false, updatable = false)
    @Convert(converter = SensorTypeConverter.class)
    protected SensorType type;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detector_unit_log_id")
    private DetectorUnitLog detectorUnitLog;

    public SensorLog(long id, SensorName name, SensorType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

}

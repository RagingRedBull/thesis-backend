package com.thesis.backend.service;

import com.thesis.backend.config.SensorServiceTestConfig;
import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SensorServiceTestConfig.class})
@WebAppConfiguration()
class SensorServiceTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void given_one_sensor_when_buildingSensorSetUpdateDto_then_return_SensorUpdateDtoSet_with_one_element() {
        Set<SensorUpdateDto> sensorUpdateDtoSet = sensorService.buildSensorSetUpdateDto(buildSensorSet(
                        buildSensorDetails(1, SensorType.DHT, SensorName.DHT11)
                ), true
        );
        SensorUpdateDto dto = sensorUpdateDtoSet.stream().findFirst().get();
        assertThat(sensorUpdateDtoSet.size(), is(1));
        assertThat(dto.getSensorId(), is(1));
        assertThat(dto.isToEnable(), is(true));
    }

    @Test
    void given_multiple_sensors_when_buildingSensorSetUpdateDto_then_return_SensorUpdateDtoSet_with_multiple_elements() {
        Set<SensorUpdateDto> sensorUpdateDtoSet = sensorService.buildSensorSetUpdateDto(buildSensorSet(
                        buildSensorDetails(1, SensorType.DHT, SensorName.DHT11),
                        buildSensorDetails(2, SensorType.DHT, SensorName.DHT22),
                        buildSensorDetails(3, SensorType.MQ, SensorName.MQ7)
                ), true
        );
        assertThat(sensorUpdateDtoSet.size(), is(3));
        assertThat(sensorUpdateDtoSet, everyItem(HasPropertyWithValue.hasProperty("toEnable", is(true))));
        assertThat(sensorUpdateDtoSet, containsInAnyOrder(
                hasProperty("sensorId", is(1)),
                hasProperty("sensorId", is(2)),
                hasProperty("sensorId", is(3))
        ));
    }

    private Set<Sensor> buildSensorSet(Sensor... sensors) {
        return new HashSet<>(Set.of(sensors));
    }

    private Sensor buildSensorDetails(int id, SensorType type, SensorName name) {
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setType(type);
        sensor.setName(name);
        return sensor;
    }
}
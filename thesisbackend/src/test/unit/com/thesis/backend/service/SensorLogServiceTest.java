package com.thesis.backend.service;

import com.thesis.backend.config.SensorLogServiceTestConfig;
import com.thesis.backend.model.dto.sensor.DhtSensorLogDto;
import com.thesis.backend.model.dto.sensor.MqSensorLogDto;
import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@Import(SensorLogServiceTestConfig.class)
@WebMvcTest(SensorLogService.class)
class SensorLogServiceTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SensorLogService sensorLogService;

    @Test
    void givenOneSensorLogDto_whenMapSensorLogDtoToEntity_thenMapOneEntity() {
        DetectorUnitLog detectorUnitLog = buildDetectorUnitLog("E8:DB:84:E5:06:5B");
        Set<SensorLogDto> sensorLogDtoSet = buildSensorLogDtoSet(
                buildDhtSensorLogDto(1,SensorName.DHT11, SensorType.DHT, 1f, 1f)
        );
        Set<SensorLog> sensorLogSet = sensorLogService.mapSensorLogDtoEntitySet(sensorLogDtoSet, detectorUnitLog);
        SensorLog sensorLog = sensorLogSet.stream().findFirst().get();
        assertThat(sensorLog.getClass(), is(typeCompatibleWith(SensorLog.class)));
        assertThat(sensorLog.getDetectorUnitLog(), is(notNullValue()));
        assertThat(sensorLog.getDetectorUnitLog().getMacAddress(), is("E8:DB:84:E5:06:5B"));
        assertThat(sensorLogSet.size(), is(1));
        assertThat(sensorLog.getId(), is(1L));
        assertThat(sensorLog.getType(), is(SensorType.DHT));
        assertThat(sensorLog.getName(), is(SensorName.DHT11));
    }

    @Test
    void givenMultipleSensorLogDto_whenMapSensorLogDtoToEntity_thenMapMultipleEntity() {
        DetectorUnitLog detectorUnitLog = buildDetectorUnitLog("E8:DB:84:E5:06:5B");
        Set<SensorLogDto> sensorLogDtoSet = buildSensorLogDtoSet(
                buildDhtSensorLogDto(1L,SensorName.DHT11, SensorType.DHT, 1F, 1F),
                buildDhtSensorLogDto(2L,SensorName.DHT22, SensorType.DHT, 1F, 1F),
                buildMqSensorLogDto(3L,SensorName.MQ7, SensorType.MQ, 1)
        );
        Set<SensorLog> sensorLogSet = sensorLogService.mapSensorLogDtoEntitySet(sensorLogDtoSet, detectorUnitLog);
        assertThat(sensorLogSet.size(), is(3));
        assertThat(sensorLogSet, hasItems(isA(SensorLog.class)));
        assertThat(sensorLogSet, hasItems(
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is(SensorName.DHT11)),
                        hasProperty("type", is(SensorType.DHT)),
                        hasProperty("temperature", is(1F)),
                        hasProperty("humidity", is(1F)),
                        hasProperty("detectorUnitLog", is(notNullValue()))
                ), allOf(
                        hasProperty("id", is(2L)),
                        hasProperty("name", is(SensorName.DHT22)),
                        hasProperty("type", is(SensorType.DHT)),
                        hasProperty("temperature", is(1F)),
                        hasProperty("humidity", is(1F)),
                        hasProperty("detectorUnitLog", is(notNullValue()))
                ), allOf(
                        hasProperty("id", is(3L)),
                        hasProperty("name", is(SensorName.MQ7)),
                        hasProperty("type", is(SensorType.MQ)),
                        hasProperty("mqValue", is(1)),
                        hasProperty("detectorUnitLog", is(notNullValue()))
                )
        ));
    }

    private LinkedHashSet<SensorLogDto> buildSensorLogDtoSet(SensorLogDto... sensorLogs) {
        return new LinkedHashSet<>(Arrays.asList(sensorLogs));
    }

    private DhtSensorLogDto buildDhtSensorLogDto(long id, SensorName name, SensorType type, float temp,
                                                 float humiditiy) {
        DhtSensorLogDto dhtSensorLogDto = new DhtSensorLogDto(id,type, name);
        dhtSensorLogDto.setTemperature(temp);
        dhtSensorLogDto.setHumidity(humiditiy);
        return dhtSensorLogDto;
    }

    private MqSensorLogDto buildMqSensorLogDto(long id, SensorName name, SensorType type, int mqValue) {
        MqSensorLogDto mqSensorLogDto = new MqSensorLogDto(id,type, name);
        mqSensorLogDto.setMqValue(mqValue);
        return mqSensorLogDto;
    }

    private DetectorUnitLog buildDetectorUnitLog(String macAddress) {
        DetectorUnitLog detectorUnitLog = new DetectorUnitLog();
        detectorUnitLog.setMacAddress(macAddress);
        detectorUnitLog.setTimeRecorded(null);
        detectorUnitLog.setSensorLogSet(new HashSet<>());
        return detectorUnitLog;
    }
}
package com.thesis.backend.service;

import com.thesis.backend.model.dto.SensorStatusDto;
import com.thesis.backend.model.dto.StatusReportLogDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.enums.SensorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final SensorLogService sensorLogService;
    private final DetectorUnitService detectorUnitService;
    public void playFireWarning(String compartmentName) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            for (int i = 0; i < 5; i++) {
                synthesizer.speakPlainText(
                        "WARNING! FIRE DETECTED AT " + compartmentName, null);
                Thread.sleep(1000);
            }
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<StatusReportLogDto> getSensorStatus(LocalDate day) {
        List<DetectorUnit> detectorUnitList = detectorUnitService.getAll();
        List<StatusReportLogDto> statusReportLogDtoList = new ArrayList<>();
        for (DetectorUnit unit : detectorUnitList) {
            List<SensorStatusDto> sensorStatusDtoList = new ArrayList<>();
            StatusReportLogDto statusReportLog = new StatusReportLogDto();
            statusReportLog.setMacAddress(unit.getMacAddress());
            for (int j = 0; j < 24; j++) {
                LocalDateTime dateTimeStart = LocalDateTime.of(day, LocalTime.of(j, 0));
                LocalDateTime dateTimeEnd = LocalDateTime.of(day,LocalTime.of(j, 59));
                unit.getAssociatedSensorSet()
                        .forEach(sensor -> {
                            if(sensor.getType() == SensorType.MQ || sensor.getType() == SensorType.DHT) {
                                SensorStatusDto dto = sensorLogService.getSensorStatus(unit.getMacAddress(),
                                        dateTimeStart,
                                        dateTimeEnd,
                                        sensor.getName(),
                                        sensor.getType());
                                dto.setStart(dateTimeStart.toLocalTime());
                                dto.setEnd(dateTimeEnd.toLocalTime());;
                                dto.setSensorName(sensor.getName());
                                dto.setSensorType(sensor.getType());
                                sensorStatusDtoList.add(dto);
                            }
                        });
            }
            statusReportLog.setSensorStatusDtoList(sensorStatusDtoList);
            statusReportLogDtoList.add(statusReportLog);
        }
        return statusReportLogDtoList;
    }
}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.SensorStatusReportLogDto;
import com.thesis.backend.model.dto.StatusReportLogDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.SensorStatusReportLog;
import com.thesis.backend.model.entity.logs.StatusReportLog;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorStatusReportMapper;
import com.thesis.backend.model.util.mapper.StatusReportLogMapper;
import com.thesis.backend.repository.SensorStatusReportLogRepository;
import com.thesis.backend.repository.StatusReportLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final SensorStatusReportLogRepository sensorStatusReportLogRepository;
    private final StatusReportLogRepository statusReportLogRepository;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "* 59 * * * *")
    @Transactional
    public void generateHourlyLogs() {
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        List<DetectorUnit> detectorUnitList = detectorUnitService.getAll();
        List<StatusReportLog> statusReportLogList = new ArrayList<>();
        LocalDateTime dateTimeStart = LocalDateTime.of(LocalDate.now(),
                LocalTime.MIDNIGHT);
        LocalDateTime dateTimeEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        for (DetectorUnit unit : detectorUnitList) {
            StatusReportLog statusReportLog = new StatusReportLog();
            statusReportLog.setMacAddress(unit.getMacAddress());
            List<SensorStatusReportLog> sensorStatusReportLogList = unit.getAssociatedSensorSet().stream().map(
                            sensor -> sensorLogService.getSensorStatusByMacAddressBetweenStartEndDate(
                                    unit.getMacAddress(),
                                    dateTimeStart,
                                    dateTimeEnd,
                                    sensor.getName(),
                                    sensor.getType()))
                    .map(sensorStatusMapper::mapToEntity)
                    .collect(Collectors.toList());
            sensorStatusReportLogList.forEach(sensorStatusReportLog ->
                    sensorStatusReportLog.setStatusReportLog(statusReportLog));
            statusReportLog.setSensorStatusReportLogs(sensorStatusReportLogList);
            statusReportLog.setDateStart(dateTimeStart);
            statusReportLog.setDateEnd(dateTimeEnd);
            statusReportLogList.add(statusReportLog);
        }
        statusReportLogRepository.saveAll(statusReportLogList);
    }
    public void generateStatusReportLog(LocalDate date) {
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        List<DetectorUnit> detectorUnitList = detectorUnitService.getAll();
        List<StatusReportLog> statusReportLogList = new ArrayList<>();
        for (DetectorUnit unit : detectorUnitList) {
            for (int i = 0; i < 24; i++) {
                LocalDateTime dateTimeStart = LocalDateTime.of(date,
                        LocalTime.of(i,0,0,0));
                LocalDateTime dateTimeEnd = LocalDateTime.of(date,
                        LocalTime.of(i,59,59,999999999));
                StatusReportLog statusReportLog = new StatusReportLog();
                statusReportLog.setMacAddress(unit.getMacAddress());
                List<SensorStatusReportLog> sensorStatusReportLogList = unit.getAssociatedSensorSet().stream().map(
                                sensor -> sensorLogService.getSensorStatusByMacAddressBetweenStartEndDate(
                                        unit.getMacAddress(),
                                        dateTimeStart,
                                        dateTimeEnd,
                                        sensor.getName(),
                                        sensor.getType()))
                        .map(sensorStatusMapper::mapToEntity)
                        .collect(Collectors.toList());
                sensorStatusReportLogList.forEach(sensorStatusReportLog ->
                        sensorStatusReportLog.setStatusReportLog(statusReportLog));
                statusReportLog.setSensorStatusReportLogs(sensorStatusReportLogList);
                statusReportLog.setDateStart(dateTimeStart);
                statusReportLog.setDateEnd(dateTimeEnd);
                statusReportLogList.add(statusReportLog);
            }
        }
        statusReportLogRepository.saveAll(statusReportLogList);
    }
    public Page<StatusReportLogDto> generateStatusReportLog(LocalDate date, Pageable page) {
        LocalDateTime startDt = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endDt = LocalDateTime.of(date, LocalTime.MAX);
        Page<StatusReportLog> statusReportLogs = statusReportLogRepository.getAllStatusReportLogsByDay(startDt,endDt,page);
        return statusReportLogs.map(this::buildSensorStatusReportLogDto);
    }

    private StatusReportLogDto buildSensorStatusReportLogDto(StatusReportLog statusReportLog) {
        StatusReportLogDto dto;
        EntityMapper<StatusReportLog, StatusReportLogDto> statusReportLogMapper = new StatusReportLogMapper();
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        dto = statusReportLogMapper.mapToDto(statusReportLog);
        dto.setSensorStatusReportLogDtoList(statusReportLog.getSensorStatusReportLogs().stream()
                .map(sensorStatusMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}

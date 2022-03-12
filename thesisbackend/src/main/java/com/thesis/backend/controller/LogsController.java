package com.thesis.backend.controller;

import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.DetectorUnitLogMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/log")
public class LogsController {
    private final DetectorUnitLogService detectorUnitLogService;
    private final DetectorUnitService detectorUnitService;
    private final SensorLogService sensorLogService;
    private final CompartmentService compartmentService;
    private final ReportService reportService;
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitLogDto>> getAllLogsPaged(@RequestParam int pageNumber,
                                                                    @RequestParam int pageSize) {
        log.info("Request Entity: Page of Detector Unit LOGS");
        log.info("Requested Size: " + pageSize);
        log.info("Requested Page No: " + pageNumber);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "timeRecorded"));
        return ResponseEntity.ok(detectorUnitLogService.findDetectorLogsByPage(page));
    }

    @GetMapping(path = "/{detectorUnitLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetectorUnitLogDto> getOneById(@PathVariable long detectorUnitLogId)
            throws EntityNotFoundException {
        log.debug(String.valueOf(detectorUnitLogId));
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> detectorLogMapper = new DetectorUnitLogMapper();
        EntityMapper<SensorLog, SensorLogDto> sensorMapper = new SensorLogMapper();
        DetectorUnitLog log = detectorUnitLogService.findOneByPrimaryKey(detectorUnitLogId);
        DetectorUnitLogDto dto = detectorLogMapper.mapToDto(log);
        dto.setSensorLogSet(log.getSensorLogSet().stream().map(sensorMapper::mapToDto).collect(Collectors.toSet()));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{detectorUnitLogId}/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<SensorLogDto>> getSensorLogsByDetectorUnitLog(@PathVariable long detectorUnitLogId) {
        log.debug("Requested Sensors with Log ID: " + detectorUnitLogId);
        Set<SensorLogDto> sensorLogDtos =
                sensorLogService.mapSensorLogEntityToDto(sensorLogService.findLogsByDetectorLogId(detectorUnitLogId));
        return ResponseEntity.ok(sensorLogDtos);
    }

    @GetMapping(path = "/compartment/{compartmentId}")
    public ResponseEntity<Set<DetectorUnitLogDto>> getSensorLogsByCompartment(@PathVariable int compartmentId) {
        log.debug("Compartment Log ID: " + compartmentId);
        Compartment compartment = compartmentService.findOneByPrimaryKey(compartmentId);
        Set<String> detectorUnitMacAdresses = compartment.getDetectorUnits()
                .stream()
                .map(DetectorUnit::getMacAddress)
                .collect(Collectors.toSet());
        log.debug(detectorUnitMacAdresses.toString());
        log.debug(compartment.getDetectorUnits().toString());
        Set<DetectorUnitLog> logs = detectorUnitLogService.findDetectorLogsByDetectorUnitId(detectorUnitMacAdresses
                , Sort.by(Sort.Order.desc("timeRecorded")));
        Set<DetectorUnitLogDto> dtos = logs.stream()
                .map(detectorUnitLogService::buildDetectorUnitLogDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping(path = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto) {
        log.debug(detectorUnitLogDto.toString());
        DetectorUnitLog detectorUnitLog = detectorUnitLogService.saveOne(detectorUnitLogDto);
        detectorUnitLogService.checkReadings(detectorUnitLog);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/date")
    public ResponseEntity<List<DetectorUnitLog>> getAllDetectorLogsByDateRange() {
        return ResponseEntity.ok(detectorUnitLogService.findAllByDateRange());
    }

    @GetMapping(path = "/status-report")
    public ResponseEntity<Object> getStatusReportLogs(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return ResponseEntity.ok(reportService.generateStatusReportLog(day));
    }
}

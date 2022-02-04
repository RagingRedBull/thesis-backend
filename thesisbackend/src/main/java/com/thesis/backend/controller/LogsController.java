package com.thesis.backend.controller;

import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.response.CompartmentLogResponse;
import com.thesis.backend.model.util.mapper.DetectorUnitLogMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.service.CompartmentService;
import com.thesis.backend.service.DetectorUnitLogService;
import com.thesis.backend.service.SensorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/log")
public class LogsController {
    private final Logger logger = LoggerFactory.getLogger(LogsController.class);
    private DetectorUnitLogService detectorUnitLogService;
    private SensorLogService sensorLogService;
    private CompartmentService compartmentService;

    public LogsController(DetectorUnitLogService detectorUnitLogService, SensorLogService sensorLogService, CompartmentService compartmentService) {
        this.detectorUnitLogService = detectorUnitLogService;
        this.sensorLogService = sensorLogService;
        this.compartmentService = compartmentService;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitLogDto>> getAllLogsPaged(@RequestParam int pageNumber,
                                                                    @RequestParam int pageSize) {
        logger.info("Request Entity: Page of Detector Unit LOGS");
        logger.info("Requested Size: " + pageSize);
        logger.info("Requested Page No: " + pageNumber);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "timeRecorded"));
        return ResponseEntity.ok(detectorUnitLogService.findDetectorLogsByPage(page));
    }

    @GetMapping(path = "/{detectorUnitLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetectorUnitLogDto> getOneById(@PathVariable long detectorUnitLogId)
            throws EntityNotFoundException {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> detectorLogMapper = new DetectorUnitLogMapper();
        EntityMapper<SensorLog, SensorLogDto> sensorMapper = new SensorLogMapper();
        DetectorUnitLog log = detectorUnitLogService.findOneByPrimaryKey(detectorUnitLogId);
        DetectorUnitLogDto dto = detectorLogMapper.mapToDto(log);
        dto.setSensorLogSet(log.getSensorLogSet().stream().map(sensorMapper::mapToDto).collect(Collectors.toSet()));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{detectorUnitLogId}/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<SensorLogDto>> getSensorLogsByDetectorUnitLog(@PathVariable long detectorUnitLogId) {
        logger.info("Requested Sensors with Log ID: " + detectorUnitLogId);
        Set<SensorLogDto> sensorLogDtos =
                sensorLogService.mapSensorLogEntityToDto(sensorLogService.findLogsByDetectorLogId(detectorUnitLogId));
        return ResponseEntity.ok(sensorLogDtos);
    }

    @GetMapping(path = "/compartment/{compartmentId}")
    public ResponseEntity<Set<DetectorUnitLogDto>> getSensorLogsByCompartment(@PathVariable int compartmentId) {
        logger.info("Compartment Log ID: " + compartmentId);
        Compartment compartment = compartmentService.findOneByPrimaryKey(compartmentId);
        Set<String> detectorUnitMacAdresses = compartment.getDetectorUnits()
                .stream()
                .map(DetectorUnit::getMacAddress)
                .collect(Collectors.toSet());
        logger.info(detectorUnitMacAdresses.toString());
        logger.info(compartment.getDetectorUnits().toString());
        Set<DetectorUnitLog> logs = detectorUnitLogService.findDetectorLogsByDetectorUnitId(detectorUnitMacAdresses
                , Sort.by(Sort.Order.desc("timeRecorded")));
        Set<DetectorUnitLogDto> dtos = logs.stream()
                .map(this::buildDetectorUnitLogDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping(path = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto) {
        logger.info(detectorUnitLogDto.toString());
        DetectorUnitLog log = detectorUnitLogService.saveOne(detectorUnitLogDto);
        return ResponseEntity.ok("Uploaded!" + "\n" + "Row ID: " + log.getId());
    }

    private DetectorUnitLogDto buildDetectorUnitLogDto(DetectorUnitLog log) {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> detectorLogMapper = new DetectorUnitLogMapper();
        EntityMapper<SensorLog, SensorLogDto> sensorMapper = new SensorLogMapper();
        DetectorUnitLogDto dto = detectorLogMapper.mapToDto(log);
        dto.setSensorLogSet(log.getSensorLogSet().stream()
                .map(sensorMapper::mapToDto)
                .collect(Collectors.toSet()));
        return dto;
    }
}

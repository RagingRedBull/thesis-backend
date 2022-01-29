package com.thesis.backend.controller;

import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.DetectorUnitLogEntityMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogEntityMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/log")
public class LogsController {
    private final Logger logger = LoggerFactory.getLogger(LogsController.class);
    private DetectorUnitLogService detectorUnitLogService;
    private SensorLogService sensorLogService;

    public LogsController(DetectorUnitLogService detectorUnitLogService, SensorLogService sensorLogService) {
        this.detectorUnitLogService = detectorUnitLogService;
        this.sensorLogService = sensorLogService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitLogDto>> getAllLogsPaged(@RequestParam(required = false) Integer pageNumber,
                                                                    @RequestParam(required = false) Integer pageSize) {
        if (pageNumber == null || pageSize == null) {
            pageNumber = 0;
            pageSize = 10;
        }
        logger.info("Request Entity: Page of Detector Unit LOGS");
        logger.info("Requested Size: " + pageSize);
        logger.info("Requested Page No: " + pageNumber);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "timeRecorded"));
        return new ResponseEntity<>(detectorUnitLogService.findDetectorLogsByPage(page), HttpStatus.OK);
    }
    @GetMapping(path = "/{detectorUnitLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetectorUnitLogDto> getOneById(@PathVariable long detectorUnitLogId)
            throws EntityNotFoundException {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> detectorLogMapper = new DetectorUnitLogEntityMapper();
        EntityMapper<SensorLog,SensorLogDto> sensorMapper = new SensorLogEntityMapper();
        DetectorUnitLog log = detectorUnitLogService.findOneByPrimaryKey(detectorUnitLogId);
        DetectorUnitLogDto dto = detectorLogMapper.mapToDto(log);
        dto.setSensorLogSet(log.getSensorLogSet().stream().map(sensorMapper::mapToDto).collect(Collectors.toSet()));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping(path = "/{detectorUnitLogId}/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<SensorLogDto>> getSensorsOfDetectorUnit(@PathVariable long detectorUnitLogId) {
        logger.info("Requested Sensors with Log ID: " + detectorUnitLogId);
        Set<SensorLogDto> sensorLogDtos =
                sensorLogService.mapSensorLogEntityToDto(sensorLogService.findLogsByDetectorLogId(detectorUnitLogId));
        return new ResponseEntity<>(sensorLogDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto) {
        logger.info(detectorUnitLogDto.toString());
        DetectorUnitLog log = detectorUnitLogService.saveOne(detectorUnitLogDto);
        return new ResponseEntity<>("Uploaded!" + "\n" + "Row ID: " + log.getId(), HttpStatus.OK);
    }
}

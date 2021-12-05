package com.thesis.backend.controller;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.service.DetectorUnitLogService;
import com.thesis.backend.service.SensorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllLogsPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
        logger.info("GETTING PAGE # " + pageNumber + " WITH SIZE OF " + pageSize);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "timeRecorded"));
        return new ResponseEntity<>(detectorUnitLogService.findDetectorLogsByPage(page), HttpStatus.OK);
    }

    @GetMapping(path = "/sensor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSensorsOfDetectorUnit(@RequestParam long detectorUnitLogId) {
        logger.info("GETTING SENSOR INFO of DetectorLog: " + detectorUnitLogId);
        Set<SensorLogDto> sensorLogDtos =
                sensorLogService.mapSensorLogEntityToDto(sensorLogService.findLogsByDetectorLogId(detectorUnitLogId));
        return new ResponseEntity<>(sensorLogDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto) {
        logger.info(detectorUnitLogDto.toString());
        detectorUnitLogService.saveOne(detectorUnitLogDto);
        return new ResponseEntity<>("LOGGED!", HttpStatus.OK);
    }

}

package com.thesis.backend.controller;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.service.DetectorUnitLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/logs")
public class LogsController {
    DetectorUnitLogService detectorUnitLogService;

    public LogsController(DetectorUnitLogService detectorUnitLogService) {
        this.detectorUnitLogService = detectorUnitLogService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllLogsPaged(){
        return new ResponseEntity<>("Logged!",HttpStatus.OK);
    }

    @PostMapping(path = "/upload", consumes = "application/json")
    public ResponseEntity<?> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto){
        detectorUnitLogService.persist(detectorUnitLogDto);
        return new ResponseEntity<>("Logged!",HttpStatus.OK);
    }

}

package com.thesis.backend.controller;

import com.thesis.backend.model.dto.DetectorUnitLogDto;
import com.thesis.backend.service.DetectorUnitLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectorUnitLogController {
    DetectorUnitLogService detectorUnitLogService;

    public DetectorUnitLogController(DetectorUnitLogService detectorUnitLogService) {
        this.detectorUnitLogService = detectorUnitLogService;
    }

    @PostMapping(path = "/detector/upload", consumes = "application/json")
    public ResponseEntity<?> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto){
        detectorUnitLogService.persist(detectorUnitLogDto);
        return new ResponseEntity<>("Logged!",HttpStatus.OK);
    }
}

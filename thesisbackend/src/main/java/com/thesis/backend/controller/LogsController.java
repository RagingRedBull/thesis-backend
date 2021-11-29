package com.thesis.backend.controller;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.service.DetectorUnitLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogsController {
    private final Logger logger = LoggerFactory.getLogger(LogsController.class);
    private DetectorUnitLogService detectorUnitLogService;

    public LogsController(DetectorUnitLogService detectorUnitLogService) {
        this.detectorUnitLogService = detectorUnitLogService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllLogsPaged(@RequestParam int pageNumber, @RequestParam int pageSize){
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "timeRecorded"));
        return new ResponseEntity<>(detectorUnitLogService.findDetectorLogsByPage(page), HttpStatus.OK);
    }

    @PostMapping(path = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadLog(@RequestBody DetectorUnitLogDto detectorUnitLogDto){
        detectorUnitLogService.persist(detectorUnitLogDto);
        return new ResponseEntity<>("Logged!",HttpStatus.OK);
    }

}

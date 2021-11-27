package com.thesis.backend.controller;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.service.DetectorUnitLogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/logs")
public class LogsController {
    private DetectorUnitLogService detectorUnitLogService;

    public LogsController(DetectorUnitLogService detectorUnitLogService) {
        this.detectorUnitLogService = detectorUnitLogService;
    }

    @GetMapping()
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

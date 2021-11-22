package com.thesis.backend.controller;

import com.thesis.backend.service.FloorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/floor")
public class FloorController {
    private FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllFloor(){
        return new ResponseEntity<>("eheh", HttpStatus.OK);
    }
    @GetMapping(path = "/{floorId}", produces = "application/json")
    public ResponseEntity<?> getFloor(int floorId) {
        return new ResponseEntity<>("single floor", HttpStatus.OK);
    }
}

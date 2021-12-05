package com.thesis.backend.controller;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.service.FloorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/floor")
public class FloorController {
    private FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(@RequestParam int pageNumber, @RequestParam int pageSize){
        return new ResponseEntity<>("eheh", HttpStatus.OK);
    }
    @GetMapping(path = "/{floorId}", produces = "application/json")
    public ResponseEntity<?> getOne(int floorId) {
        return new ResponseEntity<>("single floor", HttpStatus.OK);
    }

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFloor(@RequestBody FloorDto floorDto) {
        return null;
    }
}

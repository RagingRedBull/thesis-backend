package com.thesis.backend.controller;

import com.thesis.backend.model.dto.MachineLearningOutputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ml")
public class MachineLearningController {
    @PostMapping(path = "/output/new")
    public ResponseEntity<Object> insertNewOutput(@RequestBody MachineLearningOutputDto dto) {
        return null;
    }
    @GetMapping(path = "/output")
    public ResponseEntity<Object> getLatestInput() {
        return null;
    }
}

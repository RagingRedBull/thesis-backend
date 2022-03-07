package com.thesis.backend.controller;

import com.thesis.backend.model.dto.MachineLearningInputDto;
import com.thesis.backend.model.dto.MachineLearningOutputDto;
import com.thesis.backend.model.entity.MachineLearningInput;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.MachineLearningInputMapper;
import com.thesis.backend.service.MachineLearningInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ml")
@Slf4j
@RequiredArgsConstructor
public class MachineLearningController {
    private final MachineLearningInputService machineLearningInputService;
    @PostMapping(path = "/output/new")
    public ResponseEntity<Object> insertNewOutput(@RequestBody MachineLearningOutputDto dto) {
        return null;
    }
    @GetMapping(path = "/input", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLatestInput() {
        EntityMapper<MachineLearningInput, MachineLearningInputDto> mapper = new MachineLearningInputMapper();
        MachineLearningInput machineLearningInput = machineLearningInputService.getLatestInput();
        return ResponseEntity.ok(mapper.mapToDto(machineLearningInput));
    }
}

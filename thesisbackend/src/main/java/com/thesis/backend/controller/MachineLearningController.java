package com.thesis.backend.controller;

import com.thesis.backend.model.dto.MachineLearningInputDto;
import com.thesis.backend.model.dto.MachineLearningOutputDto;
import com.thesis.backend.model.entity.ml.MachineLearningInput;
import com.thesis.backend.model.entity.ml.MachineLearningOutput;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.MachineLearningInputMapper;
import com.thesis.backend.model.util.mapper.MachineLearningOutputMapper;
import com.thesis.backend.service.MachineLearningInputService;
import com.thesis.backend.service.MachineLearningOutputService;
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
    private final MachineLearningOutputService machineLearningOutputService;

    @PostMapping(path = "/output/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertNewOutput(@RequestBody MachineLearningOutputDto dto) {
        return ResponseEntity.ok(machineLearningOutputService.saveOne(dto));
    }
    @GetMapping(path = "/output/heat")
    public ResponseEntity<Object> getLatestHeatOutput() {
        EntityMapper<MachineLearningOutput, MachineLearningOutputDto> mapper = new MachineLearningOutputMapper();
        MachineLearningOutput entity = machineLearningOutputService.getLatestOutput("HEAT");
        return ResponseEntity.ok(mapper.mapToDto(entity));
    }
    @GetMapping(path = "/output/smoke")
    public ResponseEntity<Object> getLatestSmokeOutput() {
        EntityMapper<MachineLearningOutput, MachineLearningOutputDto> mapper = new MachineLearningOutputMapper();
        MachineLearningOutput entity = machineLearningOutputService.getLatestOutput("SMOKE");
        return ResponseEntity.ok(mapper.mapToDto(entity));
    }
    @GetMapping(path = "/input", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLatestInput() {
        EntityMapper<MachineLearningInput, MachineLearningInputDto> mapper = new MachineLearningInputMapper();
        MachineLearningInput machineLearningInput = machineLearningInputService.getLatestInput();
        return ResponseEntity.ok(mapper.mapToDto(machineLearningInput));
    }
}

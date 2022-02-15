package com.thesis.backend.controller;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.response.CompartmentLogResponse;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.service.CompartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/compartment")
public class CompartmentController {
    private final CompartmentService compartmentService;

    @GetMapping
    public ResponseEntity<Set<CompartmentDto>> getCompartmentsByFloorId(@RequestParam int floorId) {
        Set<Compartment> compartments = compartmentService.findCompartmentsByFloorId(floorId);
        Set<CompartmentDto> compartmentDtos = compartmentService.convertEntitySetToDto(compartments);
        return ResponseEntity.ok(compartmentDtos);
    }
    @PostMapping(path = "/new")
    public ResponseEntity<CompartmentDto> insertOne(@RequestBody CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        compartmentDto = mapper.mapToDto(compartmentService.saveOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<CompartmentDto> getOne(@PathVariable int compartmentId) {
        EntityMapper<Compartment, CompartmentDto> entityMapper = new CompartmentMapper();
        CompartmentDto dto = entityMapper.mapToDto(compartmentService.findOneByPrimaryKey(compartmentId));
        return ResponseEntity.ok(dto);
    }
    @PutMapping(path = "/update")
    public ResponseEntity<CompartmentDto> updateOne(@RequestBody CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        compartmentDto = mapper.mapToDto(compartmentService.updateOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }
    @GetMapping(path = "/{id}/logs")
    public ResponseEntity<CompartmentLogResponse> getCompartmentLogs(@PathVariable int compartmentId) {

        return null;
    }
}

package com.thesis.backend.controller;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.response.CompartmentLogResponse;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.service.CompartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.JacksonSerializers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/compartment")
public class CompartmentController {
    private final CompartmentService compartmentService;

    @GetMapping
    public ResponseEntity<List<CompartmentDto>> getCompartmentsByFloorId(@RequestParam int floorId) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        List<Compartment> compartments = compartmentService.findCompartmentsByFloorId(floorId);
        List<CompartmentDto> compartmentDtos = compartments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compartmentDtos);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompartmentDto> insertOne(@RequestBody CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        compartmentDto = mapper.mapToDto(compartmentService.saveOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompartmentDto> getOne(@PathVariable int compartmentId) {
        EntityMapper<Compartment, CompartmentDto> entityMapper = new CompartmentMapper();
        CompartmentDto dto = entityMapper.mapToDto(compartmentService.findOneByPrimaryKey(compartmentId));
        return ResponseEntity.ok(dto);
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompartmentDto> updateOne(@RequestBody CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        compartmentDto = mapper.mapToDto(compartmentService.updateOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }

    @DeleteMapping(path = "/{compartmentId}/delete")
    public ResponseEntity<Object> deleteOne(@PathVariable int compartmentId) {
        compartmentService.deleteOne(compartmentId);
        return ResponseEntity.ok("Deleted");
    }
    @GetMapping(path = "/{id}/logs")
    public ResponseEntity<CompartmentLogResponse> getCompartmentLogs(@PathVariable int compartmentId) {

        return null;
    }
}

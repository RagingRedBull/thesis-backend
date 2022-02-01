package com.thesis.backend.controller;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.service.CompartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/compartment")
public class CompartmentController {
    private CompartmentService compartmentService;

    public CompartmentController(CompartmentService compartmentService) {
        this.compartmentService = compartmentService;
    }

    @GetMapping
    public ResponseEntity<Set<CompartmentDto>> getCompartmentsByFloor(@RequestParam int floorId) {
        CompartmentMapper compartmentMapper = new CompartmentMapper();
        Set<CompartmentDto> compartmentDtos = compartmentService.findCompartmentsByFloorId(floorId)
                .stream()
                .map(compartmentMapper::mapToDto)
                .collect(Collectors.toSet());

        return new ResponseEntity<>(compartmentDtos, HttpStatus.OK);
    }
}

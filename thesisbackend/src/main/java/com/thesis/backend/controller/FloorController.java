package com.thesis.backend.controller;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorMapper;
import com.thesis.backend.service.CompartmentService;
import com.thesis.backend.service.FloorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/floor")
public class FloorController {
    private final Logger logger = LoggerFactory.getLogger(FloorController.class);
    private final FloorService floorService;
    private final CompartmentService compartmentService;

    public FloorController(FloorService floorService, CompartmentService compartmentService) {
        this.floorService = floorService;
        this.compartmentService = compartmentService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Page<FloorDto>> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        logger.info("Page Size: " + pageSize);
        logger.info("Page: " + (pageNumber - 1));
        return ResponseEntity.ok(floorService.getAllFloorByPage(pageNumber, pageSize));
    }

    @GetMapping(path = "/{floorId}", produces = "application/json")
    public ResponseEntity<FloorDto> getOne(@PathVariable int floorId) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        FloorDto dto = mapper.mapToDto(floorService.findOneByPrimaryKey(floorId));
        logger.info("Floor ID " + dto.getId() + " found.");
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{floorId}/compartment", produces = "application/json")
    public ResponseEntity<Set<CompartmentDto>> getCompartmentByFloorId(@PathVariable int floorId) {
        Set<Compartment> compartments = compartmentService.findCompartmentsByFloorId(floorId);
        Set<CompartmentDto> compartmentDtos = compartmentService.convertEntitySetToDto(compartments);
        return ResponseEntity.ok()
                .body(compartmentDtos);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> addFloor(@RequestBody FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        floorDto = mapper.mapToDto(floorService.saveOne(floorDto));
        return ResponseEntity.ok(floorDto);
    }

    @PostMapping(path = "/{floorId}/compartment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompartmentDto> addCompartment(CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> compartmentMapper = new CompartmentMapper();
        compartmentDto = compartmentMapper.mapToDto(compartmentService.saveOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }
}

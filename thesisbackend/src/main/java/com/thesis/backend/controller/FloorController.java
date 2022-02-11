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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FloorController {
    private final Logger logger = LoggerFactory.getLogger(FloorController.class);
    private final FloorService floorService;
    private final CompartmentService compartmentService;

    @GetMapping(path = "/all")
    public ResponseEntity<Page<FloorDto>> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        logger.debug("Page Size: " + pageSize);
        logger.debug("Page: " + (pageNumber - 1));
        return ResponseEntity.ok(floorService.getAllFloorByPage(pageNumber, pageSize));
    }

    @GetMapping(path = "/{floorId}", produces = "application/json")
    public ResponseEntity<FloorDto> getOne(@PathVariable int floorId) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        FloorDto dto = mapper.mapToDto(floorService.findOneByPrimaryKey(floorId));
        logger.debug("Floor ID " + dto.getId() + " found.");
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{floorId}/compartment", produces = "application/json")
    public ResponseEntity<Set<CompartmentDto>> getCompartmentByFloorId(@PathVariable int floorId) {
        Set<Compartment> compartments = compartmentService.findCompartmentsByFloorId(floorId);
        Set<CompartmentDto> compartmentDtos = compartmentService.convertEntitySetToDto(compartments);
        return ResponseEntity.ok(compartmentDtos);
    }

    @PutMapping(path = "/{floorId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> updateFloorInfo(@PathVariable int floorId, @RequestBody FloorDto dto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        Floor floor = floorService.updateOne(dto,floorId);
        return ResponseEntity.ok(mapper.mapToDto(floor));
    }
    @PutMapping(path = "/{floorId}/compartment/update")
    public ResponseEntity<CompartmentDto> updateCompartmentInfo(@RequestBody CompartmentDto dto) {
        return ResponseEntity.ok(null);
    }
    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> addFloor(@RequestBody FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        floorDto = mapper.mapToDto(floorService.saveOne(floorDto));
        return ResponseEntity.ok(floorDto);
    }

    @DeleteMapping(path = "/{floorId}/delete")
    public ResponseEntity<Object> deleteFloor(@PathVariable int floorId) {
        floorService.deleteOne(floorId);
        return ResponseEntity.ok("Success");
    }
    @PostMapping(path = "/{floorId}/compartment/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompartmentDto> addCompartment(@PathVariable int floorId,
                                                         CompartmentDto compartmentDto) {
        compartmentDto.setFloorId(floorId);
        EntityMapper<Compartment, CompartmentDto> compartmentMapper = new CompartmentMapper();
        compartmentDto = compartmentMapper.mapToDto(compartmentService.saveOne(compartmentDto));
        return ResponseEntity.ok(compartmentDto);
    }
}

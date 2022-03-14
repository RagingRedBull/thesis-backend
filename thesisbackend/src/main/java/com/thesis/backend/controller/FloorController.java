package com.thesis.backend.controller;

import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.DetectorUnitMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorMapper;
import com.thesis.backend.service.AuthenticationService;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.FloorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/floor")
public class FloorController {
    private final FloorService floorService;
    private final AuthenticationService authenticationService;
    private final DetectorUnitService detectorUnitService;

    @GetMapping(path = "/all")
    public ResponseEntity<Page<FloorDto>> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        log.debug("Page Size: " + pageSize);
        log.debug("Page: " + (pageNumber - 1));
        return ResponseEntity.ok(floorService.getAllFloorByPage(pageNumber, pageSize));
    }

    @GetMapping(path = "/{floorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> getOne(@PathVariable int floorId) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        FloorDto dto = mapper.mapToDto(floorService.findOneByPrimaryKey(floorId));
        log.debug("Floor ID " + dto.getId() + " found.");
        return ResponseEntity.ok(dto);
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> updateFloorInfo(@RequestBody FloorDto dto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        Floor floor = floorService.updateOne(dto);
        return ResponseEntity.ok(mapper.mapToDto(floor));
    }
    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> addFloor(@RequestBody FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        floorDto = mapper.mapToDto(floorService.saveOne(floorDto));
        return ResponseEntity.ok(floorDto);
    }

    @DeleteMapping(path = "/{floorId}/delete")
    public ResponseEntity<Object> deleteFloor(@PathVariable int floorId, Authentication authentication) {
        String username = authenticationService.getKeycloakPrincipal(authentication).getEmail();
        floorService.deleteOne(floorId);
        log.info(username + " DELETED FLOOR " + floorId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(path = "/{floorId}/detectors")
    public ResponseEntity<List<DetectorUnitDto>> getDetectorsByFloorId(@PathVariable int floorId) {
        EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitMapper();
        List<DetectorUnitDto> dtoList = detectorUnitService.getAllDetectorUnitsByFloorId(floorId).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}

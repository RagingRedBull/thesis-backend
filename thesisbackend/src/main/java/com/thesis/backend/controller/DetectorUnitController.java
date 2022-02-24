package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import com.thesis.backend.model.dto.update.DetectorUnitCompartmentUpdateDto;
import com.thesis.backend.model.dto.update.DetectorUnitSensorUpdateWrapper;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.util.mapper.DetectorUnitLogMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorLogMapper;
import com.thesis.backend.service.DetectorUnitLogService;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorLogService;
import com.thesis.backend.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/detector")
public class DetectorUnitController {
    private final DetectorUnitService detectorUnitService;
    private final DetectorUnitLogService detectorUnitLogService;
    private final SensorLogService sensorLogService;
    private final SensorService sensorService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitDto>> getAllDetectorInfoByPage(@RequestParam int pageNumber,
                                                                          @RequestParam int pageSize) {
        log.debug("Entity: Paged Detector Units");
        log.debug("Requested Size: " + pageSize);
        log.debug("Requested Page No: " + pageNumber);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "macAddress"));
        return ResponseEntity.ok(detectorUnitService.findDetectorUnitsByPage(page));
    }
    @GetMapping
    public ResponseEntity<Object> getAllDetectorsByCompartment(@RequestParam int compartmentId) {
        return null;
    }
    @GetMapping(path = "/{macAddress}")
    public ResponseEntity<String> getSensorSetOfDetectorUnit(@PathVariable String macAddress,
                                                             @RequestParam(name = "ipV4") String ipV4)
            throws JsonProcessingException {
        DetectorUnitDto detectorUnitDto = new DetectorUnitDto();
        detectorUnitDto.setMacAddress(macAddress);
        detectorUnitDto.setIpV4(ipV4);
        log.debug(detectorUnitDto.getMacAddress());
        String sensorSetJson = detectorUnitService.buildSensorSetJSON(detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress()));
        return ResponseEntity.ok(sensorSetJson);
    }

    @DeleteMapping(path = "/{macAddress}/delete")
    public ResponseEntity<Object> deleteDetectorUnit(@PathVariable String macAddress) {
        detectorUnitService.deleteOne(macAddress);
        return ResponseEntity.ok("Deleted");
    }
    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerNewDetectorUnit(@RequestBody DetectorUnitDto detectorUnitDto) throws EntityNotFoundException{
        DetectorUnit entity;
        try {
            entity = detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress());
            detectorUnitDto.setIpV4(entity.getIpV4());
        } catch (PrmtsEntityNotFoundException exception) {
            log.info("Registering Unit: " + detectorUnitDto.getMacAddress());
        }
        detectorUnitService.saveOne(detectorUnitDto);
        log.debug("DTO: " + detectorUnitDto);
        return ResponseEntity.ok(detectorUnitDto);
    }
    @GetMapping(path = "/log/latest")
    public ResponseEntity<DetectorUnitLogDto> getLatestLog(@RequestParam String macAddress) {
        EntityMapper<DetectorUnitLog, DetectorUnitLogDto> mapper = new DetectorUnitLogMapper();
        EntityMapper<SensorLog, SensorLogDto> sensorLogMapper = new SensorLogMapper();
        DetectorUnitLog detectorUnitLog = detectorUnitLogService.findLatestLog(macAddress);
        List<SensorLog> sensorLogs = sensorLogService.findLogsByDetectorLogId(detectorUnitLog.getId());
        DetectorUnitLogDto dto = mapper.mapToDto(detectorUnitLog);
        dto.setSensorLogSet(sensorLogs.stream()
                .map(sensorLogMapper::mapToDto)
                .collect(Collectors.toSet()));
        return ResponseEntity.ok(dto);
    }
    @PatchMapping(path = "/update/sensors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSensorList(@RequestBody DetectorUnitSensorUpdateWrapper detectorUnitSensorUpdateWrapper) throws JsonProcessingException {
        detectorUnitService.updateSensorList(detectorUnitSensorUpdateWrapper);
        return ResponseEntity.ok(detectorUnitSensorUpdateWrapper.toString());
    }

    @PutMapping(path = "/update/compartment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateAssociatedFloor(@RequestBody DetectorUnitCompartmentUpdateDto compartmentUpdateDto) {
        DetectorUnitDto detectorUnitDto = detectorUnitService.updateAssociatedCompartment(compartmentUpdateDto);
        return ResponseEntity.ok(detectorUnitDto);
    }
}

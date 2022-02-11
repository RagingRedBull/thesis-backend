package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.dto.update.DetectorUnitUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "/detector")
@RequiredArgsConstructor
public class DetectorUnitController {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitController.class);
    private final DetectorUnitService detectorUnitService;
    private final SensorService sensorService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitDto>> getAllDetectorInfoByPage(@RequestParam int pageNumber,
                                                                          @RequestParam int pageSize) {
        logger.debug("Entity: Paged Detector Units");
        logger.debug("Requested Size: " + pageSize);
        logger.debug("Requested Page No: " + pageNumber);
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "macAddress"));
        return ResponseEntity.ok(detectorUnitService.findDetectorUnitsByPage(page));
    }

    @GetMapping(path = "/{macAddress}")
    public ResponseEntity<String> getSensorSetOfDetectorUnit(@PathVariable String macAddress,
                                                             @RequestParam(name = "ipV4") String ipV4)
            throws JsonProcessingException {
        DetectorUnitDto detectorUnitDto = new DetectorUnitDto();
        detectorUnitDto.setMacAddress(macAddress);
        detectorUnitDto.setIpV4(ipV4);
        logger.debug(detectorUnitDto.getMacAddress());
        String sensorSetJson = detectorUnitService.buildSensorSetJSON(detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress()));
        return ResponseEntity.ok(sensorSetJson);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerNewDetectorUnit(@RequestBody DetectorUnitDto detectorUnitDto) throws EntityNotFoundException{
        DetectorUnit entity = detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress());
        detectorUnitDto.setIpV4(entity.getIpV4());
        detectorUnitService.saveOne(detectorUnitDto);
        logger.debug("DTO: " + detectorUnitDto);
        return ResponseEntity.ok(detectorUnitDto.toString());
    }

    @PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDetectorInfo(@RequestBody DetectorUnitUpdateDto detectorUnitUpdateDto) throws JsonProcessingException {
        detectorUnitService.updateSensorList(detectorUnitUpdateDto);
        return ResponseEntity.ok(detectorUnitUpdateDto.toString());
    }
}

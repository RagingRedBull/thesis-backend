package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.update.DetectorUnitUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.util.mapper.DetectorUnitEntityMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorService;
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

import java.util.Optional;

@RestController
@RequestMapping(path = "/detector")
public class DetectorUnitController {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitController.class);
    private final DetectorUnitService detectorUnitService;
    private final SensorService sensorService;

    public DetectorUnitController(DetectorUnitService detectorUnitService, SensorService sensorService) {
        this.detectorUnitService = detectorUnitService;
        this.sensorService = sensorService;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DetectorUnitDto>> getAllDetectorInfoByPage(@RequestParam int pageNumber,
                                                                          @RequestParam int pageSize) {
        logger.info("RETURNING ALL DETECTOR");
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "macAddress"));
        return new ResponseEntity<>(detectorUnitService.findDetectorUnitsByPage(page), HttpStatus.OK);
    }

    @GetMapping(path = "/{macAddress}")
    public ResponseEntity<String> getSensorSetOfDetectorUnit(@PathVariable String macAddress,
                                                             @RequestParam(name = "ipV4") String ipV4)
            throws JsonProcessingException {
        DetectorUnitDto detectorUnitDto = new DetectorUnitDto();
        detectorUnitDto.setMacAddress(macAddress);
        detectorUnitDto.setIpV4(ipV4);
        logger.info(detectorUnitDto.getMacAddress());
        Optional<DetectorUnit> entity = detectorUnitService.findOneByPrimaryKey(detectorUnitDto);
        if (entity.isPresent()) {
            if (detectorUnitDto.getIpV4().equals(entity.get().getIpV4())) {
                return new ResponseEntity<>(detectorUnitService.buildSensorSetJSON(entity.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Detector Unit does not exist.", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerNewDetectorUnit(@RequestBody DetectorUnitDto detectorUnitDto) {
        EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitEntityMapper();
        DetectorUnit entity;
        Optional<DetectorUnit> optionalDetectorUnit = detectorUnitService.findOneByPrimaryKey(detectorUnitDto);
        optionalDetectorUnit.ifPresent(detectorUnit -> detectorUnitDto.setIpV4(detectorUnit.getIpV4()));
        detectorUnitService.saveOne(detectorUnitDto);
        return new ResponseEntity<>(detectorUnitDto.toString(), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDetectorInfo(@RequestBody DetectorUnitUpdateDto detectorUnitUpdateDto) throws JsonProcessingException {
        Optional<DetectorUnit> entity =
                detectorUnitService.findOneByPrimaryKey(detectorUnitUpdateDto.getDetectorUnitDto());
        if (entity.isEmpty()) {
            logger.info("ERROR: UNKNOWN MAC ADDRESS");
            return new ResponseEntity<>("UNKNOWN MAC ADDRESS", HttpStatus.NOT_FOUND);
        }
        if (detectorUnitUpdateDto.getSensorUpdateDtoSet().isEmpty()) {
            logger.info("EMPTY SENSOR LIST");
            return new ResponseEntity<>("EMPTY SENSOR LIST", HttpStatus.EXPECTATION_FAILED);
        }
        DetectorUnit detectorUnit = entity.get();
        logger.info("UPDATING DETECTOR UNIT: " + detectorUnit.getId());
        if (detectorUnitService.updateSensorList(detectorUnit, detectorUnitUpdateDto.getSensorUpdateDtoSet())
        ) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

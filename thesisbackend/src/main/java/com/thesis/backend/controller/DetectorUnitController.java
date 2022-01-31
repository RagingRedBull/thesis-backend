package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.dto.update.DetectorUnitUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
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

import javax.persistence.EntityNotFoundException;

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
        logger.info("Entity: Paged Detector Units");
        logger.info("Requested Size: " + pageSize);
        logger.info("Requested Page No: " + pageNumber);
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
        String sensorSetJson = detectorUnitService.buildSensorSetJSON(detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress()));
        return new ResponseEntity<>(sensorSetJson, HttpStatus.OK);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerNewDetectorUnit(@RequestBody DetectorUnitDto detectorUnitDto) {
        logger.info("DTO: " + detectorUnitDto.toString());
        try{
            DetectorUnit entity = detectorUnitService.findOneByPrimaryKey(detectorUnitDto.getMacAddress());
            detectorUnitDto.setIpV4(entity.getIpV4());
            logger.info("Updating Ip from: " + detectorUnitDto.getMacAddress() +
                    " to: " + detectorUnitDto.getIpV4());
        } catch (EntityNotFoundException e) {
            logger.info("Registering Unit: " + detectorUnitDto.getMacAddress());
        }
        detectorUnitService.saveOne(detectorUnitDto);
        return new ResponseEntity<>(detectorUnitDto.toString(), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDetectorInfo(@RequestBody DetectorUnitUpdateDto detectorUnitUpdateDto) throws JsonProcessingException {
        detectorUnitService.updateSensorList(detectorUnitUpdateDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

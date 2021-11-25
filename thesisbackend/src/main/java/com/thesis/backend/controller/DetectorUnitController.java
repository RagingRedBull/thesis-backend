package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.detector.DetectorUnitUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorService;
import com.thesis.backend.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/detector")
public class DetectorUnitController {

    private DetectorUnitService detectorUnitService;
    private SensorService sensorService;

    public DetectorUnitController(DetectorUnitService detectorUnitService, SensorService sensorService) {
        this.detectorUnitService = detectorUnitService;
        this.sensorService = sensorService;
    }

    @PostMapping(path = "/startup", consumes = "application/json")
    public ResponseEntity<?> getUnitController(@RequestBody DetectorUnitDto detectorUnitDto) throws JsonProcessingException {
        String macAddress = detectorUnitDto.getMacAddress();
        String ipV4 = detectorUnitDto.getIpV4();
        System.out.println(detectorUnitDto.getMacAddress());
        System.out.println(detectorUnitDto.getIpV4());
        if (macAddress == null) {
            return new ResponseEntity<>("ERROR: EMPTY ID", HttpStatus.EXPECTATION_FAILED);
        }
        macAddress = StringUtil.formatMacAddress(macAddress);
        String responseBody = "";
        DetectorUnit connectedDetectorUnit = detectorUnitService.findOneByMacAddress(macAddress);
        if(connectedDetectorUnit == null) {
            connectedDetectorUnit = new DetectorUnit(macAddress, ipV4);
            detectorUnitService.persistOne(connectedDetectorUnit);
            responseBody = "REGISTERED";
        } else {
            responseBody = sensorService.serializeListOfIds(connectedDetectorUnit.getAssociatedSensorSet());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping(path = "/update", consumes = "application/json")
    public ResponseEntity<?> updateDetectorInfo(@RequestBody DetectorUnitUpdateDto detectorUnitUpdateDto) {
        DetectorUnit unitToUpdate;
        unitToUpdate = detectorUnitService.findOneByMacAddress(detectorUnitUpdateDto.getMacAddress());
        if(unitToUpdate == null){
            return new ResponseEntity<>("UNKNOWN MAC ADDRESS", HttpStatus.NOT_FOUND);
        }
        if(detectorUnitUpdateDto.getSensorUpdateDtoSet().isEmpty()){
            return new ResponseEntity<>("EMPTY SENSOR LIST", HttpStatus.EXPECTATION_FAILED);
        }
        detectorUnitService.updateSensorList(unitToUpdate, detectorUnitUpdateDto.getSensorUpdateDtoSet());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

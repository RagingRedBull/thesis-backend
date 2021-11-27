package com.thesis.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.update.DetectorUnitUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.service.DetectorUnitService;
import com.thesis.backend.service.SensorService;
import com.thesis.backend.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/detector")
public class DetectorUnitController {
    Logger logger = LoggerFactory.getLogger(DetectorUnitController.class);
    private DetectorUnitService detectorUnitService;
    private SensorService sensorService;

    public DetectorUnitController(DetectorUnitService detectorUnitService, SensorService sensorService) {
        this.detectorUnitService = detectorUnitService;
        this.sensorService = sensorService;
    }

    @PostMapping(path = "/startup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUnitController(@RequestBody DetectorUnitDto detectorUnitDto) throws JsonProcessingException {
        String macAddress = detectorUnitDto.getMacAddress();
        String ipV4 = detectorUnitDto.getIpV4();
        logger.info("Unit Startup!");
        logger.info("Mac Address: " + macAddress);
        logger.info("IPV4: " + ipV4);
        if (macAddress == null) {
            logger.info("EMPTY ID LIST");
            return new ResponseEntity<>("ERROR: EMPTY ID", HttpStatus.EXPECTATION_FAILED);
        }
        macAddress = StringUtil.formatMacAddress(macAddress);
        String responseBody;
        DetectorUnit connectedDetectorUnit = detectorUnitService.findOneByMacAddress(macAddress);
        if (connectedDetectorUnit == null) {
            connectedDetectorUnit = new DetectorUnit(macAddress, ipV4);
            detectorUnitService.persistOne(connectedDetectorUnit);
            responseBody = "REGISTERED";
            logger.info("Registered Unit: " + connectedDetectorUnit.getMacAddress());
            logger.info("Current IP: " + connectedDetectorUnit.getIpV4());
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer().withRootName("sensorSet");
            responseBody =
                    writer.writeValueAsString(sensorService.builSensorSetUpdateDto(connectedDetectorUnit.getAssociatedSensorSet()
                    , true));
            logger.info("SENDING SESNSOR OF " + connectedDetectorUnit.getMacAddress());
            logger.info("Sensor List: " + connectedDetectorUnit.getAssociatedSensorSet().toString());
            logger.info("Response Body: " + responseBody);
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDetectorInfo(@RequestBody DetectorUnitUpdateDto detectorUnitUpdateDto) throws JsonProcessingException {
        DetectorUnit unitToUpdate;
        unitToUpdate = detectorUnitService.findOneByMacAddress(detectorUnitUpdateDto.getMacAddress());
        logger.info("UPDATING DETECTOR UNIT: " + unitToUpdate.getMacAddress());
        if (unitToUpdate == null) {
            logger.info("ERROR: UNKNOWN MAC ADDRESS");
            return new ResponseEntity<>("UNKNOWN MAC ADDRESS", HttpStatus.NOT_FOUND);
        }
        if (detectorUnitUpdateDto.getSensorUpdateDtoSet().isEmpty()) {
            logger.info("EMPTY SENSOR LIST");
            return new ResponseEntity<>("EMPTY SENSOR LIST", HttpStatus.EXPECTATION_FAILED);
        }
        detectorUnitService.updateSensorList(unitToUpdate, detectorUnitUpdateDto.getSensorUpdateDtoSet());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

package net.prmts.app.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.prmts.app.model.DetectorUnitLog;
import net.prmts.app.model.dto.DetectorUnitLogDto;
import net.prmts.app.repository.DetectorUnitLogRepository;
import net.prmts.app.repository.DetectorUnitRepository;

@RestController
public class DetectorController {
	private DetectorUnitLogRepository detectorLogRepository;
	private DetectorUnitRepository detectorUnitRepository;
	public DetectorController(DetectorUnitLogRepository detectorLogRepository, DetectorUnitRepository detectorRepository) {
		this.detectorLogRepository = detectorLogRepository;
		this.detectorUnitRepository = detectorRepository;
	}
	
	@PostMapping(path = "/detector/upload")
	public ResponseEntity<String> log(@RequestBody DetectorUnitLogDto log){
		DetectorUnitLog logToInsert = new DetectorUnitLog(log);
		logToInsert.setDetectorUnit(detectorUnitRepository.getById(log.getMacAddress()));
		logToInsert.setDateRecorded(ZonedDateTime.now(ZoneId.of("Asia/Manila")));
		this.detectorLogRepository.save(logToInsert);
		return new ResponseEntity<String>("Logging", HttpStatus.OK);
	}
}

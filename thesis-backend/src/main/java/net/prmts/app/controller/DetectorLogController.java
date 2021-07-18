package net.prmts.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.prmts.app.model.dto.DetectorUnitLogDto;
import net.prmts.app.service.DetectorUnitLogService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class DetectorLogController {
	private DetectorUnitLogService detectorLogService;
	
	public DetectorLogController(DetectorUnitLogService detectorLogService) {
		this.detectorLogService = detectorLogService;
	}
	@GetMapping(path = "/logs", produces = { "application/json"})
	@ResponseBody
	public Page<DetectorUnitLogDto> getAllLogsPaged(@RequestParam int page, @RequestParam int pageSize){
		Pageable requestedPage = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "dateRecorded"));
		return detectorLogService.findAll(requestedPage);
	}
}

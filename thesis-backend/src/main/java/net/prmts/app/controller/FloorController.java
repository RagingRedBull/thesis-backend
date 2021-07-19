package net.prmts.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.prmts.app.model.dto.FloorDto;
import net.prmts.app.repository.FloorRepository;
import net.prmts.app.service.FloorService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class FloorController {
	private FloorService floorService;
	public FloorController(FloorService floorStorageService, FloorRepository floorRepository) {
		this.floorService = floorStorageService;
	}
	@GetMapping(path = "/map", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getFloor(){
		return new ResponseEntity<List<Integer>>(this.floorService.findAllIds(),HttpStatus.OK);
	}
	@PostMapping(path = "/map/upload")
	public ResponseEntity<?> uploadMap(@RequestParam("mapFile")MultipartFile mapFile){
		ResponseEntity<FloorDto> floorStorageResponse = floorService.saveToImageServer(mapFile);
		return floorStorageResponse;
	}
}

package net.prmts.app.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	Logger logger = LoggerFactory.getLogger(FloorController.class);
	private FloorService floorService;
	public FloorController(FloorService floorStorageService, FloorRepository floorRepository) {
		this.floorService = floorStorageService;
	}
	@GetMapping(path = "/map", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getFloor(){
		return new ResponseEntity<List<Integer>>(this.floorService.findAllIds(),HttpStatus.OK);
	}
	@GetMapping(path = "/map/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getFloorById(@PathVariable Integer id){
		ResponseEntity<?> response = new ResponseEntity<String>("Could not find map with id " + id.toString(),HttpStatus.NOT_FOUND);
		this.logger.info("Searching for map with id " + id.toString());
		Optional<FloorDto> dtoResponse = this.floorService.findById(id);
		if (dtoResponse.isPresent()) {
			response = new ResponseEntity<FloorDto>(dtoResponse.get(),HttpStatus.OK);
		}
		return response;
	}
	@PostMapping(path = "/map/upload")
	public ResponseEntity<?> uploadMap(@RequestParam("mapFile")MultipartFile mapFile){
		ResponseEntity<FloorDto> floorStorageResponse = floorService.saveToImageServer(mapFile);
		return floorStorageResponse;
	}
}

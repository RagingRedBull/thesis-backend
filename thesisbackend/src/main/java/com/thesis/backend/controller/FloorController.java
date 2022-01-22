package com.thesis.backend.controller;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.wrapper.FloorPayload;
import com.thesis.backend.service.FloorService;
import com.thesis.backend.service.interfaces.FileService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/floor")
public class FloorController {
    private final Logger logger = LoggerFactory.getLogger(FloorController.class);
    private final FloorService floorService;
    private final FileService fileService;
    private final Environment env;

    public FloorController(FloorService floorService, FileService fileService, Environment env) {
        this.floorService = floorService;
        this.fileService = fileService;
        this.env = env;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        logger.info("Getting Floors with Page Size: " + pageSize + " of Page: " + (pageNumber-1));
        return new ResponseEntity<>(System.getProperty("catalina.home"), HttpStatus.OK);
    }

    @GetMapping(path = "/{floorId}", produces = "application/json")
    public ResponseEntity<?> getOne(int floorId) {
        Optional<Floor> floor = floorService.findOnById(floorId);
        if (floor.isEmpty()) {
            return new ResponseEntity<>("Floor object with id " + floorId
                    + " can not be found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(floor.get(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addFloor(FloorDto dto, @RequestParam MultipartFile file) throws IOException {
        if(dto != null) {
            logger.info(dto.getName());
            logger.info(dto.getDescription());
        } else {
            logger.info("DTO does not exist");
        }
        if (!file.isEmpty()){
            logger.info("Image was sent");
        } else {
            logger.info("image does not exist");
        }
        Tika tika = new Tika();
        if (file.isEmpty() && dto != null){
            if(tika.detect(file.getInputStream()).contains("images/")){
//                floorService.saveOne(floorDto);
                fileService.save(file);
            }
        } else {
            return new ResponseEntity<>("Invalid Parameter/s", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(env.getProperty(""), HttpStatus.OK);
    }
}

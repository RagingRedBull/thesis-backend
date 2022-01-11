package com.thesis.backend.controller;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.service.FloorService;
import org.apache.tika.Tika;
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
    private FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return new ResponseEntity<>("eheh", HttpStatus.OK);
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

    @PostMapping(path = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFloor(@RequestPart MultipartFile file, @RequestPart FloorDto floorDto) throws IOException {
        Tika tika = new Tika();
        if (file != null && floorDto != null){
            if(tika.detect(file.getInputStream()).contains("images/")){

            }
        } else {
            return new ResponseEntity<>("Invalid Parameter/s", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return null;
    }
}

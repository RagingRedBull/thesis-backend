package com.thesis.backend.controller;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.service.interfaces.FileService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping(path = "/images")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ImageResourcesController {
    private final Logger logger = LoggerFactory.getLogger(ImageResourcesController.class);
    private final FileService imageFileService;

    public ImageResourcesController(FileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @GetMapping(path = "/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String imageId) throws IOException {
        Tika tika = new Tika();
        Resource resource = imageFileService.load(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        tika.detect(resource.getInputStream())
                ))
                .body(resource);
    }

    @PostMapping(path = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FloorDto> uploadImage(MultipartFile file) throws IOException {
        FloorDto dto = new FloorDto();
        dto.setImageUrl(imageFileService.save(file));
        return ResponseEntity.ok(dto);
    }
}

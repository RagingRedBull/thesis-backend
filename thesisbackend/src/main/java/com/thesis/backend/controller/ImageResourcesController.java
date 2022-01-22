package com.thesis.backend.controller;

import com.thesis.backend.service.interfaces.FileService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class ImageResourcesController {
    private final Logger logger = LoggerFactory.getLogger(ImageResourcesController.class);
    private final FileService imageFileService;

    public ImageResourcesController(FileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @GetMapping(path = "/images/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String imageId) {
        Tika tika = new Tika();
        try {
            Resource resource = imageFileService.load(imageId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            tika.detect(resource.getInputStream())
                    ))
                    .body(resource);
        } catch (FileNotFoundException fileNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException ioException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.thesis.backend.controller;

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

import java.io.IOException;

@RestController
public class ImageResourcesController {
    private final Logger logger = LoggerFactory.getLogger(ImageResourcesController.class);
    @GetMapping(path = "/images/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String imageId) throws IOException {
        String loc = "H:\\testFolder\\" + imageId;
        String fileMediaType;
        Resource resource = new FileSystemResource(loc);
        Tika tika = new Tika();
        try {
            fileMediaType = tika.detect(resource.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileMediaType))
                    .body(resource);
        } catch (IOException ioException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

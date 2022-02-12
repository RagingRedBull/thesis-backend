package com.thesis.backend.controller;

import com.thesis.backend.exception.InvalidFileException;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/images")
public class ImageResourcesController {
    private final FileService imageFileService;

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
    public ResponseEntity<FloorDto> uploadImage(MultipartFile file) throws IOException, InvalidFileException, MissingServletRequestParameterException {
        if(file == null) {
            throw new MissingServletRequestParameterException("file", "Image File");
        }
        FloorDto dto = new FloorDto();
        dto.setImageUrl(imageFileService.save(file));
        return ResponseEntity.ok(dto);
    }
}

package com.thesis.backend.service;

import com.thesis.backend.controller.FloorController;
import com.thesis.backend.service.interfaces.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageFileService implements FileService {
    private final Logger logger = LoggerFactory.getLogger(ImageFileService.class);

    @Override
    public Resource load(String fileName) throws FileNotFoundException {
        String path = "/var/lib/prmts/images/" + fileName;
        Resource resource = new FileSystemResource(path);
        if (resource.exists() && resource.isFile()) {
            return resource;
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        Path saveDir = Paths.get("/var/lib/prmts/images/" + fileName);
        Files.copy(file.getInputStream(), saveDir,
                StandardCopyOption.REPLACE_EXISTING);
        logger.info("Image at: " + saveDir);
        return saveDir + file.getOriginalFilename();
    }
}

package com.thesis.backend.service;

import com.google.common.base.CharMatcher;
import com.thesis.backend.exception.InvalidFileException;
import com.thesis.backend.service.interfaces.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ImageFileService implements FileService {
    @Override
    public Resource load(String fileName) throws FileNotFoundException {
        String path = "/var/lib/prmts/images/" + fileName;
        Resource resource = new FileSystemResource(path);
        if (resource.exists() && resource.isFile()) {
            return resource;
        } else {
            throw new FileNotFoundException("Image " + fileName + " does not exist!");
        }
    }

    @Override
    public String save(MultipartFile file) throws IOException, InvalidFileException{
        Tika tika = new Tika();
        if (! tika.detect(file.getInputStream()).contains(("image"))) {
            throw new InvalidFileException("Invalid File Type! File must be an image.");
        }
        if(!isValidResolution(file.getInputStream())){
            throw new InvalidFileException("Image resolution must be atleast 1280x720! pixels");
        }
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        Path saveDir = Paths.get("/var/lib/prmts/images/" + fileName);
        Files.copy(file.getInputStream(), saveDir,
                StandardCopyOption.REPLACE_EXISTING);
        log.info("Image at: " + saveDir);
        return fileName;
    }

    private boolean isValidResolution(InputStream fileInputStream) {
        boolean isValid = true;
        int imgWidth = 0;
        int imgHeight = 0;
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        try {
            parser.parse(fileInputStream, handler,metadata, context);
            imgHeight = Integer.parseInt(CharMatcher.inRange('0', '9')
                    .retainFrom(metadata.get("Image Height"))
            );
            imgWidth = Integer.parseInt(CharMatcher.inRange('0', '9')
                    .retainFrom(metadata.get("Image Width"))
            );
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        if (imgHeight < 720 || imgWidth < 1280) {
            isValid = false;
        }
        return isValid;
    }
}

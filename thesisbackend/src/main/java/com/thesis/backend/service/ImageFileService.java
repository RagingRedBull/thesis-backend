package com.thesis.backend.service;

import com.thesis.backend.exception.InvalidFileException;
import com.thesis.backend.service.interfaces.FileService;
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
    public String save(MultipartFile file) throws IOException, InvalidFileException{
        Tika tika = new Tika();
        if (! tika.detect(file.getInputStream()).contains(("image"))) {
            throw new InvalidFileException("Invalid File Type!");
        }
        if(isValidResolution(file.getInputStream())){

        }
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        Path saveDir = Paths.get("/var/lib/prmts/images/" + fileName);
        Files.copy(file.getInputStream(), saveDir,
                StandardCopyOption.REPLACE_EXISTING);
        logger.info("Image at: " + saveDir);
        return fileName;
    }

    private boolean isValidResolution(InputStream fileInputStream) {
        boolean isValid = true;
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        try {
            parser.parse(fileInputStream, handler,metadata,context);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        String[] metadataNames = metadata.names();
        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
        return isValid;
    }
}

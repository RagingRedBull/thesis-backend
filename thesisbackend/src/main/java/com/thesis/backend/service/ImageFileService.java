package com.thesis.backend.service;

import com.thesis.backend.service.interfaces.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageFileService implements FileService {

    @Override
    public Resource load(String fileName) throws FileNotFoundException {
        String path = "/var/lib/prmts/images/" + fileName;
        Resource resource = new FileSystemResource(path);
        if(resource.exists() && resource.isFile()){
            return resource;
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        String path = "/var/lib/prmts/images/";
        Files.copy(file.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        path = path + file.getOriginalFilename();
        return path;
    }
}

package com.thesis.backend.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    Resource load(String fileName) throws FileNotFoundException;
    String save(MultipartFile file) throws IOException;

}

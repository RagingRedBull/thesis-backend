package com.thesis.backend.model.util.wrapper;

import com.thesis.backend.model.dto.FloorDto;
import org.springframework.web.multipart.MultipartFile;

public class FloorPayload {
    private MultipartFile file;
    private FloorDto dto;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public FloorDto getDto() {
        return dto;
    }

    public void setDto(FloorDto dto) {
        this.dto = dto;
    }
}

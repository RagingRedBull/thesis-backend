package com.thesis.backend.service;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorEntityMapper;
import com.thesis.backend.repository.FloorRespository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FloorService {
    @Value("${}")
    private String dataDirPath;
    private FloorRespository floorRespository;

    public FloorService(FloorRespository floorRespository) {
        this.floorRespository = floorRespository;
    }

    public Optional<Floor> findOnById(int id) {
         return floorRespository.findById(id);
    }

    public Floor saveOne(MultipartFile file, FloorDto dto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorEntityMapper();
        Floor entity = mapper.mapToEntity(dto);
        entity = floorRespository.saveAndFlush(entity);
        return entity;
    }

}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorEntityMapper;
import com.thesis.backend.repository.FloorRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FloorService implements EntityService<Floor, FloorDto>{

    private FloorRespository floorRespository;

    public FloorService(FloorRespository floorRespository) {
        this.floorRespository = floorRespository;
    }

    @Override
    public Optional<Floor> findOneByPrimaryKey(FloorDto floorDto) {
        return floorRespository.findById(floorDto.getId());
    }

    @Override
    public Floor saveOne(FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorEntityMapper();
        Floor entity = mapper.mapToEntity(floorDto);
        entity = floorRespository.saveAndFlush(entity);
        return entity;
    }

    public Optional<Floor> findOnById(int id) {
         return floorRespository.findById(id);
    }

    private String saveFile(MultipartFile floorPlan) {
        return null;
    }
}

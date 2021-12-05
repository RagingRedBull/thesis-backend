package com.thesis.backend.service;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorEntityMapper;
import com.thesis.backend.repository.FloorRespository;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    private FloorRespository floorRespository;

    public FloorService(FloorRespository floorRespository) {
        this.floorRespository = floorRespository;
    }

    public Floor saveOne(FloorDto dto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorEntityMapper();
        Floor entity = mapper.mapToEntity(dto);
        entity = floorRespository.saveAndFlush(entity);
        return entity;
    }

}

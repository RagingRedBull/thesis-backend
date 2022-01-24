package com.thesis.backend.service;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorEntityMapper;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FloorService implements EntityService<Floor, FloorDto> {

    private FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override
    public Optional<Floor> findOneByPrimaryKey(FloorDto floorDto) {
        return floorRepository.findById(floorDto.getId());
    }

    @Override
    public Floor saveOne(FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorEntityMapper();
        Floor entity = mapper.mapToEntity(floorDto);
        entity = floorRepository.saveAndFlush(entity);
        return entity;
    }

    public Optional<Floor> findOnById(int id) {
         return floorRepository.findById(id);
    }
}

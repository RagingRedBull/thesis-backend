package com.thesis.backend.service;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorEntityMapper;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class FloorService implements EntityService<Floor, FloorDto, Integer> {

    private FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override
    public Floor findOneByPrimaryKey(Integer primaryKey) {
        Optional<Floor> wrapper = floorRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return wrapper.get();
        }
    }

    @Override
    public Floor saveOne(FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorEntityMapper();
        Floor entity = mapper.mapToEntity(floorDto);
        entity = floorRepository.saveAndFlush(entity);
        return entity;
    }

}

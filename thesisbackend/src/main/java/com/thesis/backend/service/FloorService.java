package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorMapper;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FloorService implements EntityService<Floor, FloorDto, Integer> {

    private final FloorRepository floorRepository;

    @Override
    public Floor findOneByPrimaryKey(Integer primaryKey) throws EntityNotFoundException{
        Optional<Floor> wrapper = floorRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            throw new PrmtsEntityNotFoundException(Floor.class, primaryKey);
        } else {
            return wrapper.get();
        }
    }

    @Override
    public Floor saveOne(FloorDto floorDto) {
        EntityMapper<Floor, FloorDto> mapper = new FloorMapper();
        Floor entity = mapper.mapToEntity(floorDto);
        entity = floorRepository.saveAndFlush(entity);
        return entity;
    }

    @Override
    public void deleteOne(Integer primaryKey) {
        floorRepository.deleteById(primaryKey);
    }

    @Override
    public Floor updateOne(FloorDto dto, Integer primaryKey) {
        Floor floor = floorRepository.getById(primaryKey);
        floor.setDescription(dto.getDescription());
        floor.setImageName(dto.getImageUrl());
        floor.setName(dto.getName());
        floor.setOrder(dto.getOrder());
        return floorRepository.saveAndFlush(floor);
    }

    public Page<FloorDto> getAllFloorByPage (int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        FloorMapper mapper = new FloorMapper();
        return floorRepository.findAll(page).map(mapper::mapToDto);
    }
}

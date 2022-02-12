package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.FloorMapper;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
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
        Floor floor;
        try {
            floor = floorRepository.getById(primaryKey);
        } catch (EmptyResultDataAccessException resultDataAccessException) {
            throw new PrmtsEntityNotFoundException(Floor.class, primaryKey);
        }
        floor.setDescription(dto.getDescription());
        floor.setImageName(dto.getImageUrl());
        floor.setName(dto.getName());
        floor.setOrder(dto.getOrder());
        return floorRepository.saveAndFlush(floor);
    }

    public Page<FloorDto> getAllFloorByPage (int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "order"));
        FloorMapper mapper = new FloorMapper();
        return floorRepository.findAll(page).map(mapper::mapToDto);
    }
}

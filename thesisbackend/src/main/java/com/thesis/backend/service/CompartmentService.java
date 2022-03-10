package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.repository.CompartmentRepository;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompartmentService implements EntityService<Compartment, CompartmentDto,Integer> {
    private final CompartmentRepository compartmentRepository;
    private final FloorRepository floorRepository;

    @Override
    public Compartment findOneByPrimaryKey(Integer primaryKey) throws EntityNotFoundException {
        Optional<Compartment> entity = compartmentRepository.findById(primaryKey);
        if (entity.isEmpty()){
            throw new PrmtsEntityNotFoundException(Compartment.class, primaryKey);
        } else {
            return entity.get();
        }
    }

    @Override
    @Transactional
    public Compartment saveOne(CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        Floor floor = floorRepository.getById(compartmentDto.getFloorId());
        Compartment compartment = mapper.mapToEntity(compartmentDto);
        compartment.setFloor(floor);
        return compartmentRepository.saveAndFlush(compartment);
    }

    @Override
    @Transactional
    public void deleteOne(Integer primaryKey) {
        try {
            compartmentRepository.deleteById(primaryKey);
        } catch (EmptyResultDataAccessException exception) {
            throw new PrmtsEntityNotFoundException(Compartment.class, primaryKey);
        }
    }

    @Override
    public Compartment updateOne(CompartmentDto compartmentDto) {
        Compartment compartment;
        try {
            compartment = compartmentRepository.getById(compartmentDto.getId());
        } catch (EmptyResultDataAccessException resultDataAccessException) {
            throw new PrmtsEntityNotFoundException(Compartment.class, compartmentDto.getId());
        }
        compartment.setName(compartmentDto.getName());
        compartment.setYKonva(compartmentDto.getYKonva());
        compartment.setXKonva(compartmentDto.getXKonva());
        compartment.setYDimension(compartmentDto.getYDimension());
        compartment.setXDimension(compartmentDto.getXDimension());
        compartment.setWidthKonva(compartmentDto.getWidthKonva());
        compartment.setHeightKonva(compartmentDto.getHeightKonva());
        compartment.setWidth(compartmentDto.getWidth());
        compartment.setDepth(compartmentDto.getDepth());
        return compartmentRepository.saveAndFlush(compartment);
    }

    public List<Compartment> findCompartmentsByFloorId(int floorId) {
        return compartmentRepository.findByFloorId(floorId, Sort.by(Sort.Direction.ASC, "id"));
    }

    public Set<CompartmentDto> convertEntitySetToDto (Set<Compartment> compartments) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        return compartments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }
}

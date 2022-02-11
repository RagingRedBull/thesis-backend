package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.entity.logs.DhtSensorLog;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.response.CompartmentLogResponse;
import com.thesis.backend.model.util.mapper.CompartmentMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.repository.CompartmentRepository;
import com.thesis.backend.repository.FloorRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompartmentService implements EntityService<Compartment, CompartmentDto,Integer> {
    private final Logger logger = LoggerFactory.getLogger(CompartmentService.class);
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
    public Compartment saveOne(CompartmentDto compartmentDto) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        Floor floor = floorRepository.getById(compartmentDto.getFloorId());
        Compartment compartment = mapper.mapToEntity(compartmentDto);
        compartment.setFloor(floor);
        return compartmentRepository.saveAndFlush(compartment);
    }

    @Override
    public void deleteOne(Integer primaryKey) {
        compartmentRepository.findById(primaryKey);
    }

    @Override
    public Compartment updateOne(CompartmentDto compartmentDto, Integer primaryKey) {
        return null;
    }

    public Set<Compartment> findCompartmentsByFloorId(int floorId) {
        return compartmentRepository.findByFloor_Id(floorId);
    }

    public Set<CompartmentDto> convertEntitySetToDto (Set<Compartment> compartments) {
        EntityMapper<Compartment, CompartmentDto> mapper = new CompartmentMapper();
        return compartments.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }
}

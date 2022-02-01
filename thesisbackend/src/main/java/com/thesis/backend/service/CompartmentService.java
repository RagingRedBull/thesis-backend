package com.thesis.backend.service;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.repository.CompartmentRepository;
import com.thesis.backend.service.interfaces.EntityService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class CompartmentService implements EntityService<Compartment, CompartmentDto,Integer> {
    private CompartmentRepository compartmentRepository;

    public CompartmentService(CompartmentRepository compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    @Override
    public Compartment findOneByPrimaryKey(Integer primaryKey) throws EntityNotFoundException {
        Optional<Compartment> entity = compartmentRepository.findById(primaryKey);
        if (entity.isEmpty()){
            throw new EntityNotFoundException();
        } else {
            return entity.get();
        }
    }

    @Override
    public Compartment saveOne(CompartmentDto compartmentDto) {
//        Compartment compartment =
        return null;
    }

    public Set<Compartment> findCompartmentsByFloorId(int floorId) {
        return compartmentRepository.findByFloor_Id(floorId);
    }
}

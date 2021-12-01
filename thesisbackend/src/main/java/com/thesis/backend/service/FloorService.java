package com.thesis.backend.service;

import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
import com.thesis.backend.model.util.mapper.FloorMapper;
import com.thesis.backend.repository.FloorRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloorService {
    private FloorRespository floorRespository;

    public FloorService(FloorRespository floorRespository) {
        this.floorRespository = floorRespository;
    }

    public List<Floor> findAll(){
        return floorRespository.findAll();
    }

    public List<FloorDto> findAllAsDto(){
        FloorMapper mapper = new FloorMapper();
        return findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<FloorDto> convertListToDto(List<Floor> floorList, List<DetectorUnitDto> detectorUnitDtoList){
        FloorMapper mapper = new FloorMapper();
        return floorList.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}

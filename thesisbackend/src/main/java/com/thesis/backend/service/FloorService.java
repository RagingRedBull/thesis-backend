package com.thesis.backend.service;

import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;
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
        return findAll().stream()
                .map(FloorDto::new)
                .collect(Collectors.toList());
    }

    public List<FloorDto> convertListToDto(List<Floor> floorList, List<DetectorUnitDto> detectorUnitDtoList){
        return floorList.stream()
                .map(FloorDto::new)
                .collect(Collectors.toList());
    }
}

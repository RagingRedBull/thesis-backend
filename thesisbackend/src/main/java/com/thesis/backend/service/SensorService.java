package com.thesis.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public String serializeListOfIds(Set<Sensor> sensorList) throws JsonProcessingException {
        List<Integer> sensorIdList = sensorList.stream().map(Sensor::getId).collect(Collectors.toList());
        return new ObjectMapper().writeValueAsString(sensorIdList);
    }

    public List<Sensor> getAllSensorsInList(List<Integer> sensorIdLIst){
       return sensorRepository.findByIdIn(sensorIdLIst);
    }
}

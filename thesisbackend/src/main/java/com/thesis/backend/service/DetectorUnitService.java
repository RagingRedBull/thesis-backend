package com.thesis.backend.service;

import com.thesis.backend.model.dto.sensor.SensorUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.repository.DetectorUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DetectorUnitService {
    private DetectorUnitRepository detectorUnitRepository;
    private SensorService sensorService;

    public DetectorUnitService(DetectorUnitRepository detectorUnitRepository, SensorService sensorService) {
        this.detectorUnitRepository = detectorUnitRepository;
        this.sensorService = sensorService;
    }

    public DetectorUnit findOneByMacAddress(String macAddress) {
        return detectorUnitRepository.findById(macAddress)
                .orElse(null);
    }

    public void persistOne(DetectorUnit detectorUnit) {
        detectorUnitRepository.saveAndFlush(detectorUnit);
    }

    public void updateSensorList(DetectorUnit unitToUpdate, Set<SensorUpdateDto> sensorUpdateDtoList) {
        System.out.println("UPDATE!!!");
        System.out.println("MacAddress: " + unitToUpdate.getMacAddress());
        System.out.println("Sensor List: \n" + sensorUpdateDtoList.toString());
        List<Sensor> targetSensorList = sensorService.getAllSensorsInList(getTargetSensors(sensorUpdateDtoList));
        unitToUpdate.getAssociatedSensorSet().removeAll(getSensorsToRemove(targetSensorList, sensorUpdateDtoList));
        unitToUpdate.getAssociatedSensorSet().addAll(getSensorToAdd(targetSensorList, sensorUpdateDtoList));
        detectorUnitRepository.saveAndFlush(unitToUpdate);
    }
    private List<Integer> getTargetSensors(Set<SensorUpdateDto> sensorUpdateDtoList) {
        return sensorUpdateDtoList.stream()
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
    }
    private Set<Sensor> getSensorsToRemove(List<Sensor> targetSensorList,
                                            Set<SensorUpdateDto> sensorUpdateDtoList) {
        Predicate<SensorUpdateDto> filterByToEnableFalse = sensorUpdateDto -> !sensorUpdateDto.isToEnable();
        List<Integer> idSensorsToRemove = sensorUpdateDtoList.stream()
                .filter(filterByToEnableFalse)
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
        Predicate<Sensor> filterByIdToRemove = sensor -> idSensorsToRemove.contains(sensor.getId());
        return targetSensorList.stream()
                .filter(filterByIdToRemove)
                .collect(Collectors.toSet());
    }
    private Set<Sensor> getSensorToAdd(List<Sensor> targetSensorList,
                                        Set<SensorUpdateDto> sensorUpdateDtoList) {
        Predicate<SensorUpdateDto> filterByToEnableTrue = SensorUpdateDto::isToEnable;
        List<Integer> idSensorsToRemove = sensorUpdateDtoList.stream()
                .filter(filterByToEnableTrue)
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
        Predicate<Sensor> filterByIdToAdd = sensor -> idSensorsToRemove.contains(sensor.getId());
        return targetSensorList.stream()
                .filter(filterByIdToAdd)
                .collect(Collectors.toSet());
    }

}

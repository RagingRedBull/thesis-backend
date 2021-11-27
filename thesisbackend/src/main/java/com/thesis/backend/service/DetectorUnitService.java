package com.thesis.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.repository.DetectorUnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DetectorUnitService {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitService.class);
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

    public DetectorUnit updateSensorList(DetectorUnit unitToUpdate, Set<SensorUpdateDto> sensorUpdateDtoSet) throws JsonProcessingException {
        logger.info("PERFORMING UPDATE!");
        logger.info("Unit Mac Address: " + unitToUpdate.getMacAddress());
        logger.info("New Sensor Set: " + sensorUpdateDtoSet.toString());
        Set<Sensor> targetSensorSet = sensorService.getAllSensorsInList(getTargetSensors(sensorUpdateDtoSet));
        Set<Sensor> sensorSetToRemove = getSensorsToRemove(targetSensorSet, sensorUpdateDtoSet);
        unitToUpdate.getAssociatedSensorSet().removeAll(sensorSetToRemove);
        unitToUpdate.getAssociatedSensorSet().addAll(getSensorToAdd(targetSensorSet, sensorUpdateDtoSet));
        detectorUnitRepository.saveAndFlush(unitToUpdate);
        contactDetectorUnitToUpdate(unitToUpdate, sensorUpdateDtoSet);
        logger.info("Updated Detector Unit Sensor Set: " + unitToUpdate.getAssociatedSensorSet().toString());
        return unitToUpdate;
    }

    private boolean contactDetectorUnitToUpdate(DetectorUnit detectorUnit, Set<SensorUpdateDto> sensorSet) throws JsonProcessingException {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        ObjectWriter jsonObjectWriter = jsonObjectMapper.writer().withRootName("sensorSet");
        WebClient webClient = WebClient.builder()
                .baseUrl("http://" + detectorUnit.getIpV4())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/update");
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(jsonObjectWriter.writeValueAsString(sensorSet));
        WebClient.ResponseSpec responseSpec = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        Mono<String> response = headersSpec.retrieve()
                .bodyToMono(String.class);
        response.subscribe(
                logger::info,
                error -> logger.info(error.getMessage()),
                () -> logger.info("No response")
        );
        return true;
    }

    private List<Integer> getTargetSensors(Set<SensorUpdateDto> sensorUpdateDtoSet) {
        return sensorUpdateDtoSet.stream()
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
    }

    private Set<Sensor> getSensorsToRemove(Set<Sensor> targetSensorSet,
                                           Set<SensorUpdateDto> sensorUpdateDtoSet) {
        Predicate<SensorUpdateDto> filterByToEnableFalse = sensorUpdateDto -> !sensorUpdateDto.isToEnable();
        List<Integer> idSensorsToRemove = sensorUpdateDtoSet.stream()
                .filter(filterByToEnableFalse)
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
        Predicate<Sensor> filterByIdToRemove = sensor -> idSensorsToRemove.contains(sensor.getId());
        return targetSensorSet.stream()
                .filter(filterByIdToRemove)
                .collect(Collectors.toSet());
    }

    private Set<Sensor> getSensorToAdd(Set<Sensor> targetSensorSet,
                                       Set<SensorUpdateDto> sensorUpdateDtoSet) {
        Predicate<SensorUpdateDto> filterByToEnableTrue = SensorUpdateDto::isToEnable;
        List<Integer> idSensorsToRemove = sensorUpdateDtoSet.stream()
                .filter(filterByToEnableTrue)
                .map(SensorUpdateDto::getSensorId)
                .collect(Collectors.toList());
        Predicate<Sensor> filterByIdToAdd = sensor -> idSensorsToRemove.contains(sensor.getId());
        return targetSensorSet.stream()
                .filter(filterByIdToAdd)
                .collect(Collectors.toSet());
    }

}

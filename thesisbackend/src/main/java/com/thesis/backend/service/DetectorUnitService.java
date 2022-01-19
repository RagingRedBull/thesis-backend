package com.thesis.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.backend.model.dto.detector.DetectorUnitDto;
import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.util.mapper.DetectorUnitEntityMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.repository.DetectorUnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DetectorUnitService implements EntityService<DetectorUnit, DetectorUnitDto> {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitService.class);
    private final EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitEntityMapper();
    private final DetectorUnitRepository detectorUnitRepository;
    private final SensorService sensorService;

    public DetectorUnitService(DetectorUnitRepository detectorUnitRepository, SensorService sensorService) {
        this.detectorUnitRepository = detectorUnitRepository;
        this.sensorService = sensorService;
    }

    @Override
    public Optional<DetectorUnit> findOneByPrimaryKey(DetectorUnitDto detectorUnitDto) {
        return detectorUnitRepository.findById(detectorUnitDto.getMacAddress());
    }

    @Override
    public DetectorUnit saveOne(DetectorUnitDto detectorUnitDto) {
        return detectorUnitRepository.saveAndFlush(mapper.mapToEntity(detectorUnitDto));
    }

    public Page<DetectorUnitDto> findDetectorUnitsByPage(Pageable page) {
        return detectorUnitRepository.findAll(page).map(mapper::mapToDto);
    }

    public boolean updateSensorList(DetectorUnit unitToUpdate, Set<SensorUpdateDto> sensorUpdateDtoSet) throws JsonProcessingException {
        boolean isSuccessful = false;
        logger.info("PERFORMING UPDATE!");
        logger.info("Unit Mac Address: " + unitToUpdate.getId());
        logger.info("Sensor Update Set: " + sensorUpdateDtoSet.toString());
        Set<Sensor> targetSensorSet =
                sensorService.getAllSensorsInList(getSensorIdFromSensorUpdateDtoAsList(sensorUpdateDtoSet));
        unitToUpdate.getAssociatedSensorSet().removeAll(getSensorsToRemove(targetSensorSet, sensorUpdateDtoSet));
        unitToUpdate.getAssociatedSensorSet().addAll(getSensorToAdd(targetSensorSet, sensorUpdateDtoSet));
        if (contactDetectorUnitToUpdate(unitToUpdate, sensorUpdateDtoSet)) {
            detectorUnitRepository.saveAndFlush(unitToUpdate);
            isSuccessful = true;
            logger.info("Unit Mac Address: " + unitToUpdate.getId());
            logger.info("Updated Sensor Set: " + unitToUpdate.getAssociatedSensorSet().toString());
        }
        return isSuccessful;
    }

    public String buildSensorSetDto(DetectorUnit detectorUnit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("sensorSet");
        logger.info("SENDING SENSOR OF " + detectorUnit.getId());
        logger.info("Sensor Set: " + detectorUnit.getAssociatedSensorSet().toString());
        return
                writer.writeValueAsString(sensorService.buildSensorSetUpdateDto(
                        detectorUnit.getAssociatedSensorSet(), true));
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
        WebClient.RequestHeadersSpec<?> headersSpec =
                bodySpec.bodyValue(jsonObjectWriter.writeValueAsString(sensorSet));
        WebClient.ResponseSpec responseSpec = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        Mono<String> response = headersSpec.retrieve()
                .bodyToMono(String.class);
        response.subscribe(
                logger::info,
                error -> logger.info(error.getMessage())
        );
        return true;
    }

    private List<Integer> getSensorIdFromSensorUpdateDtoAsList(Set<SensorUpdateDto> sensorUpdateDtoSet) {
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

    private Set<DetectorUnitDto> mapEntityToDto(Set<DetectorUnit> entitySet) {
        EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitEntityMapper();
        return entitySet.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<DetectorUnit> mapDtoToEntity(Set<DetectorUnitDto> dtoSet) {
        EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitEntityMapper();
        return dtoSet.stream()
                .map(mapper::mapToEntity)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

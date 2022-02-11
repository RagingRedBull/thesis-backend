package com.thesis.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.dto.update.DetectorUnitUpdateDto;
import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import com.thesis.backend.model.util.mapper.DetectorUnitMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.repository.DetectorUnitRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetectorUnitService implements EntityService<DetectorUnit, DetectorUnitDto, String> {
    private final Logger logger = LoggerFactory.getLogger(DetectorUnitService.class);
    private final EntityMapper<DetectorUnit, DetectorUnitDto> mapper = new DetectorUnitMapper();
    private final DetectorUnitRepository detectorUnitRepository;
    private final SensorService sensorService;


    @Override
    public DetectorUnit findOneByPrimaryKey(String primaryKey) throws EntityNotFoundException{
        Optional<DetectorUnit> wrapper = detectorUnitRepository.findById(primaryKey);
        if (wrapper.isEmpty()) {
            logger.error("No Detector Unit with Mac Address: " + primaryKey);
            throw new PrmtsEntityNotFoundException(DetectorUnit.class, primaryKey);
        } else {
            return wrapper.get();
        }
    }

    @Override
    public DetectorUnit saveOne(DetectorUnitDto detectorUnitDto) {
        return detectorUnitRepository.saveAndFlush(mapper.mapToEntity(detectorUnitDto));
    }

    @Override
    public void deleteOne(String primaryKey) {
        detectorUnitRepository.deleteById(primaryKey);
    }

    public Page<DetectorUnitDto> findDetectorUnitsByPage(Pageable page) {
        return detectorUnitRepository.findAll(page).map(mapper::mapToDto);
    }

    public void updateSensorList(DetectorUnitUpdateDto detectorUnitUpdateDto) throws JsonProcessingException,
            EntityNotFoundException {
        DetectorUnit unitToUpdate = findOneByPrimaryKey(detectorUnitUpdateDto.getDetectorUnitDto().getMacAddress());
        boolean isSuccessful = false;
        logger.info("PERFORMING UPDATE!");
        logger.info("Unit Mac Address: " + detectorUnitUpdateDto.getDetectorUnitDto().getMacAddress());
        logger.info("Sensor Update Set: " + detectorUnitUpdateDto.getSensorUpdateDtoSet().toString());
        Set<Sensor> targetSensorSet =
                sensorService.getAllSensorsInList(getSensorIdFromSensorUpdateDtoAsList(
                        detectorUnitUpdateDto.getSensorUpdateDtoSet()));
        unitToUpdate.getAssociatedSensorSet().removeAll(getSensorsToRemove(
                targetSensorSet, detectorUnitUpdateDto.getSensorUpdateDtoSet()));
        unitToUpdate.getAssociatedSensorSet().addAll(getSensorToAdd(
                targetSensorSet, detectorUnitUpdateDto.getSensorUpdateDtoSet()));
        if (contactDetectorUnitToUpdate(unitToUpdate, detectorUnitUpdateDto.getSensorUpdateDtoSet())) {
            detectorUnitRepository.saveAndFlush(unitToUpdate);
            logger.info("Unit Mac Address: " + unitToUpdate.getMacAddress());
            logger.info("Updated Sensor Set: " + unitToUpdate.getAssociatedSensorSet().toString());
        } else {

        }
    }

    public String buildSensorSetJSON(DetectorUnit detectorUnit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("sensorSet");
        logger.info("SENDING SENSOR OF " + detectorUnit.getMacAddress());
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
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.put();
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
}

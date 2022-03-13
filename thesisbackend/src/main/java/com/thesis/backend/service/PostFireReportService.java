package com.thesis.backend.service;

import com.thesis.backend.model.dto.PostFireReportLogDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.logs.PostFireReportLog;
import com.thesis.backend.model.util.mapper.PostFireReportLogMapper;
import com.thesis.backend.repository.PostFireReportLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFireReportService implements EntityService<PostFireReportLog, PostFireReportLogDto, Long> {
    private final PostFireReportLogRepository postFireReportLogRepository;
    private final CompartmentService compartmentService;
    private final SensorLogService sensorLogService;
    @Override
    public PostFireReportLog findOneByPrimaryKey(Long primaryKey) throws EntityNotFoundException {
        return null;
    }

    @Override
    public PostFireReportLog saveOne(PostFireReportLogDto postFireReportLogDto) {
        return null;
    }

    @Override
    public void deleteOne(Long primaryKey) {

    }

    @Override
    public PostFireReportLog updateOne(PostFireReportLogDto postFireReportLogDto) {
        return null;
    }

    public Page<PostFireReportLogDto> findAllByPage(Pageable page) {
        Page<PostFireReportLog> postFireReportLogs = postFireReportLogRepository.findAll(page);
        return postFireReportLogs.map(this::buildPostFireReportLogDto);
    }

    public List<PostFireReportLogDto> getIdsAndDates() {
        return postFireReportLogRepository.getIdAndDates();
    }
    private PostFireReportLogDto buildPostFireReportLogDto(PostFireReportLog log) {
        Compartment affectedCompartment = compartmentService.findOneByPrimaryKey(log.getCompartmentId());
        PostFireReportLogDto dto = new PostFireReportLogMapper().mapToDto(log);
        dto.setSensorLogSet(sensorLogService.mapSensorLogEntityToDto(log.getLogsDetected()));
        dto.setCompartmentName(affectedCompartment.getName());
        dto.setFloorDesc(affectedCompartment.getFloor().getDescription());
        return dto;
    }
}

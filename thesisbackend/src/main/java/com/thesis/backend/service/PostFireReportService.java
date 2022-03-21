package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.PostFireReportLogDto;
import com.thesis.backend.model.dto.PostFireReportCompartmentDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.logs.PostFireReportLog;
import com.thesis.backend.repository.DetectorUnitRepository;
import com.thesis.backend.repository.PostFireReportLogRepository;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFireReportService implements EntityService<PostFireReportLog, PostFireReportLogDto, Long> {
    private final PostFireReportLogRepository postFireReportLogRepository;
    private final CompartmentService compartmentService;
    private final SensorLogRepository sensorLogRepository;
    private final DetectorUnitRepository detectorUnitRepository;

    @Override
    public PostFireReportLog findOneByPrimaryKey(Long primaryKey) throws EntityNotFoundException {
        Optional<PostFireReportLog> entity = postFireReportLogRepository.findById(primaryKey);
        if (entity.isEmpty()){
            throw new PrmtsEntityNotFoundException(Compartment.class, primaryKey);
        } else {
            return entity.get();
        }
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

    @Transactional
    public PostFireReportLog saveOne(PostFireReportLog postFireReportLog) {
        return postFireReportLogRepository.saveAndFlush(postFireReportLog);
    }

    public PostFireReportLog getReferenceOfActivePfr() {
        Long id = postFireReportLogRepository.getIdOfLatestPfrWithNoFireOut();
        PostFireReportLog log;
        if (id == null || id == 0) {
            log = null;
        } else {
            log = postFireReportLogRepository.getById(id);
        }
        return log;
    }

    public List<PostFireReportLogDto> getIdsAndDates() {
        return postFireReportLogRepository.getIdAndDates();
    }

    public Page<PostFireReportCompartmentDto> getAffectedCompartmentsByPfrId(long pfrId, Pageable page) {
        return sensorLogRepository.getAffectedCompartmentsByPfrIdPaged(pfrId, page);
    }
    public List<PostFireReportCompartmentDto> getAffectedCompartmentsByPfrId(long pfrId) {
        return sensorLogRepository.getAffectedCompartmentsByPfrId(pfrId);
    }
    public Long getIdOfActivePFR() {
        return postFireReportLogRepository.getIdOfLatestPfrWithNoFireOut();
    }
}

package com.thesis.backend.service;


import com.thesis.backend.model.dto.MachineLearningOutputDto;
import com.thesis.backend.model.entity.ml.MachineLearningOutput;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.MachineLearningOutputMapper;
import com.thesis.backend.repository.MachineLearningOutputRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MachineLearningOutputService implements EntityService<MachineLearningOutput, MachineLearningOutputDto, Long> {
    private final MachineLearningOutputRepository machineLearningOutputRepository;

    @Override
    public MachineLearningOutput findOneByPrimaryKey(Long primaryKey) throws EntityNotFoundException {
        return null;
    }

    @Transactional
    @Override
    public MachineLearningOutput saveOne(MachineLearningOutputDto machineLearningOutputDto) {
        EntityMapper<MachineLearningOutput, MachineLearningOutputDto> entityMapper = new MachineLearningOutputMapper();
        return machineLearningOutputRepository.saveAndFlush(entityMapper.mapToEntity(machineLearningOutputDto));
    }

    @Override
    public void deleteOne(Long primaryKey) {

    }

    @Override
    public MachineLearningOutput updateOne(MachineLearningOutputDto machineLearningOutputDto) {
        return null;
    }

    public MachineLearningOutput getLatestOutput(String type) {
        return machineLearningOutputRepository.getLatestOutput(type);
    }
}

package com.thesis.backend.service;

import com.thesis.backend.model.dto.MachineLearningInputDto;
import com.thesis.backend.model.entity.ml.MachineLearningInput;
import com.thesis.backend.repository.MachineLearningInputRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineLearningInputService implements EntityService<MachineLearningInput, MachineLearningInputDto, Long> {
    private final MachineLearningInputRepository machineLearningInputRepository;
    @Override
    public MachineLearningInput findOneByPrimaryKey(Long primaryKey) throws EntityNotFoundException {
        return null;
    }

    @Override
    public MachineLearningInput saveOne(MachineLearningInputDto machineLearningInputDto) {
        return null;
    }

    @Override
    public void deleteOne(Long primaryKey) {

    }

    @Override
    public MachineLearningInput updateOne(MachineLearningInputDto machineLearningInputDto) {
        return null;
    }

    public MachineLearningInput saveOne(MachineLearningInput machineLearningInput) {
        return machineLearningInputRepository.saveAndFlush(machineLearningInput);
    }

    public MachineLearningInput getLatestInput() {
        return machineLearningInputRepository.getLatestInput();
    }
}

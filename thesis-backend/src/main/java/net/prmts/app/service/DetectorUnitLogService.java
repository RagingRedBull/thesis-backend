package net.prmts.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.prmts.app.model.dto.DetectorUnitLogDto;
import net.prmts.app.repository.DetectorUnitLogRepository;

@Service
public class DetectorUnitLogService {
	private DetectorUnitLogRepository detectorLogRepository;
	
	public DetectorUnitLogService(DetectorUnitLogRepository detectorLogRepository) {
		this.detectorLogRepository = detectorLogRepository;
	}
	
	public Page<DetectorUnitLogDto> findAll(Pageable pageRequest){
		//Return a DTO instead of an entity
		Page<DetectorUnitLogDto> records = detectorLogRepository.findAll(pageRequest).map(DetectorUnitLogDto::new);
		return records;
	}
}

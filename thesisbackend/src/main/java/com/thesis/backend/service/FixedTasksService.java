package com.thesis.backend.service;

import com.thesis.backend.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixedTasksService {
    private final AppConfig appConfig;
    private final DetectorUnitLogService detectorUnitLogService;


    @Scheduled(fixedRate = 1000)
    public void testLog() {
        log.info("test");
    }
}

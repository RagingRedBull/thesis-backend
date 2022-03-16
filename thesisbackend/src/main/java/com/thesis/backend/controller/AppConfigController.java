package com.thesis.backend.controller;

import com.thesis.backend.config.AppConfig;
import com.thesis.backend.model.entity.logs.PostFireReportLog;
import com.thesis.backend.service.PostFireReportService;
import com.thesis.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AppConfigController {
    private final PostFireReportService postFireReportService;
    private final ReportService reportService;
    private final AppConfig appConfig;

    @GetMapping("/alarming")
    public ResponseEntity<Object> getAlarmingMode() {
        return ResponseEntity.ok(appConfig.isAlarmingMode());
    }
    @GetMapping("/fire-drill")
    public ResponseEntity<Object> getFireDrillMode() {
        return ResponseEntity.ok(appConfig.isFireDrillMode());
    }
    @GetMapping("/alarming/update")
    public ResponseEntity<Object> setAlarmingMode(@RequestParam boolean enableAlarming) {
        PostFireReportLog currentPFR = postFireReportService.getReferenceOfActivePfr();
        if(currentPFR != null){
            currentPFR.setFireOut(LocalDateTime.now());
            postFireReportService.saveOne(currentPFR);
        }
        appConfig.setAlarmingMode(enableAlarming);
        return ResponseEntity.ok(appConfig.isAlarmingMode());
    }
    @GetMapping("/fire-drill/update")
    public ResponseEntity<Object> setFireDrillMode(@RequestParam boolean enableFireDrillMode) {
        if(enableFireDrillMode) {
            reportService.playFireDrillMode();
            reportService.sendSmsToUsers();
        }
        appConfig.setFireDrillMode(enableFireDrillMode);
        return ResponseEntity.ok(appConfig.isFireDrillMode());
    }
}

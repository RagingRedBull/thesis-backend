package com.thesis.backend.controller;

import com.thesis.backend.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AppConfigController {
    private final AppConfig appConfig;

    @GetMapping("/alarming")
    public ResponseEntity<Object> getAlarmingMode() {
        return ResponseEntity.ok(appConfig.isEnabledAlarmingMode());
    }
    @GetMapping("/alarming/update")
    public ResponseEntity<Object> setAlarmingMode(@RequestParam boolean enableAlarming) {
        appConfig.setEnabledAlarmingMode(enableAlarming);
        return ResponseEntity.ok(appConfig.isEnabledAlarmingMode());
    }
}

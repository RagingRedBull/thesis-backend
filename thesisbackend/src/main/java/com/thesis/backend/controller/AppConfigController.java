package com.thesis.backend.controller;

import com.thesis.backend.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AppConfigController {
    private final AppConfig appConfig;

    @GetMapping("/alarming")
    public ResponseEntity<Object> getAlarmingMode() {
        return ResponseEntity.ok(appConfig.isEnabledAlarmingMode());
    }
    @PostMapping("/alaraming/update")
    public ResponseEntity<Object> setAlarmingMode(@RequestBody boolean enableAlarming) {
        appConfig.setEnabledAlarmingMode(enableAlarming);
        return ResponseEntity.ok(appConfig.isEnabledAlarmingMode());
    }
}

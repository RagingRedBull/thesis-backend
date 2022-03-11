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
        return ResponseEntity.ok(appConfig.isAlarmingMode());
    }
    @GetMapping("/fire-drill")
    public ResponseEntity<Object> getFireDrillMode() {
        return ResponseEntity.ok(appConfig.isFireDrillMode());
    }
    @GetMapping("/alarming/update")
    public ResponseEntity<Object> setAlarmingMode(@RequestParam boolean enableAlarming) {
        appConfig.setAlarmingMode(enableAlarming);
        return ResponseEntity.ok(appConfig.isAlarmingMode());
    }
    @GetMapping("/fire-drill/update")
    public ResponseEntity<Object> setFireDrillMode(@RequestParam boolean enableFireDrillMode) {
        appConfig.setFireDrillMode(enableFireDrillMode);
        return ResponseEntity.ok(appConfig.isFireDrillMode());
    }
}

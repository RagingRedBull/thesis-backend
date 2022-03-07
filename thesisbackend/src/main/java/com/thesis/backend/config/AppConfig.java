package com.thesis.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:prmts.properties")
@Getter
@Setter
public class AppConfig {
    @Value("${prmts.alarming}")
    private boolean isEnabledAlarmingMode;
}

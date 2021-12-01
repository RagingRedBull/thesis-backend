package com.thesis.backend.config;

import com.thesis.backend.model.util.factory.SensorLogFactory;
import com.thesis.backend.model.util.factory.SensorUpdateFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfiguration {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SensorLogFactory sensorLogFactory() {
        return new SensorLogFactory();
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SensorUpdateFactory sensorUpdateFactory() {
        return new SensorUpdateFactory();
    }
}

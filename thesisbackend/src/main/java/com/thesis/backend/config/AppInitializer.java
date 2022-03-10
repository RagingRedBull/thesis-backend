package com.thesis.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class AppInitializer {
    @Autowired
    private Environment environment;
    private final Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    @PostConstruct
    public void init() {
        logger.info(environment.getProperty("server.images.linux-data-dir"));
        logger.info(environment.getProperty("spring.servlet.multipart.max-file-size"));
        logger.info(environment.getProperty("spring.servlet.multipart.max-request-size"));
    }
}

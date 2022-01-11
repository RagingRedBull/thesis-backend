package com.thesis.backend.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String osName = System.getProperty("os.name");
        if (osName.contains("Linux")){
            servletContext.setInitParameter("spring.profiles.active", "linux");
        } else {
            servletContext.setInitParameter("spring.profiles.active", "windows");
        }
    }
}

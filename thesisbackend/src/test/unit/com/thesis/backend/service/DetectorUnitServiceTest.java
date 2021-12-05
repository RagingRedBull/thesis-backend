package com.thesis.backend.service;

import com.thesis.backend.config.DetectorUnitServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@Import(DetectorUnitServiceTestConfig.class)
@WebMvcTest(DetectorUnitService.class)
class DetectorUnitServiceTest {
    @Autowired
    private DetectorUnitService detectorUnitService;

    @Test
    void given() {
    }
}
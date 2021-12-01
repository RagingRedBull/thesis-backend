package com.thesis.backend.service;

import com.thesis.backend.config.DetectorUnitServiceConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {DetectorUnitServiceConfig.class})
@WebAppConfiguration
class DetectorUnitServiceTest {

}
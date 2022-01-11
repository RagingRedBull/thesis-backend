package com.thesis.backend.controller;


import com.thesis.backend.BackendApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {BackendApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:integration-test.properties"})
public class LogsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LogsController logsController;
    private MultiValueMap<String, String> params;

    @Test
    public void givenPageSizeAndPageNumber_whenGetAllLogsPaged_thenBodyContentIsPageSizeAndStatusIsOk() throws Exception {
        params = new LinkedMultiValueMap<>();
        params.add("pageSize", String.valueOf(10));
        params.add("pageNumber", String.valueOf(0));
        mockMvc.perform(get("/log/all")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").value(hasSize(10)))
                .andExpect(jsonPath("$.pageable.pageNumber").value(is(0)))
                .andExpect(jsonPath("$.pageable.pageSize").value(is(10)));
    }

//    @Test
//    public void givenDetectorUnitLogId_whenGetSensorOfDetectorUnit_thenReturnSensorLogDtoAndStatusIsOk() throws Exception {
//        params = new LinkedMultiValueMap<>();
//        params.add("detectorUnitLogId", String.valueOf(1000L));
//        mockMvc.perform(get("/log/sensor")
//                        .params(params))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$").value(hasSize(3)))
//                .andExpect(jsonPath("$.[0].id").value(is(2999)))
//                .andExpect(jsonPath("$.[0].type").value(is("DHT")))
//                .andExpect(jsonPath("$.[0].name").value(is("DHT-22")))
//                .andExpect(jsonPath("$.[0].temperature").value(is(33.3)))
//                .andExpect(jsonPath("$.[0].humidity").value(is(68.8)))
//                .andExpect(jsonPath("$.[1].id").value(is(2998)))
//                .andExpect(jsonPath("$.[1].type").value(is("DHT")))
//                .andExpect(jsonPath("$.[1].name").value(is("DHT-11")))
//                .andExpect(jsonPath("$.[1].temperature").value(is(33.3)))
//                .andExpect(jsonPath("$.[1].humidity").value(is(67.0)))
//                .andExpect(jsonPath("$.[2].id").value(is(3000)))
//                .andExpect(jsonPath("$.[2].type").value(is("MQ")))
//                .andExpect(jsonPath("$.[2].name").value(is("MQ-2")))
//                .andExpect(jsonPath("$.[2].mqValue").value(is(775)));
//    }
    @Test
    public void givenDetectorUnitLogDto_whenUploadLog_thenPersistOne() {

    }
}

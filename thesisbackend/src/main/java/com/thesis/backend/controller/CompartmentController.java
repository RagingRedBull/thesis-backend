package com.thesis.backend.controller;

import com.thesis.backend.model.response.CompartmentLogResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/compartment")
public class CompartmentController {
    @GetMapping(name = "/{id}/logs")
    public ResponseEntity<CompartmentLogResponse> getCompartmentLogs(@PathVariable int compartmentId) {

        return null;
    }
}

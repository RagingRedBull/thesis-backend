package com.thesis.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageResourseController {
    @GetMapping(path = "/images")
    public String serveImage(){
        return "test";
    }
}

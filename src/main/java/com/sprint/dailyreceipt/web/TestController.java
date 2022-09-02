package com.sprint.dailyreceipt.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String healthCheck() {
        return "ok";
    }
}
package com.sprint.dailyreceipt.infra.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApi {

    @GetMapping("/")
    public ResponseEntity<Void> healthcheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

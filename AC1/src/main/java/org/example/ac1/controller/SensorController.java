package org.example.ac1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @PostMapping
    public void receberEvento(@RequestBody Map<String, Object> evento) {
        System.out.println("Evento IoT recebido: " + evento);
    }
}
package org.example.ac1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    /** Quantidade de eventos recentes mantidos em memoria para o painel do front. */
    static final int LIMITE_EVENTOS = 20;

    private final Deque<Map<String, Object>> recentes = new ConcurrentLinkedDeque<>();

    @PostMapping
    public void receberEvento(@RequestBody Map<String, Object> evento) {
        System.out.println("Evento IoT recebido: " + evento);

        Map<String, Object> registro = new LinkedHashMap<>(evento);
        registro.put("recebidoEm", Instant.now().toString());
        recentes.addFirst(registro);
        while (recentes.size() > LIMITE_EVENTOS) {
            recentes.pollLast();
        }
    }

    /** Ultimos eventos IoT recebidos, do mais recente para o mais antigo. */
    @GetMapping
    public List<Map<String, Object>> listarRecentes() {
        return new ArrayList<>(recentes);
    }
}

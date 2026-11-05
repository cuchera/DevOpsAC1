package org.example.ac1.status;

import org.example.ac1.ia.internal.FastApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Status resumido dos componentes para a faixa de status do front. */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final FastApiClient fastApiClient;

    public StatusController(FastApiClient fastApiClient) {
        this.fastApiClient = fastApiClient;
    }

    @GetMapping
    public StatusResponse status() {
        return new StatusResponse("UP", fastApiClient.disponivel());
    }

    public record StatusResponse(String core, boolean iaDisponivel) {
    }
}

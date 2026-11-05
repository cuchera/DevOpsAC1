package org.example.ac1.ia.api;

import org.example.ac1.ia.application.IaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos/{alunoId}/ia")
public class IaController {

    private final IaService service;

    public IaController(IaService service) {
        this.service = service;
    }

    @PostMapping("/recomendacao")
    public ResponseEntity<IaResponse> recomendar(
            @PathVariable Long alunoId) {
        return resposta(service.recomendar(alunoId));
    }

    @PostMapping("/assistente")
    public ResponseEntity<IaResponse> responder(
            @PathVariable Long alunoId,
            @RequestBody IaPerguntaRequest request) {
        return resposta(service.responder(alunoId, request.pergunta()));
    }

    private ResponseEntity<IaResponse> resposta(IaResponse response) {
        HttpStatus status = response.disponivel()
                ? HttpStatus.OK
                : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(response);
    }
}

package org.example.ac1.ia.internal;

import org.example.ac1.ia.api.IaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
public class FastApiClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FastApiClient.class);

    private final RestClient restClient;

    public FastApiClient(
            @Value("${ai.service.base-url:http://localhost:8000}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public IaResponse recomendar(Long alunoId, String nome, double mediaFinal) {
        try {
            IaPayload payload = restClient.post()
                    .uri("/recommendations")
                    .body(Map.of(
                            "studentId", alunoId,
                            "studentName", nome,
                            "finalGrade", mediaFinal))
                    .retrieve()
                    .body(IaPayload.class);
            return resposta(payload);
        } catch (RestClientException exception) {
            return indisponivel(exception);
        }
    }

    public IaResponse responder(Long alunoId, String pergunta) {
        try {
            IaPayload payload = restClient.post()
                    .uri("/assistant")
                    .body(Map.of(
                            "studentId", alunoId,
                            "question", pergunta))
                    .retrieve()
                    .body(IaPayload.class);
            return resposta(payload);
        } catch (RestClientException exception) {
            return indisponivel(exception);
        }
    }

    private IaResponse indisponivel(RestClientException exception) {
        LOGGER.warn("Serviço de IA indisponível: {}", exception.getMessage());
        return new IaResponse(
                false,
                "IA indisponível no momento; o fluxo principal continua funcionando.");
    }

    private IaResponse resposta(IaPayload payload) {
        if (payload == null || payload.content() == null
                || payload.content().isBlank()) {
            LOGGER.warn("Serviço de IA retornou uma resposta vazia.");
            return new IaResponse(
                    false,
                    "IA retornou uma resposta inválida; o fluxo principal continua funcionando.");
        }
        return new IaResponse(true, payload.content());
    }

    private record IaPayload(String content) {
    }
}

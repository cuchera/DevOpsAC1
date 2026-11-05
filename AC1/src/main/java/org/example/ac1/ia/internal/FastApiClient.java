package org.example.ac1.ia.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ac1.ia.api.IaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class FastApiClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FastApiClient.class);

    private final String baseUrl;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FastApiClient(
            @Value("${ai.service.base-url:http://localhost:8000}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /** Consulta o /health do servico de IA (usado pela faixa de status do front). */
    public boolean disponivel() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/health"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();
            HttpResponse<Void> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (IOException | InterruptedException
                 | IllegalArgumentException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    public IaResponse recomendar(Long alunoId, String nome, double mediaFinal) {
        return post("/recommendations",
                new RecommendationPayload(alunoId, nome, mediaFinal));
    }

    public IaResponse responder(Long alunoId, String pergunta) {
        return post("/assistant", new AssistantPayload(alunoId, pergunta));
    }

    private IaResponse post(String path, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(
                            json.getBytes(StandardCharsets.UTF_8)))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException(
                        "HTTP " + response.statusCode() + ": " + response.body());
            }

            IaPayload result = objectMapper.readValue(
                    response.body(), IaPayload.class);
            return resposta(result);
        } catch (IOException | InterruptedException
                 | IllegalArgumentException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return indisponivel(exception);
        }
    }

    private IaResponse indisponivel(Exception exception) {
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

    private record RecommendationPayload(
            Long studentId,
            String studentName,
            double finalGrade) {
    }

    private record AssistantPayload(Long studentId, String question) {
    }
}

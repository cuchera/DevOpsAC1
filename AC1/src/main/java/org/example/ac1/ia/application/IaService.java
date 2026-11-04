package org.example.ac1.ia.application;

import org.example.ac1.aluno.internal.persistence.AlunoEntity;
import org.example.ac1.aluno.internal.persistence.AlunoRepository;
import org.example.ac1.ia.api.IaResponse;
import org.example.ac1.ia.internal.FastApiClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IaService {

    private final AlunoRepository alunoRepository;
    private final FastApiClient fastApiClient;

    public IaService(
            AlunoRepository alunoRepository,
            FastApiClient fastApiClient) {
        this.alunoRepository = alunoRepository;
        this.fastApiClient = fastApiClient;
    }

    public IaResponse recomendar(Long alunoId) {
        AlunoEntity aluno = buscar(alunoId);
        return fastApiClient.recomendar(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMediaFinal());
    }

    public IaResponse responder(Long alunoId, String pergunta) {
        AlunoEntity aluno = buscar(alunoId);
        if (pergunta == null || pergunta.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe uma pergunta.");
        }
        return fastApiClient.responder(aluno.getId(), pergunta.trim());
    }

    private AlunoEntity buscar(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aluno não encontrado."));
    }
}

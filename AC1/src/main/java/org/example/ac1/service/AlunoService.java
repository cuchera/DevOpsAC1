package org.example.ac1.service;

import domain.Aluno;
import org.example.ac1.dto.AlunoRequest;
import org.example.ac1.dto.AlunoResponse;
import org.example.ac1.entity.AlunoEntity;
import org.example.ac1.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(AlunoResponse::de)
                .toList();
    }

    public AlunoResponse cadastrar(AlunoRequest request) {
        validarCurso(request);

        if (request.nome() == null || request.nome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o nome do aluno.");
        }

        AlunoEntity entidade = new AlunoEntity(request.nome().trim());
        aplicarResultado(entidade, request);

        return AlunoResponse.de(repository.save(entidade));
    }

    public AlunoResponse concluirOutroCurso(
            Long id, AlunoRequest request) {

        validarCurso(request);

        AlunoEntity entidade = buscar(id);
        aplicarResultado(entidade, request);

        return AlunoResponse.de(repository.save(entidade));
    }

    public AlunoResponse resgatar(Long id) {
        AlunoEntity entidade = buscar(id);

        if (entidade.getQuantidadeCursosExtras() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Aluno não possui saldo de cursos extras.");
        }

        entidade.resgatarCursoExtra();

        return AlunoResponse.de(repository.save(entidade));
    }

    private AlunoEntity buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aluno não encontrado."));
    }

    private void aplicarResultado(
            AlunoEntity entidade, AlunoRequest request) {

        Aluno dominio = new Aluno(
                entidade.getNome(),
                request.cursoConcluido(),
                request.mediaFinal()
        );

        dominio.processarEncerramentoDoCurso();

        entidade.registrarResultado(
                request.cursoConcluido(),
                request.mediaFinal(),
                dominio.getQuantidadeCursosExtras()
        );
    }

    private void validarCurso(AlunoRequest request) {
        if (request == null || request.cursoConcluido() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe se o curso foi concluído.");
        }

        Double media = request.mediaFinal();

        if (media == null || !Double.isFinite(media)
                || media < 0 || media > 10) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A média deve estar entre 0 e 10.");
        }
    }
}
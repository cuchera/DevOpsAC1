package org.example.ac1.aluno.application;

import org.example.ac1.aluno.domain.Aluno;
import org.example.ac1.aluno.api.AlunoRequest;
import org.example.ac1.aluno.api.AlunoResponse;
import org.example.ac1.aluno.internal.persistence.AlunoEntity;
import org.example.ac1.aluno.internal.persistence.AlunoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository repository;
    private final ApplicationEventPublisher eventos;

    public AlunoService(AlunoRepository repository,
                        ApplicationEventPublisher eventos) {
        this.repository = repository;
        this.eventos = eventos;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraResponse)
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

        AlunoEntity salvo = repository.save(entidade);
        publicarConclusao(salvo);

        return paraResponse(salvo);
    }

    public AlunoResponse concluirOutroCurso(
            Long id, AlunoRequest request) {

        validarCurso(request);

        AlunoEntity entidade = buscar(id);
        aplicarResultado(entidade, request);

        AlunoEntity salvo = repository.save(entidade);
        publicarConclusao(salvo);

        return paraResponse(salvo);
    }

    private void publicarConclusao(AlunoEntity salvo) {
        if (salvo.isCursoConcluido()) {
            eventos.publishEvent(new CursoConcluidoEvent(
                    salvo.getId(),
                    salvo.getNome(),
                    salvo.getMediaFinal(),
                    salvo.getQuantidadeCursosExtras(),
                    Instant.now()));
        }
    }

    public AlunoResponse resgatar(Long id) {
        AlunoEntity entidade = buscar(id);

        if (entidade.getQuantidadeCursosExtras() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Aluno não possui saldo de cursos extras.");
        }

        entidade.resgatarCursoExtra();

        return paraResponse(repository.save(entidade));
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

    private AlunoResponse paraResponse(AlunoEntity entidade) {
        Aluno dominio = new Aluno(
                entidade.getNome(),
                entidade.isCursoConcluido(),
                entidade.getMediaFinal()
        );

        dominio.processarEncerramentoDoCurso();

        return AlunoResponse.de(
                entidade.getId(),
                entidade.getNome(),
                entidade.isCursoConcluido(),
                entidade.getMediaFinal(),
                entidade.getQuantidadeCursosExtras(),
                dominio.getMensagem()
        );
    }
}
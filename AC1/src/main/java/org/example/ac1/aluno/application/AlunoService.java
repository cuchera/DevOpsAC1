package org.example.ac1.aluno.application;

import org.example.ac1.aluno.api.AlunoRequest;
import org.example.ac1.aluno.api.AlunoResponse;
import org.example.ac1.aluno.application.port.out.AlunoPort;
import org.example.ac1.aluno.application.port.out.AlunoPort.AlunoDados;
import org.example.ac1.aluno.domain.Aluno;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class AlunoService {

    private final AlunoPort repository;

    public AlunoService(AlunoPort repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar() {
        return repository.listar().stream()
                .map(this::paraResponse)
                .toList();
    }

    public AlunoResponse cadastrar(AlunoRequest request) {
        validarCurso(request);

        if (request.nome() == null || request.nome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o nome do aluno.");
        }

        Aluno dominio = new Aluno(
                request.nome().trim(),
                request.cursoConcluido(),
                request.mediaFinal()
        );
        dominio.processarEncerramentoDoCurso();

        AlunoDados dados = new AlunoDados(
                null, request.nome().trim(),
                request.cursoConcluido(), request.mediaFinal(),
                dominio.getQuantidadeCursosExtras()
        );

        return paraResponse(repository.salvar(dados));
    }

    public AlunoResponse concluirOutroCurso(Long id, AlunoRequest request) {
        validarCurso(request);

        AlunoDados atual = buscar(id);
        Aluno dominio = new Aluno(
                atual.nome(), request.cursoConcluido(), request.mediaFinal()
        );
        dominio.processarEncerramentoDoCurso();

        AlunoDados atualizado = new AlunoDados(
                atual.id(), atual.nome(),
                request.cursoConcluido(), request.mediaFinal(),
                atual.quantidadeCursosExtras()
                        + dominio.getQuantidadeCursosExtras()
        );

        return paraResponse(repository.salvar(atualizado));
    }

    public AlunoResponse resgatar(Long id) {
        AlunoDados atual = buscar(id);

        if (atual.quantidadeCursosExtras() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Aluno não possui saldo de cursos extras.");
        }

        AlunoDados atualizado = new AlunoDados(
                atual.id(), atual.nome(), atual.cursoConcluido(),
                atual.mediaFinal(), atual.quantidadeCursosExtras() - 1
        );

        return paraResponse(repository.salvar(atualizado));
    }

    private AlunoDados buscar(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aluno não encontrado."));
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

    private AlunoResponse paraResponse(AlunoDados dados) {
        Aluno dominio = new Aluno(
                dados.nome(), dados.cursoConcluido(), dados.mediaFinal()
        );
        dominio.processarEncerramentoDoCurso();

        return AlunoResponse.de(
                dados.id(), dados.nome(), dados.cursoConcluido(),
                dados.mediaFinal(), dados.quantidadeCursosExtras(),
                dominio.getMensagem()
        );
    }
}
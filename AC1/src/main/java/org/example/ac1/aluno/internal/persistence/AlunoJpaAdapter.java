package org.example.ac1.aluno.internal.persistence;

import org.example.ac1.aluno.application.port.out.AlunoPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlunoJpaAdapter implements AlunoPort {

    private final AlunoRepository repository;

    public AlunoJpaAdapter(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AlunoDados> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDados)
                .toList();
    }

    @Override
    public Optional<AlunoDados> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::paraDados);
    }

    @Override
    public AlunoDados salvar(AlunoDados dados) {
        AlunoEntity entidade = dados.id() == null
                ? new AlunoEntity(dados.nome())
                : repository.findById(dados.id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno não encontrado."));

        entidade.atualizarDados(
                dados.cursoConcluido(),
                dados.mediaFinal(),
                dados.quantidadeCursosExtras()
        );

        return paraDados(repository.save(entidade));
    }

    private AlunoDados paraDados(AlunoEntity entidade) {
        return new AlunoDados(
                entidade.getId(),
                entidade.getNome(),
                entidade.isCursoConcluido(),
                entidade.getMediaFinal(),
                entidade.getQuantidadeCursosExtras()
        );
    }
}
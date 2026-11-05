package org.example.ac1.aluno.fake;

import org.example.ac1.aluno.application.port.out.AlunoPort;
import org.example.ac1.aluno.application.port.out.AlunoPort.AlunoDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoFakeAdapter implements AlunoPort {

    private final List<AlunoDados> alunos = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public List<AlunoDados> listar() {
        return new ArrayList<>(alunos);
    }

    @Override
    public Optional<AlunoDados> buscarPorId(Long id) {
        return alunos.stream()
                .filter(aluno -> aluno.id().equals(id))
                .findFirst();
    }

    @Override
    public AlunoDados salvar(AlunoDados aluno) {
        AlunoDados salvo = new AlunoDados(
                aluno.id() == null ? proximoId++ : aluno.id(),
                aluno.nome(),
                aluno.cursoConcluido(),
                aluno.mediaFinal(),
                aluno.quantidadeCursosExtras()
        );

        alunos.removeIf(item -> item.id().equals(salvo.id()));
        alunos.add(salvo);

        return salvo;
    }
}
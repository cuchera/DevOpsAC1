package org.example.ac1.aluno.application.port.out;

import java.util.List;
import java.util.Optional;

public interface AlunoPort {

    List<AlunoDados> listar();

    Optional<AlunoDados> buscarPorId(Long id);

    AlunoDados salvar(AlunoDados aluno);

    record AlunoDados(
            Long id,
            String nome,
            boolean cursoConcluido,
            double mediaFinal,
            int quantidadeCursosExtras
    ) {}
}
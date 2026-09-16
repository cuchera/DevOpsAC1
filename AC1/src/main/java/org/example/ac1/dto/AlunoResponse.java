package org.example.ac1.dto;

import domain.Aluno;
import org.example.ac1.entity.AlunoEntity;

public record AlunoResponse(
        Long id,
        String nome,
        boolean cursoConcluido,
        double mediaFinal,
        int quantidadeCursosExtras,
        String mensagem
) {
    public static AlunoResponse de(AlunoEntity aluno) {
        Aluno dominio = new Aluno(
                aluno.getNome(),
                aluno.isCursoConcluido(),
                aluno.getMediaFinal()
        );

        dominio.processarEncerramentoDoCurso();

        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.isCursoConcluido(),
                aluno.getMediaFinal(),
                aluno.getQuantidadeCursosExtras(),
                dominio.getMensagem()
        );
    }
}
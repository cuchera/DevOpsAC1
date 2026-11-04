package org.example.ac1.aluno.application;

import java.time.Instant;

/**
 * Fato de dominio: "o aluno concluiu um curso e o resultado foi registrado".
 * Quem publica (modulo aluno) nao conhece quem consome.
 */
public record CursoConcluidoEvent(
        Long alunoId,
        String nome,
        double mediaFinal,
        int quantidadeCursosExtras,
        Instant ocorridoEm
) {
}

package org.example.ac1.aluno.api;

public record AlunoRequest(
        String nome,
        Boolean cursoConcluido,
        Double mediaFinal
) {
}
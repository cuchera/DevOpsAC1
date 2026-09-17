package org.example.ac1.dto;

public record AlunoRequest(
        String nome,
        Boolean cursoConcluido,
        Double mediaFinal
) {
}
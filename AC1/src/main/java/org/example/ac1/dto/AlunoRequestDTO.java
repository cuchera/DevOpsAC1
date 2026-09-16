package org.example.ac1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AlunoRequestDTO(
        @Schema(description = "Nome do aluno", example = "Leonardo da Silva")
        String nome,

        @Schema(description = "Indica se o curso foi concluído", example = "true")
        boolean cursoConcluido,

        @Schema(description = "Média final obtida no curso", example = "8.5")
        double mediaFinal
) {}
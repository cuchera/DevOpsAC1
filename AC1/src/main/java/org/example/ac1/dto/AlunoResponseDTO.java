package org.example.ac1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AlunoResponseDTO(
        @Schema(description = "ID gerado", example = "1")
        Long id,

        @Schema(description = "Nome do aluno", example = "Leonardo Cuchera")
        String nome,

        @Schema(description = "Status de conclusão", example = "true")
        boolean cursoConcluido,

        @Schema(description = "Média final", example = "8.5")
        double mediaFinal,

        @Schema(description = "Cursos extras concedidos", example = "3")
        int quantidadeCursosExtras,

        @Schema(description = "Indica direito a cursos extras", example = "true")
        boolean temDireitoACursosExtras
) {}
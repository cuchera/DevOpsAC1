package org.example.ac1.aluno.api;

public record AlunoResponse(
        Long id,
        String nome,
        boolean cursoConcluido,
        double mediaFinal,
        int quantidadeCursosExtras,
        String mensagem
) {
    public static AlunoResponse de(
            Long id,
            String nome,
            boolean cursoConcluido,
            double mediaFinal,
            int quantidadeCursosExtras,
            String mensagem
    ) {
        return new AlunoResponse(
                id,
                nome,
                cursoConcluido,
                mediaFinal,
                quantidadeCursosExtras,
                mensagem
        );
    }
}
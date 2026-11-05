package org.example.ac1.recompensa.domain;

public class ProgressaoAluno {

    private static final int CURSOS_PARA_PREMIUM = 12;

    private int cursosConcluidos;
    private boolean planoPremium;
    private boolean voucherDisponivel;
    private final CarteiraMoedas carteira;

    public ProgressaoAluno(CarteiraMoedas carteira) {
        this.carteira = carteira;
    }

    public void concluirCurso() {
        cursosConcluidos++;

        if (cursosConcluidos >= CURSOS_PARA_PREMIUM
                && !planoPremium) {

            planoPremium = true;
            voucherDisponivel = true;
            carteira.adicionarMoedas(3);
        }
    }

    public boolean isPlanoPremium() {
        return planoPremium;
    }

    public boolean isVoucherDisponivel() {
        return voucherDisponivel;
    }

    public int getCursosConcluidos() {
        return cursosConcluidos;
    }
}
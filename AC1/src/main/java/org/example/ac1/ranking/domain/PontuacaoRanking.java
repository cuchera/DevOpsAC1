package org.example.ac1.ranking.domain;

public class PontuacaoRanking {

    private final Long alunoId;
    private final String nomeAluno;
    private int pontos;

    public PontuacaoRanking(Long alunoId, String nomeAluno) {
        this.alunoId = alunoId;
        this.nomeAluno = nomeAluno;
        this.pontos = 0;
    }

    public void registrarTopico() {
        pontos += 2;
    }

    public void registrarComentarioUtil() {
        pontos++;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public int getPontos() {
        return pontos;
    }
}
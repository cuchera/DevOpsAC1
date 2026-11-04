package org.example.ac1.conquista.domain;

public class Conquista {

    private final String nome;
    private final int cursosNecessarios;
    private boolean desbloqueada;

    public Conquista(String nome, int cursosNecessarios) {
        this.nome = nome;
        this.cursosNecessarios = cursosNecessarios;
        this.desbloqueada = false;
    }

    public void verificarDesbloqueio(int cursosConcluidos) {
        if (cursosConcluidos >= cursosNecessarios) {
            desbloqueada = true;
        }
    }

    public String getNome() {
        return nome;
    }

    public boolean isDesbloqueada() {
        return desbloqueada;
    }
}
package org.example.ac1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long versao;

    private String nome;
    private boolean cursoConcluido;
    private double mediaFinal;
    private int quantidadeCursosExtras;

    protected AlunoEntity() {
    }

    public AlunoEntity(String nome) {
        this.nome = nome;
    }

    public void registrarResultado(
            boolean cursoConcluido,
            double mediaFinal,
            int creditosConcedidos) {

        if (creditosConcedidos < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de créditos não pode ser negativa.");
        }

        this.cursoConcluido = cursoConcluido;
        this.mediaFinal = mediaFinal;
        this.quantidadeCursosExtras += creditosConcedidos;
    }

    public void resgatarCursoExtra() {
        if (quantidadeCursosExtras <= 0) {
            throw new IllegalStateException(
                    "Aluno não possui saldo de cursos extras.");
        }

        quantidadeCursosExtras--;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isCursoConcluido() {
        return cursoConcluido;
    }

    public double getMediaFinal() {
        return mediaFinal;
    }

    public int getQuantidadeCursosExtras() {
        return quantidadeCursosExtras;
    }
}
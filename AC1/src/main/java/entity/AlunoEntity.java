package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private boolean cursoConcluido;
    private double mediaFinal;
    private int quantidadeCursosExtras = 0;
    private String mensagem;

    public AlunoEntity() {}

    public AlunoEntity(String nome, boolean cursoConcluido, double mediaFinal) {
        this.nome = nome;
        this.cursoConcluido = cursoConcluido;
        this.mediaFinal = mediaFinal;
    }

    public void acumularCursosExtras(int quantidade) {
        this.quantidadeCursosExtras += quantidade;
    }

    public void usarCursoExtra() {
        if (this.quantidadeCursosExtras > 0) {
            this.quantidadeCursosExtras--;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public boolean isCursoConcluido() { return cursoConcluido; }
    public void setCursoConcluido(boolean cursoConcluido) { this.cursoConcluido = cursoConcluido; }

    public double getMediaFinal() { return mediaFinal; }
    public void setMediaFinal(double mediaFinal) { this.mediaFinal = mediaFinal; }

    public int getQuantidadeCursosExtras() { return quantidadeCursosExtras; }
    public void setQuantidadeCursosExtras(int quantidadeCursosExtras) { this.quantidadeCursosExtras = quantidadeCursosExtras; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
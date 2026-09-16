package domain;

public class Aluno {

    private final boolean cursoConcluido;
    private final double mediaFinal;
    private int quantidadeCursosExtras;

    public Aluno(boolean cursoConcluido, double mediaFinal) {
        this.cursoConcluido = cursoConcluido;
        this.mediaFinal = mediaFinal;
    }

    public void processarEncerramentoDoCurso() {
        if (cursoConcluido && mediaFinal > 7.0) {
            quantidadeCursosExtras = 3;
        } else {
            quantidadeCursosExtras = 0;
        }
    }

    public boolean temDireitoACursosExtras() {
        return quantidadeCursosExtras > 0;
    }

    public int getQuantidadeCursosExtras() {
        return quantidadeCursosExtras;
    }
}
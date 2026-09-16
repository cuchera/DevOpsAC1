package domain;

public class Aluno {

    private static final double MEDIA_MINIMA_PARA_CURSOS_EXTRAS = 7.0;
    private static final int QUANTIDADE_DE_CURSOS_EXTRAS = 3;

    private final boolean cursoConcluido;
    private final double mediaFinal;
    private int quantidadeCursosExtras;

    public Aluno(boolean cursoConcluido, double mediaFinal) {
        this.cursoConcluido = cursoConcluido;
        this.mediaFinal = mediaFinal;
    }

    public void processarEncerramentoDoCurso() {
        quantidadeCursosExtras = alunoAtendeAosCriterios()
                ? QUANTIDADE_DE_CURSOS_EXTRAS
                : 0;
    }

    private boolean alunoAtendeAosCriterios() {
        return cursoConcluido
                && mediaFinal > MEDIA_MINIMA_PARA_CURSOS_EXTRAS;
    }

    public boolean temDireitoACursosExtras() {
        return quantidadeCursosExtras == QUANTIDADE_DE_CURSOS_EXTRAS;
    }

    public int getQuantidadeCursosExtras() {
        return quantidadeCursosExtras;
    }
}
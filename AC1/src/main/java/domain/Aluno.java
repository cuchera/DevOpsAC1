package domain;

public class Aluno {

    private static final double MEDIA_MINIMA_PARA_CURSOS_EXTRAS = 7.0;
    private static final int QUANTIDADE_DE_CURSOS_EXTRAS = 3;

    private static final String MENSAGEM_RECUPERACAO = "Aluno tem direito a uma recuperação";
    private static final String MENSAGEM_APROVADO = "Parabéns! Você ganhou 3 cursos extras.";
    private static final String MENSAGEM_SEM_DIREITO = "Sem direito a cursos extras.";

    private String nome;
    private final boolean cursoConcluido;
    private final double mediaFinal;
    private int quantidadeCursosExtras;
    private String mensagem = "";

    // Construtor principal
    public Aluno(String nome, boolean cursoConcluido, double mediaFinal) {
        this.nome = nome;
        this.cursoConcluido = cursoConcluido;
        this.mediaFinal = mediaFinal;
    }

    // Construtor de compatibilidade (para quando não passa o nome)
    public Aluno(boolean cursoConcluido, double mediaFinal) {
        this("Aluno", cursoConcluido, mediaFinal);
    }

    public void processarEncerramentoDoCurso() {
        if (alunoAtendeAosCriterios()) {
            this.quantidadeCursosExtras = QUANTIDADE_DE_CURSOS_EXTRAS;
            this.mensagem = MENSAGEM_APROVADO;
        } else if (alunoTemDireitoARecuperacao()) {
            this.quantidadeCursosExtras = 0;
            this.mensagem = MENSAGEM_RECUPERACAO;
        } else {
            this.quantidadeCursosExtras = 0;
            this.mensagem = MENSAGEM_SEM_DIREITO;
        }
    }

    private boolean alunoAtendeAosCriterios() {
        return cursoConcluido && mediaFinal > MEDIA_MINIMA_PARA_CURSOS_EXTRAS;
    }

    private boolean alunoTemDireitoARecuperacao() {
        return cursoConcluido && mediaFinal == MEDIA_MINIMA_PARA_CURSOS_EXTRAS;
    }

    public boolean temDireitoACursosExtras() {
        return quantidadeCursosExtras == QUANTIDADE_DE_CURSOS_EXTRAS;
    }

    public int getQuantidadeCursosExtras() {
        return quantidadeCursosExtras;
    }

    public String getMensagem() {
        return mensagem;
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
}
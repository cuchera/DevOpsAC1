package domain;

public class Aluno {

    private final String nome;
    private int cursosExtrasDisponiveis = 0;
    private String mensagem;

    public Aluno(String nome) {
        this.nome = nome;
    }

    public void encerrarCurso(double mediaFinal) {
        if (mediaFinal > 7.0) {
            this.cursosExtrasDisponiveis += 3;
        } else if (mediaFinal == 7.0) {
            this.mensagem = "Aluno tem direito a uma recuperação";
        }
    }

    public int getCursosExtrasDisponiveis() {
        return cursosExtrasDisponiveis;
    }

    public String getNome() {
        return nome;
    }

    public String getMensagem() {
        return mensagem;
    }
}
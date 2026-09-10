package domain;

public class Aluno {

    private String mensagem;

    public void encerrarCurso(double mediaFinal) {
        if (mediaFinal == 7.0) {
            mensagem = "Aluno tem direito a uma recuperação";
        }
    }

    public String getMensagem() {
        return mensagem;
    }
}

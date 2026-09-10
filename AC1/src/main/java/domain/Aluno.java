package domain;

public class Aluno {

    private boolean direitoRecuperacao = false;

    public void encerrarCurso(double mediaFinal) {
        if (mediaFinal == 7.0) {
            direitoRecuperacao = true;
        }
    }

    public boolean getDireitoRecuperacao() {
        return direitoRecuperacao;
    }
}

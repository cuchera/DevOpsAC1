package domain;

public class Aluno {

    private int cursosExtras;
    private boolean acessoAoCursoExtra;

    public void matricularCursoExtra() {
        if (cursosExtras > 0) {
            cursosExtras--;
            concederAcessoAoCursoExtra();
        }
    }

    private void concederAcessoAoCursoExtra() {
        acessoAoCursoExtra = true;
    }

    public boolean temAcessoAoCursoExtra() {
        return acessoAoCursoExtra;
    }

    public int getCursosExtras() {
        return cursosExtras;
    }

    public void setCursosExtras(int cursosExtras) {
        this.cursosExtras = cursosExtras;
    }
}
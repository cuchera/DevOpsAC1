package domain;

public class Aluno {

    private int cursosExtras;

    public void matricularCursoExtra() {
        if (cursosExtras > 0) {
            cursosExtras--;
        }
    }

    public int getCursosExtras() {
        return cursosExtras;
    }

    public void setCursosExtras(int cursosExtras) {
        this.cursosExtras = cursosExtras;
    }
}

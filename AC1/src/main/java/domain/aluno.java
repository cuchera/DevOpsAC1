package domain;

public class Aluno {

    private final String nome;
    private int cursosExtrasDisponiveis = 0;

    public Aluno(String nome) {
            this.nome = nome;
        }

        public void encerrarCurso(double mediaFinal) {
            if (mediaFinal >= 7.0) {
                this.cursosExtrasDisponiveis = this.cursosExtrasDisponiveis + 1;
            };
        }

        public int getCursosExtrasDisponiveis() {
            return cursosExtrasDisponiveis;
        }

        public String getNome() {
            return nome;
        }
    }

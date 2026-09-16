package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlunoTest {

    @Test
    public void deveInformarDireitoARecuperacaoQuandoMediaForIgualASete() {
        Aluno aluno = new Aluno("Ariane");

        aluno.encerrarCurso(7.0);

        Assertions.assertEquals(
                "Aluno tem direito a uma recuperação",
                aluno.getMensagem()
        );
    }

    @Test
    void deveConcederTresCursosExtrasQuandoMediaForMaiorQueSete() {
        Aluno aluno = new Aluno("Leonardo");

        int totalQueDeveDar = aluno.getCursosExtrasDisponiveis() + 3;
        aluno.encerrarCurso(9.5);

        Assertions.assertEquals(
                totalQueDeveDar,
                aluno.getCursosExtrasDisponiveis()
        );
    }
}
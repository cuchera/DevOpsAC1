package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AlunoTest {

    @Test
    void deveConcederTresCursosExtrasQuandoMediaForMaiorQueSete() {
        Aluno aluno = new Aluno("Leonardo");

        int totalQueDeveDar= aluno.getCursosExtrasDisponiveis() + 3;
        aluno.encerrarCurso(9.5);

        Assertions.assertEquals(totalQueDeveDar, aluno.getCursosExtrasDisponiveis());
    }

}

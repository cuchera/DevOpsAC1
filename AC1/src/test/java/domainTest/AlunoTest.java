package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlunoTest {

    @Test
    void deveConcederAcessoAoTerceiroCursoExtra() {

        Aluno aluno = new Aluno();
        aluno.setCursosExtras(3);

        aluno.matricularCursoExtra();
        aluno.matricularCursoExtra();

        aluno.matricularCursoExtra();

        assertTrue(aluno.temAcessoAoCursoExtra());
    }
}
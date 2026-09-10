package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    void deveZerarSaldoAoMatricularTerceiroCursoExtra() {

        Aluno aluno = new Aluno();
        aluno.setCursosExtras(3);

        aluno.matricularCursoExtra();
        aluno.matricularCursoExtra();
        aluno.matricularCursoExtra();

        assertEquals(0, aluno.getCursosExtras());
    }
}

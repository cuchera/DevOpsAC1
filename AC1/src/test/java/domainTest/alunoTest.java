package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class alunoTest {

    @Test
    public void deveTerDireitoARecuperacaoQuandoMediaForIgualASete() {

        Aluno aluno = new Aluno();

        aluno.encerrarCurso(7.0);

        Assertions.assertTrue(aluno.getDireitoRecuperacao());
    }
}

package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class alunoTest {

    @Test
    public void deveInformarDireitoARecuperacaoQuandoMediaForIgualASete() {

        Aluno aluno = new Aluno();

        aluno.encerrarCurso(7.0);

        Assertions.assertEquals(
            "Aluno tem direito a uma recuperação",
            aluno.getMensagem()
        );
    }
}

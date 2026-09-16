package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AlunoTest {

    @Test
    @DisplayName("BDD 2 - Leonardo - aluno com média inferior a 7 não recebe cursos extras")
    void naoDeveConcederCursosExtrasParaAlunoComMediaInferiorASete() {
        // Dado um aluno que concluiu um curso
        // E sua média final foi inferior a 7,0
        Aluno aluno = new Aluno(true, 6.9);

        // Quando o sistema processa o encerramento do curso
        aluno.processarEncerramentoDoCurso();

        // Então ele não deve ter direito a escolher mais 3 cursos
        assertFalse(aluno.temDireitoACursosExtras());
        assertEquals(0, aluno.getQuantidadeCursosExtras());
    }
}
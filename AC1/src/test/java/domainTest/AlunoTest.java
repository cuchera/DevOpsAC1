package domainTest;

import domain.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    @DisplayName("Teste complementar - concede 3 cursos quando o aluno atende aos critérios")
    void deveConcederTresCursosQuandoAlunoAtendeAosCriterios() {
        Aluno aluno = new Aluno(true, 8.0);

        aluno.processarEncerramentoDoCurso();

        assertTrue(aluno.temDireitoACursosExtras());
        assertEquals(3, aluno.getQuantidadeCursosExtras());
    }

    @Test
    @DisplayName("Teste complementar - não concede cursos quando o curso não foi concluído")
    void naoDeveConcederCursosQuandoCursoNaoFoiConcluido() {
        Aluno aluno = new Aluno(false, 8.0);

        aluno.processarEncerramentoDoCurso();

        assertFalse(aluno.temDireitoACursosExtras());
        assertEquals(0, aluno.getQuantidadeCursosExtras());
    }
}
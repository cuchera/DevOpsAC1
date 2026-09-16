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

    @Test
    @DisplayName("BDD 3 - Ariane - informa direito a recuperação quando média for igual a 7.0")
    void deveInformarDireitoARecuperacaoQuandoMediaForIgualASete() {
        // Dado uma aluna instanciada com média 7.0
        Aluno aluno = new Aluno(true, 7.0);

        // Quando o sistema processa o encerramento
        aluno.processarEncerramentoDoCurso();

        // Então informa a recuperação e não libera cursos extras
        assertEquals("Aluno tem direito a uma recuperação", aluno.getMensagem());
        assertEquals(0, aluno.getQuantidadeCursosExtras());
        assertFalse(aluno.temDireitoACursosExtras());
    }

    @Test
    void deveConcederAcessoAoTerceiroCursoExtra() {
        Aluno aluno = new Aluno(true, 8.0);
        aluno.processarEncerramentoDoCurso();

        assertEquals(3, aluno.getQuantidadeCursosExtras());

        aluno.matricularCursoExtra();
        assertEquals(2, aluno.getQuantidadeCursosExtras());

        aluno.matricularCursoExtra();
        assertEquals(1, aluno.getQuantidadeCursosExtras());

        aluno.matricularCursoExtra();
        assertEquals(0, aluno.getQuantidadeCursosExtras());
        assertTrue(aluno.temAcessoAoCursoExtra());

        aluno.matricularCursoExtra();
        assertEquals(0, aluno.getQuantidadeCursosExtras());
    }

        @Test
        void naoDeveConcederAcessoSemSaldo() {
            Aluno aluno = new Aluno(true, 6.9);
            aluno.processarEncerramentoDoCurso();

            aluno.matricularCursoExtra();

            assertEquals(0, aluno.getQuantidadeCursosExtras());
            assertFalse(aluno.temAcessoAoCursoExtra());
        }

    @Test
    void devePreservarDadosInformadosNoCadastro() {
        Aluno aluno = new Aluno("Leonardo", true, 8.5);

        assertEquals("Leonardo", aluno.getNome());
        assertTrue(aluno.isCursoConcluido());
        assertEquals(8.5, aluno.getMediaFinal(), 0.001);
    }

    @Test
    void deveAtualizarSaldoEConsumirCreditoNaMatricula() {
        Aluno aluno = new Aluno(true, 8.0);

        aluno.setCursosExtras(3);

        assertEquals(3, aluno.getCursosExtras());
        assertEquals(3, aluno.getQuantidadeCursosExtras());

        aluno.matricularCursoExtra();

        assertEquals(2, aluno.getCursosExtras());
        assertEquals(2, aluno.getQuantidadeCursosExtras());
        assertTrue(aluno.temAcessoAoCursoExtra());
    }
    }
package domainTest;

import org.example.ac1.recompensa.domain.CarteiraMoedas;
import org.example.ac1.recompensa.domain.ProgressaoAluno;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressaoAlunoTest {

    @Test
    void deveConcederPremiumAoConcluirDozeCursos() {
        CarteiraMoedas carteira = new CarteiraMoedas();
        ProgressaoAluno aluno = new ProgressaoAluno(carteira);

        for (int i = 0; i < 12; i++) {
            aluno.concluirCurso();
        }

        assertTrue(aluno.isPlanoPremium());
        assertTrue(aluno.isVoucherDisponivel());
        assertEquals(3, carteira.getSaldo());
    }

    @Test
    void naoDeveConcederPremiumAntesDeDozeCursos() {
        CarteiraMoedas carteira = new CarteiraMoedas();
        ProgressaoAluno aluno = new ProgressaoAluno(carteira);

        for (int i = 0; i < 11; i++) {
            aluno.concluirCurso();
        }

        assertFalse(aluno.isPlanoPremium());
        assertFalse(aluno.isVoucherDisponivel());
        assertEquals(0, carteira.getSaldo());
    }

    @Test
    void naoDeveConcederMoedasPremiumMaisDeUmaVez() {
        CarteiraMoedas carteira = new CarteiraMoedas();
        ProgressaoAluno aluno = new ProgressaoAluno(carteira);

        for (int i = 0; i < 15; i++) {
            aluno.concluirCurso();
        }

        assertEquals(3, carteira.getSaldo());
    }
}
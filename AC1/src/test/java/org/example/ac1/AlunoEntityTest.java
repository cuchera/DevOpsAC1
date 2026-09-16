package org.example.ac1;

import org.example.ac1.entity.AlunoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoEntityTest {

    @Test
    void deveRejeitarCreditosNegativosSemAlterarSaldo() {
        AlunoEntity aluno = new AlunoEntity("Ana");
        aluno.registrarResultado(true, 8.0, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> aluno.registrarResultado(true, 9.0, -1)
        );

        assertEquals(3, aluno.getQuantidadeCursosExtras());
        assertEquals(8.0, aluno.getMediaFinal(), 0.001);
    }

    @Test
    void deveBloquearResgateSemSaldo() {
        AlunoEntity aluno = new AlunoEntity("Ana");

        assertThrows(
                IllegalStateException.class,
                aluno::resgatarCursoExtra
        );

        assertEquals(0, aluno.getQuantidadeCursosExtras());
    }

    @Test
    void devePermitirConsumirSaldoSemFicarNegativo() {
        AlunoEntity aluno = new AlunoEntity("Ana");
        aluno.registrarResultado(true, 8.0, 3);

        aluno.resgatarCursoExtra();
        aluno.resgatarCursoExtra();
        aluno.resgatarCursoExtra();

        assertEquals(0, aluno.getQuantidadeCursosExtras());

        assertThrows(
                IllegalStateException.class,
                aluno::resgatarCursoExtra
        );

        assertEquals(0, aluno.getQuantidadeCursosExtras());
    }
}
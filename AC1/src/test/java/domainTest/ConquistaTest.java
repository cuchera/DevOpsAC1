package domainTest;

import org.example.ac1.conquista.domain.Conquista;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConquistaTest {

    @Test
    void deveDesbloquearConquistaAoConcluirCursosNecessarios() {
        Conquista conquista = new Conquista("Aluno dedicado", 5);

        conquista.verificarDesbloqueio(5);

        assertTrue(conquista.isDesbloqueada());
    }

    @Test
    void naoDeveDesbloquearConquistaAntesDaMeta() {
        Conquista conquista = new Conquista("Aluno dedicado", 5);

        conquista.verificarDesbloqueio(4);

        assertFalse(conquista.isDesbloqueada());
    }
}
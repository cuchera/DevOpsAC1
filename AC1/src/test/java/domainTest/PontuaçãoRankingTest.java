package domainTest;

import org.example.ac1.ranking.domain.PontuacaoRanking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PontuacaoRankingTest {

    @Test
    void deveIniciarComZeroPontos() {
        PontuacaoRanking ranking = new PontuacaoRanking(1L, "Gabriel");
        assertEquals(0, ranking.getPontos());
    }

    @Test
    void deveAdicionarDoisPontosPorTopico() {
        PontuacaoRanking ranking = new PontuacaoRanking(1L, "Gabriel");
        ranking.registrarTopico();
        assertEquals(2, ranking.getPontos());
    }

    @Test
    void deveAdicionarUmPontoPorComentarioUtil() {
        PontuacaoRanking ranking = new PontuacaoRanking(1L, "Gabriel");
        ranking.registrarComentarioUtil();
        assertEquals(1, ranking.getPontos());
    }

    @Test
    void deveSomarTopicosEComentarios() {
        PontuacaoRanking ranking = new PontuacaoRanking(1L, "Gabriel");
        ranking.registrarTopico();
        ranking.registrarTopico();
        ranking.registrarComentarioUtil();
        assertEquals(5, ranking.getPontos());
    }
}
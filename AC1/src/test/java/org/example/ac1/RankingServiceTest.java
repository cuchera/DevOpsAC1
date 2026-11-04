package domainTest;

import org.example.ac1.ranking.application.RankingService;
import org.example.ac1.ranking.domain.PontuacaoRanking;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RankingServiceTest {

    @Test
    void deveOrdenarAlunosDoMaiorParaOMenor() {
        PontuacaoRanking aluno1 = new PontuacaoRanking(1L, "Gabriel");
        PontuacaoRanking aluno2 = new PontuacaoRanking(2L, "Leonardo");
        PontuacaoRanking aluno3 = new PontuacaoRanking(3L, "Ariane");

        aluno1.registrarTopico(); // 2 pontos
        aluno2.registrarTopico();
        aluno2.registrarTopico(); // 4 pontos
        aluno3.registrarComentarioUtil(); // 1 ponto

        RankingService service = new RankingService();

        List<PontuacaoRanking> resultado = service.classificar(
                List.of(aluno1, aluno2, aluno3)
        );

        assertEquals("Leonardo", resultado.get(0).getNomeAluno());
        assertEquals("Gabriel", resultado.get(1).getNomeAluno());
        assertEquals("Ariane", resultado.get(2).getNomeAluno());
    }
}
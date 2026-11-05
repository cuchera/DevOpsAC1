package org.example.ac1;

import org.example.ac1.aluno.api.AlunoRequest;
import org.example.ac1.aluno.application.AlunoService;
import org.example.ac1.observabilidade.MetricasArquiteturais;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Evidencia do Desafio 6: as metricas refletem a decisao do ADR-004. */
class MetricasArquiteturaisTest {

    private static void esperar(BooleanSupplier cond) throws InterruptedException {
        for (int i = 0; i < 50 && !cond.getAsBoolean(); i++) Thread.sleep(100);
    }

    @Nested
    @SpringBootTest(properties = {
            "spring.datasource.url=jdbc:h2:mem:d6-falha;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "app.notificacao.modo=assincrono",
            "app.notificacao.simular-indisponibilidade=true"})
    class ConsumidorIndisponivel {
        @Autowired AlunoService service;
        @Autowired MetricasArquiteturais metricas;

        @Test
        void conclusoesSobemMesmoComNotificacoesFalhando() throws Exception {
            double conclusoesAntes = metricas.conclusoes();
            double falhasAntes = metricas.notificacoesComFalha();

            service.cadastrar(new AlunoRequest("Ana", true, 8.0));
            service.cadastrar(new AlunoRequest("Leo", true, 9.0));

            assertEquals(conclusoesAntes + 2, metricas.conclusoes());
            esperar(() -> metricas.notificacoesComFalha() >= falhasAntes + 2);
            assertEquals(falhasAntes + 2, metricas.notificacoesComFalha());
        }
    }

    @Nested
    @SpringBootTest(properties = {
            "spring.datasource.url=jdbc:h2:mem:d6-ok;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "app.notificacao.modo=assincrono"})
    class ConsumidorDisponivel {
        @Autowired AlunoService service;
        @Autowired MetricasArquiteturais metricas;

        @Test
        void notificacaoEntregueEhContada() throws Exception {
            double entreguesAntes = metricas.notificacoesEntregues();

            service.cadastrar(new AlunoRequest("Bia", true, 9.5));

            esperar(() -> metricas.notificacoesEntregues() >= entreguesAntes + 1);
            assertEquals(entreguesAntes + 1, metricas.notificacoesEntregues());
        }

        @Test
        void cursoNaoConcluidoNaoContaConclusao() {
            double conclusoesAntes = metricas.conclusoes();

            service.cadastrar(new AlunoRequest("Caio", false, 5.0));

            assertEquals(conclusoesAntes, metricas.conclusoes());
        }
    }
}

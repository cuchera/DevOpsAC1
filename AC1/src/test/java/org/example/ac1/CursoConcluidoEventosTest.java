package org.example.ac1;

import org.example.ac1.aluno.api.AlunoRequest;
import org.example.ac1.aluno.application.AlunoService;
import org.example.ac1.aluno.internal.persistence.AlunoRepository;
import org.example.ac1.notificacao.NotificacaoSender;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/** Evidencia do Desafio 4: sincrono acoplado x assincrono com consumidor indisponivel. */
class CursoConcluidoEventosTest {

    private static void esperar(java.util.function.BooleanSupplier cond) throws InterruptedException {
        for (int i = 0; i < 50 && !cond.getAsBoolean(); i++) Thread.sleep(100);
    }

    @Nested
    @SpringBootTest(properties = {
            "spring.datasource.url=jdbc:h2:mem:d4-sync;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "app.notificacao.modo=sincrono",
            "app.notificacao.simular-indisponibilidade=true"})
    class Sincrono {
        @Autowired AlunoService service;
        @Autowired AlunoRepository repository;

        @Test
        void falhaDeNotificacaoDerrubaAConclusaoEFazRollback() {
            repository.deleteAll();
            assertThrows(IllegalStateException.class,
                    () -> service.cadastrar(new AlunoRequest("Ana", true, 8.0)));
            assertEquals(0, repository.count(), "rollback: nada foi persistido");
        }
    }

    @Nested
    @SpringBootTest(properties = {
            "spring.datasource.url=jdbc:h2:mem:d4-async-down;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "app.notificacao.modo=assincrono",
            "app.notificacao.simular-indisponibilidade=true"})
    class AssincronoComConsumidorIndisponivel {
        @Autowired AlunoService service;
        @Autowired AlunoRepository repository;
        @Autowired NotificacaoSender sender;

        @Test
        void conclusaoPersisteMesmoComConsumidorForaDoAr() throws Exception {
            repository.deleteAll();
            var resp = service.cadastrar(new AlunoRequest("Ana", true, 8.0));
            assertEquals(3, resp.quantidadeCursosExtras());
            assertEquals(1, repository.count(), "conclusao e recompensa persistidas");
            esperar(() -> sender.tentativas() >= 1);
            assertTrue(sender.tentativas() >= 1, "tentou notificar e falhou isoladamente");
        }
    }

    @Nested
    @SpringBootTest(properties = {
            "spring.datasource.url=jdbc:h2:mem:d4-async-ok;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "app.notificacao.modo=assincrono"})
    class AssincronoComConsumidorDisponivel {
        @Autowired AlunoService service;
        @Autowired AlunoRepository repository;
        @Autowired NotificacaoSender sender;

        @Test
        void notificacaoEhEntregueDepoisDoCommit() throws Exception {
            repository.deleteAll();
            service.cadastrar(new AlunoRequest("Leo", true, 9.0));
            esperar(() -> sender.tentativas() >= 1);
            assertEquals(1, sender.tentativas());
        }

        @Test
        void cursoNaoConcluidoNaoPublicaEvento() throws Exception {
            repository.deleteAll();
            int antes = sender.tentativas();
            service.cadastrar(new AlunoRequest("Bia", false, 5.0));
            Thread.sleep(500);
            assertEquals(antes, sender.tentativas());
        }
    }
}

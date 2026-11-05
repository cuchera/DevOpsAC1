package org.example.ac1.notificacao;

import org.example.ac1.aluno.application.CursoConcluidoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/** Consumidor externo simulado. Com a flag ligada, finge estar indisponivel. */
@Component
public class LogNotificacaoSender implements NotificacaoSender {

    private static final Logger log = LoggerFactory.getLogger(LogNotificacaoSender.class);

    private final boolean simularIndisponibilidade;
    private final AtomicInteger tentativas = new AtomicInteger();

    public LogNotificacaoSender(
            @Value("${app.notificacao.simular-indisponibilidade:false}") boolean simularIndisponibilidade) {
        this.simularIndisponibilidade = simularIndisponibilidade;
    }

    @Override
    public void enviar(CursoConcluidoEvent evento) {
        tentativas.incrementAndGet();
        if (simularIndisponibilidade) {
            throw new IllegalStateException("Servico de notificacao indisponivel");
        }
        log.info("[NOTIFICACAO] Parabens {}! Voce tem {} cursos extras.",
                evento.nome(), evento.quantidadeCursosExtras());
    }

    @Override
    public int tentativas() {
        return tentativas.get();
    }
}

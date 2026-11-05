package org.example.ac1.notificacao;

import org.example.ac1.aluno.application.CursoConcluidoEvent;
import org.example.ac1.observabilidade.MetricasArquiteturais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/** DECISAO: so executa depois do COMMIT, em outra thread; falha fica isolada. */
@Component
@ConditionalOnProperty(name = "app.notificacao.modo", havingValue = "assincrono", matchIfMissing = true)
public class NotificacaoAssincronaListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoAssincronaListener.class);

    private final NotificacaoSender sender;
    private final MetricasArquiteturais metricas;

    public NotificacaoAssincronaListener(NotificacaoSender sender, MetricasArquiteturais metricas) {
        this.sender = sender;
        this.metricas = metricas;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoConcluirCurso(CursoConcluidoEvent evento) {
        try {
            sender.enviar(evento);
            metricas.notificacaoEntregue();
        } catch (RuntimeException e) {
            metricas.notificacaoFalhou();
            log.warn("[NOTIFICACAO] falhou para alunoId={} (conclusao NAO afetada): {}",
                    evento.alunoId(), e.getMessage());
        }
    }
}

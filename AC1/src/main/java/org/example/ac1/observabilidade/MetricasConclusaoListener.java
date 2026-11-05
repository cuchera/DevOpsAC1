package org.example.ac1.observabilidade;

import org.example.ac1.aluno.application.CursoConcluidoEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/** Conta somente conclusoes que de fato foram gravadas (rollback nao conta). */
@Component
public class MetricasConclusaoListener {

    private final MetricasArquiteturais metricas;

    public MetricasConclusaoListener(MetricasArquiteturais metricas) {
        this.metricas = metricas;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoConfirmarConclusao(CursoConcluidoEvent evento) {
        metricas.conclusaoConfirmada();
    }
}

package org.example.ac1.observabilidade;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * Desafio 6: metricas que comprovam decisoes arquiteturais (nao so saude tecnica).
 *
 * <ul>
 *   <li>{@code ac1_cursos_concluidos_total}: conclusoes confirmadas (pos-commit).</li>
 *   <li>{@code ac1_notificacao_entregas_total{resultado="sucesso|falha"}}: entregas da notificacao.</li>
 * </ul>
 * Se as falhas de notificacao sobem e as conclusoes continuam subindo, o ADR-004 esta valendo em execucao.
 */
@Component
public class MetricasArquiteturais {

    private final Counter conclusoes;
    private final Counter notificacoesEntregues;
    private final Counter notificacoesComFalha;

    public MetricasArquiteturais(ObjectProvider<MeterRegistry> registros) {
        MeterRegistry registry = registros.getIfAvailable(SimpleMeterRegistry::new);

        this.conclusoes = Counter.builder("ac1.cursos.concluidos")
                .description("Conclusoes de curso confirmadas no banco (apos commit)")
                .register(registry);
        this.notificacoesEntregues = Counter.builder("ac1.notificacao.entregas")
                .description("Tentativas de notificacao de conclusao por resultado")
                .tag("resultado", "sucesso")
                .register(registry);
        this.notificacoesComFalha = Counter.builder("ac1.notificacao.entregas")
                .description("Tentativas de notificacao de conclusao por resultado")
                .tag("resultado", "falha")
                .register(registry);
    }

    public void conclusaoConfirmada() {
        conclusoes.increment();
    }

    public void notificacaoEntregue() {
        notificacoesEntregues.increment();
    }

    public void notificacaoFalhou() {
        notificacoesComFalha.increment();
    }

    public double conclusoes() {
        return conclusoes.count();
    }

    public double notificacoesEntregues() {
        return notificacoesEntregues.count();
    }

    public double notificacoesComFalha() {
        return notificacoesComFalha.count();
    }
}

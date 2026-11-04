package org.example.ac1.notificacao;

import org.example.ac1.aluno.application.CursoConcluidoEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** BASELINE (acoplado): roda na mesma thread e transacao de quem publicou. */
@Component
@ConditionalOnProperty(name = "app.notificacao.modo", havingValue = "sincrono")
public class NotificacaoSincronaListener {

    private final NotificacaoSender sender;

    public NotificacaoSincronaListener(NotificacaoSender sender) {
        this.sender = sender;
    }

    @EventListener
    public void aoConcluirCurso(CursoConcluidoEvent evento) {
        sender.enviar(evento);
    }
}

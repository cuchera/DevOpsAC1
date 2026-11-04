package org.example.ac1.notificacao;

import org.example.ac1.aluno.application.CursoConcluidoEvent;

public interface NotificacaoSender {

    void enviar(CursoConcluidoEvent evento);

    int tentativas();
}

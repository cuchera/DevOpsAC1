# Evidências — Desafio 4

| # | Evidência | O que prova |
|---|---|---|
| 01 | ![](01-teste-assincrono-consumidor-indisponivel.png) | Teste: conclusão gravada e notificação falhando isolada em outra thread (`task-1`) |
| 02 | ![](02-testes-desafio4.png) | Os 5 testes do Desafio 4 executados |
| 03 | ![](03-jacoco-geral.png) | Cobertura JaCoCo 100% no projeto |
| 04 | ![](04-jacoco-notificacao.png) | Cobertura 100% no módulo `notificacao` |
| 05b | ![](05b-sincrono-log.png) | Log do modo síncrono: falha da notificação propagada para a requisição |
| 05 | ![](05-sincrono-falha-e-rollback.png) | **Problema:** modo síncrono — 500 e aluno não gravado (rollback) |
| 06 | ![](06-assincrono-conclusao-salva.png) | **Solução:** modo assíncrono — 201 e aluno gravado com 3 cursos extras |
| 07 | ![](07-assincrono-log-warn.png) | Requisição em `nio-8080-exec-1`, notificação falha em `task-1` sem afetar a conclusão |

Como reproduzir (dentro de `AC1`):

    .\mvnw.cmd -B package -DskipTests
    java -jar target\AC1-0.0.1-SNAPSHOT.jar --app.notificacao.modo=sincrono --app.notificacao.simular-indisponibilidade=true
    java -jar target\AC1-0.0.1-SNAPSHOT.jar --app.notificacao.modo=assincrono --app.notificacao.simular-indisponibilidade=true

Requisições em `requests.http`.
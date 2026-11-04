# Evidências — Desafio 4

Salvar aqui os prints:

- `01-testes-verdes.png` — `CursoConcluidoEventosTest` com os 4 testes passando.
- `02-sincrono-500.png` — perfil `modo=sincrono` + consumidor indisponível: POST retorna 500.
- `03-sincrono-lista-vazia.png` — GET após o 500: lista vazia (rollback).
- `04-assincrono-201.png` — perfil `modo=assincrono` + consumidor indisponível: POST retorna 201 com 3 cursos extras.
- `05-assincrono-log-warn.png` — console com `WARN [NOTIFICACAO] falhou ... (conclusao NAO afetada)`.

Argumentos de execução (Run > Edit Configurations > Program arguments):

- Síncrono: `--app.notificacao.modo=sincrono --app.notificacao.simular-indisponibilidade=true`
- Assíncrono: `--app.notificacao.modo=assincrono --app.notificacao.simular-indisponibilidade=true`

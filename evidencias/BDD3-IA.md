# Evidência — BDD3: Serviço Python e IA

## Serviço disponível

Com o Compose iniciado:

```text
GET http://localhost:8001/health
200 {"status":"ok"}
```

Após cadastrar um aluno, a recomendação é solicitada por:

```text
POST http://localhost:8081/api/alunos/{id}/ia/recomendacao
200 {"disponivel":true,"conteudo":"..."}
```

O assistente usa:

```text
POST http://localhost:8081/api/alunos/{id}/ia/assistente
{"pergunta":"Qual curso devo fazer agora?"}
200 {"disponivel":true,"conteudo":"..."}
```

## Falha controlada

1. Execute `docker compose up -d --build`.
2. Confirme a recomendação com o serviço Python disponível.
3. Execute `docker compose stop ai-service`.
4. Repita a chamada de recomendação.

Resultado esperado:

```text
503 {"disponivel":false,"conteudo":"IA indisponível no momento; o fluxo principal continua funcionando."}
```

Mesmo durante a falha, `GET /api/alunos` e os endpoints de conclusão/resgate continuam sendo atendidos pelo Core. Isso demonstra tolerância a falhas sem transformar a IA em dependência do fluxo essencial.

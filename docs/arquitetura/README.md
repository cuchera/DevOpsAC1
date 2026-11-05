# Arquitetura — Desafio 3: Serviço Python e IA

## Rastreabilidade

| Requisito | RNF/ASR | RPC | Decisão | Evidência |
|---|---|---|---|---|
| RF03 Recomendação inteligente | Deploy independente, tolerância a falhas, escalabilidade | Core continua Java; IA pode ficar indisponível | FastAPI separado, consumido por REST | [evidência do mini-lab](../../evidencias/Desafio3-IA.md) |
| RF04 Assistente educacional | Deploy independente, tolerância a falhas, escalabilidade | ML/RAG permanecem fora do Core | Mesma fronteira de IA inicialmente, com endpoints independentes | [evidência do mini-lab](../../evidencias/Desafio3-IA.md) |

## ASRs selecionados

- **Deploy independente:** o serviço Python tem imagem e ciclo de publicação próprios, sem recompilar o Core Java.
- **Tolerância a falhas:** recomendações e assistente são capacidades opcionais; a indisponibilidade retorna `503` explícito e não impede operações de alunos.
- **Escalabilidade:** a API FastAPI é stateless e pode ser replicada separadamente do Core conforme a carga de inferência.

## ADR-003 — Fronteira de IA

### Contexto

Recomendação e assistência educacional exigem modelos Python com atualização e carga diferentes do Core. A indisponibilidade dessas capacidades não pode bloquear o cadastro, conclusão ou resgate de cursos.

### Alternativas

- **A — IA embutida no Core:** menor latência de chamada, mas mistura runtimes, deploys e escala, além de aumentar o impacto de falhas de modelo.
- **B — módulo Python acoplado ao Core:** separa o runtime parcialmente, mas mantém o mesmo ciclo operacional e dificulta isolamento de recursos.
- **C — serviço Python independente:** permite deploy, escala e recuperação independentes; adiciona uma chamada REST e observabilidade de outro serviço.

### Decisão

Adotar a alternativa C. ML e RAG ficam na mesma fronteira `ai-service` neste estágio, com endpoints REST independentes (`/recommendations` e `/assistant`). A separação entre serviços só será feita quando houver escala, segurança, dados ou ciclo de atualização significativamente diferentes.

O Core chama FastAPI por REST. Quando a chamada falha, o endpoint de IA devolve `503` com `disponivel=false`; os fluxos essenciais do Core continuam isolados e operacionais.

### Consequências e trade-offs

- Positivas: deploy e escala independentes, isolamento de falhas e interoperabilidade por HTTP/JSON.
- Negativas: novo container para operar, latência de rede e necessidade de métricas, timeout e evolução de contrato.
- O mini-lab usa respostas determinísticas para provar a fronteira; modelos reais podem substituir a implementação sem alterar o contrato do Core.

## C4 — Container

```text
[Usuário]
    |
    v
[Core Java/Spring Boot] -- REST/JSON --> [AI Service/FastAPI]
    |                                      |-- ML: recomendação
    |                                      `-- RAG/LLM: assistente
    `-- PostgreSQL
```

O `docker-compose.yml` executa os containers separadamente. A URL é configurável por `AI_SERVICE_BASE_URL`, permitindo o mesmo Core em desenvolvimento, homologação e produção.

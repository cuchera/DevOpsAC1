# Evidências — Desafio 6

Salvar aqui os prints (nomes sugeridos):

| # | Arquivo | O que prova |
|---|---|---|
| 01 | `01-testes-e-quality-gate.png` | `mvnw clean test jacoco:check`: testes verdes e cobertura acima do mínimo (o gate que bloqueia o deploy) |
| 02 | `02-prometheus-targets.png` | Prometheus coletando **cada componente separadamente** (`ac1-core` e `ai-service` UP) |
| 03 | `03-grafana-normal.png` | Dashboard com tudo saudável: conclusões e notificações com sucesso sobem juntas |
| 04 | `04-grafana-falha-notificacao.png` | **Condição provocada 1 (ADR-004):** notificações em falha sobem e as conclusões **continuam subindo** |
| 05 | `05-grafana-ia-fora.png` | **Condição provocada 2 (ADR-003):** `ai-service` = FORA, rotas de IA em 503, Core UP |
| 06 | `06-jenkins-pipeline.png` | Pipeline com os stages novos (quality gate e observabilidade) verdes — se houver Jenkins disponível |

Como reproduzir: ver a seção "Evidência" do Desafio 6 em [`docs/arquitetura/README.md`](../../docs/arquitetura/README.md).
Tráfego: `gerar-trafego.ps1`.

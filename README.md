# DevOpsAC1


## Participação individual — Leonardo

### User Story escolhida

**Como** administrador da plataforma,
**quero** conceder automaticamente o direito a mais três cursos para alunos que concluírem um curso com média superior a 7,0,
**para** incentivar a retenção e o bom desempenho acadêmico na plataforma.

### BDD 2 — Leonardo

**Cenário:** aluno com média inferior a 7,0 não recebe cursos extras.

* **Dado** um aluno que concluiu um curso;
* **E** sua média final foi inferior a 7,0;
* **Quando** o sistema processa o encerramento do curso;
* **Então** ele não deve ter direito a escolher mais três cursos.

O cenário também está versionado em:

`AC1/src/test/resources/features/concessao-cursos-extras.feature`

### Implementação com TDD

A implementação foi desenvolvida seguindo o ciclo RED, GREEN e BLUE.

#### RED

O teste foi criado antes da implementação correta da regra. A primeira versão concedia três cursos independentemente da média, fazendo o teste falhar com o resultado esperado `false` e o resultado obtido `true`.

![Evidência RED — teste falhando](evidencias/Leonardo/BDD2-RED.png)

#### GREEN

A regra foi implementada para conceder três cursos somente quando o aluno concluiu o curso e obteve média superior a 7,0. Com a correção, o teste do BDD 2 passou.

![Evidência GREEN — teste passando](evidencias/Leonardo/BDD2-GREEN.png)

#### BLUE

O código foi refatorado com constantes para a média mínima e a quantidade de cursos extras, além da extração do critério de elegibilidade para um método próprio.

Também foram adicionados testes técnicos complementares para cobrir todos os caminhos da regra de negócio.

O relatório do JaCoCo apresentou:

* 100% das instruções;
* 100% dos branches;
* 100% das linhas;
* 100% dos métodos;
* 100% das classes.

![Evidência BLUE — cobertura de 100% no JaCoCo](evidencias/Leonardo/BDD2-BLUE-JACOCO-100.png)

### Arquivos implementados

* `AC1/src/main/java/domain/Aluno.java`
* `AC1/src/test/java/domainTest/AlunoTest.java`
* `AC1/src/test/resources/features/concessao-cursos-extras.feature`
* `evidencias/Leonardo/BDD2-RED.png`
* `evidencias/Leonardo/BDD2-GREEN.png`
* `evidencias/Leonardo/BDD2-BLUE-JACOCO-100.png`

### Regra implementada

* Aluno com curso concluído e média superior a 7,0 recebe três cursos extras.
* Aluno com média inferior a 7,0 não recebe cursos extras.
* Aluno que ainda não concluiu o curso não recebe cursos extras.

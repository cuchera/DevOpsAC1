# DevOpsAC1

<<<<<<< HEAD
RED
Antes de qualquer implementação (ou implementação temporariamente quebrada, ver commit), o teste falhava conforme esperado, comprovando que ele realmente testa a regra de negócio e não um "green falso".
![RED - teste falhando](image.png)


GREEN
Com a implementação de `Aluno.encerrarCurso()` feita, todos os testes passam.
![GREEN - testes passando](image-1.png)

Após o GREEN, a classe `Aluno` foi revisada (ajustes de formatação/indentação) e testes complementares foram adicionados para fechar os branches não cobertos, Com isso, a suíte de testes atinge 100% de cobertura, sem necessidade de nenhuma refatoração adicional na lógica de negócio — apenas nos testes, para exercitar todos os caminhos possíveis.

![alt text](image-2.png)
![alt text](image-3.png)

**Linha 13 (amarelo — losango 🔶):** o `if (mediaFinal >= 7.0)` tem cobertura parcial de branch. Até este momento só existe o teste do caminho em que a condição é **verdadeira** (média ≥ 7,0). O branch em que ela é **falsa** (média < 7,0) é coberto pelo teste `naoDeveConcederCursosExtrasQuandoMediaForMenorQueSete`, de responsabilidade de outro membro do grupo — parte do trabalho dividido entre a equipe e ainda em desenvolvimento no momento deste print.
=======

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
>>>>>>> Leonardo

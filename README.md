# DevOpsAC1

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

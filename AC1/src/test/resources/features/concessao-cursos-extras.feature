# Autor: Leonardo
# BDD 2

Funcionalidade: Concessão de cursos extras por desempenho
  Como administrador da plataforma
  Quero conceder automaticamente mais 3 cursos aos alunos elegíveis
  Para incentivar a retenção e o bom desempenho acadêmico

  Cenário: Aluno com média inferior a 7,0 não recebe cursos extras
    Dado um aluno que concluiu um curso
    E a sua média final foi inferior a 7,0
    Quando o sistema processa o encerramento do curso
    Então ele não deve ter direito a escolher mais 3 cursos
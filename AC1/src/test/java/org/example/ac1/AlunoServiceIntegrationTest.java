package org.example.ac1;

import org.example.ac1.dto.AlunoRequest;
import org.example.ac1.dto.AlunoResponse;
import org.example.ac1.repository.AlunoRepository;
import org.example.ac1.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:service-tests;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AlunoServiceIntegrationTest {

    @Autowired
    private AlunoService service;

    @Autowired
    private AlunoRepository repository;

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void deveCadastrarEListarAlunoComTresCreditos() {
        AlunoResponse aluno = service.cadastrar(
                new AlunoRequest("Ana", true, 8.0)
        );

        assertNotNull(aluno.id());
        assertEquals(3, aluno.quantidadeCursosExtras());

        var alunos = service.listar();

        assertEquals(1, alunos.size());
        assertEquals(aluno.id(), alunos.get(0).id());
        assertEquals("Ana", alunos.get(0).nome());
    }

    @Test
    void deveAcumularResgatarEPreservarSaldoComNotaBaixa() {
        AlunoResponse aluno = service.cadastrar(
                new AlunoRequest("Leo", true, 8.0)
        );

        assertEquals(
                2,
                service.resgatar(aluno.id()).quantidadeCursosExtras()
        );

        assertEquals(
                5,
                service.concluirOutroCurso(
                        aluno.id(),
                        new AlunoRequest("Leo", true, 9.0)
                ).quantidadeCursosExtras()
        );

        assertEquals(
                5,
                service.concluirOutroCurso(
                        aluno.id(),
                        new AlunoRequest("Leo", true, 6.0)
                ).quantidadeCursosExtras()
        );

        assertEquals(
                5,
                repository.findById(aluno.id())
                        .orElseThrow()
                        .getQuantidadeCursosExtras()
        );
    }

    @Test
    void deveInformarRecuperacaoSemConcederCreditos() {
        AlunoResponse aluno = service.cadastrar(
                new AlunoRequest("Gabriel", true, 7.0)
        );

        assertEquals(0, aluno.quantidadeCursosExtras());
        assertEquals(
                "Aluno tem direito a uma recuperação",
                aluno.mensagem()
        );
    }

    @Test
    void naoDeveConcederCreditosParaCursoPendente() {
        AlunoResponse aluno = service.cadastrar(
                new AlunoRequest("Joao", false, 9.0)
        );

        assertFalse(aluno.cursoConcluido());
        assertEquals(0, aluno.quantidadeCursosExtras());
    }

    @Test
    void deveBloquearQuartoResgateSemDeixarSaldoNegativo() {
        AlunoResponse aluno = service.cadastrar(
                new AlunoRequest("Ana", true, 8.0)
        );

        service.resgatar(aluno.id());
        service.resgatar(aluno.id());
        service.resgatar(aluno.id());

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> service.resgatar(aluno.id())
        );

        assertEquals(409, erro.getStatusCode().value());

        assertEquals(
                0,
                repository.findById(aluno.id())
                        .orElseThrow()
                        .getQuantidadeCursosExtras()
        );
    }

    @Test
    void deveRejeitarMediaForaDoIntervalo() {
        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> service.cadastrar(
                        new AlunoRequest("Ana", true, 11.0)
                )
        );

        assertEquals(400, erro.getStatusCode().value());
        assertEquals(0L, repository.count());
    }

    @Test
    void deveInformarAlunoInexistente() {
        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> service.resgatar(Long.MAX_VALUE)
        );

        assertEquals(404, erro.getStatusCode().value());
    }

    @Test
    void deveRejeitarRequisicaoNula() {
        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> service.cadastrar(null)
        );

        assertEquals(400, erro.getStatusCode().value());
        assertEquals(0L, repository.count());
    }

    @Test
    void deveRejeitarSituacaoDoCursoAusente() {
        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> service.cadastrar(
                        new AlunoRequest("Ana", null, 8.0)
                )
        );

        assertEquals(400, erro.getStatusCode().value());
        assertEquals(0L, repository.count());
    }

    @Test
    void deveRejeitarMediasInvalidas() {
        Double[] mediasInvalidas = {
                null,
                -1.0,
                11.0,
                Double.NaN,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY
        };

        for (Double media : mediasInvalidas) {
            ResponseStatusException erro = assertThrows(
                    ResponseStatusException.class,
                    () -> service.cadastrar(
                            new AlunoRequest("Ana", true, media)
                    )
            );

            assertEquals(400, erro.getStatusCode().value());
        }

        assertEquals(0L, repository.count());
    }

    @Test
    void deveRejeitarNomeAusenteOuEmBranco() {
        String[] nomesInvalidos = {null, "", "   "};

        for (String nome : nomesInvalidos) {
            ResponseStatusException erro = assertThrows(
                    ResponseStatusException.class,
                    () -> service.cadastrar(
                            new AlunoRequest(nome, true, 8.0)
                    )
            );

            assertEquals(400, erro.getStatusCode().value());
        }

        assertEquals(0L, repository.count());
    }

    @Test
    void deveAceitarMediasNosLimitesPermitidos() {
        AlunoResponse minimo = service.cadastrar(
                new AlunoRequest("Minimo", true, 0.0)
        );

        AlunoResponse maximo = service.cadastrar(
                new AlunoRequest("Maximo", true, 10.0)
        );

        assertEquals(0, minimo.quantidadeCursosExtras());
        assertEquals(3, maximo.quantidadeCursosExtras());
        assertEquals(2L, repository.count());
    }
}
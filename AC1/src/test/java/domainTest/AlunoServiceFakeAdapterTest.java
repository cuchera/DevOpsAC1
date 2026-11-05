package domainTest;

import org.example.ac1.aluno.api.AlunoRequest;
import org.example.ac1.aluno.api.AlunoResponse;
import org.example.ac1.aluno.application.AlunoService;
import org.example.ac1.aluno.fake.AlunoFakeAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoServiceFakeAdapterTest {

    private AlunoService service;

    @BeforeEach
    void configurar() {
        AlunoFakeAdapter fake = new AlunoFakeAdapter();
        service = new AlunoService(fake, evento -> {});
    }

    @Test
    void deveCadastrarAlunoUtilizandoAdapterFake() {
        AlunoRequest request =
                new AlunoRequest("Gabriel", true, 8.5);

        AlunoResponse response = service.cadastrar(request);

        assertNotNull(response.id());
        assertEquals("Gabriel", response.nome());
        assertEquals(3, response.quantidadeCursosExtras());
    }

    @Test
    void deveListarAlunosUtilizandoAdapterFake() {
        AlunoRequest request =
                new AlunoRequest("Leonardo", true, 9.0);

        service.cadastrar(request);

        var alunos = service.listar();

        assertEquals(1, alunos.size());
        assertEquals("Leonardo", alunos.get(0).nome());
    }

    @Test
    void deveResgatarCursoExtraUtilizandoAdapterFake() {
        AlunoRequest request =
                new AlunoRequest("Ariane", true, 8.0);

        AlunoResponse cadastrado = service.cadastrar(request);

        AlunoResponse resgatado =
                service.resgatar(cadastrado.id());

        assertEquals(2, resgatado.quantidadeCursosExtras());
    }
}
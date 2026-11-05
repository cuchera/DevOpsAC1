package domainTest;

import org.example.ac1.aluno.fake.AlunoFakeAdapter;
import org.example.ac1.aluno.application.port.out.AlunoPort.AlunoDados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoFakeAdapterTest {

    @Test
    void deveSalvarEBuscarAlunoSemBancoDeDados() {
        AlunoFakeAdapter fake = new AlunoFakeAdapter();

        AlunoDados aluno = new AlunoDados(
                null, "Gabriel", true, 8.0, 3
        );

        AlunoDados salvo = fake.salvar(aluno);

        assertNotNull(salvo.id());
        assertEquals("Gabriel",
                fake.buscarPorId(salvo.id()).orElseThrow().nome());
    }

    @Test
    void deveListarAlunosSalvos() {
        AlunoFakeAdapter fake = new AlunoFakeAdapter();

        fake.salvar(new AlunoDados(
                null, "Gabriel", true, 8.0, 3
        ));

        assertEquals(1, fake.listar().size());
    }

    @Test
    void deveAtualizarAlunoExistente() {
        AlunoFakeAdapter fake = new AlunoFakeAdapter();

        AlunoDados salvo = fake.salvar(
                new AlunoDados(null, "Gabriel", true, 8.0, 3)
        );

        AlunoDados atualizado = fake.salvar(
                new AlunoDados(salvo.id(), "Gabriel", true, 9.0, 5)
        );

        assertEquals(1, fake.listar().size());
        assertEquals(9.0, fake.buscarPorId(salvo.id())
                .orElseThrow().mediaFinal());
        assertEquals(5, atualizado.quantidadeCursosExtras());
    }
}
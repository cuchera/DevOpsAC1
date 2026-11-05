package domainTest;

import org.example.ac1.aluno.application.port.out.AlunoPort.AlunoDados;
import org.example.ac1.aluno.internal.persistence.AlunoEntity;
import org.example.ac1.aluno.internal.persistence.AlunoJpaAdapter;
import org.example.ac1.aluno.internal.persistence.AlunoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoJpaAdapterTest {

    @Test
    void deveSalvarNovoAluno() {
        AlunoRepository repository = mock(AlunoRepository.class);

        when(repository.save(any(AlunoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AlunoJpaAdapter adapter = new AlunoJpaAdapter(repository);

        AlunoDados resultado = adapter.salvar(
                new AlunoDados(null, "Gabriel", true, 8.0, 3)
        );

        assertEquals("Gabriel", resultado.nome());
        verify(repository).save(any(AlunoEntity.class));
    }

    @Test
    void deveAtualizarAlunoExistente() {
        AlunoRepository repository = mock(AlunoRepository.class);
        AlunoEntity entidade = new AlunoEntity("Gabriel");

        when(repository.findById(1L)).thenReturn(Optional.of(entidade));
        when(repository.save(any(AlunoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AlunoJpaAdapter adapter = new AlunoJpaAdapter(repository);

        AlunoDados resultado = adapter.salvar(
                new AlunoDados(1L, "Gabriel", true, 9.0, 5)
        );

        assertEquals(9.0, resultado.mediaFinal());
        assertEquals(5, resultado.quantidadeCursosExtras());
    }

    @Test
    void deveFalharAoAtualizarAlunoInexistente() {
        AlunoRepository repository = mock(AlunoRepository.class);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        AlunoJpaAdapter adapter = new AlunoJpaAdapter(repository);

        assertThrows(
                IllegalArgumentException.class,
                () -> adapter.salvar(
                        new AlunoDados(99L, "Gabriel", true, 8.0, 3)
                )
        );
    }
}
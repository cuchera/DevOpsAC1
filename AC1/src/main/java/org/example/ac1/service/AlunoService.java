package org.example.ac1.service;

import domain.Aluno;
import entity.AlunoEntity;
import org.example.ac1.dto.AlunoRequestDTO;
import org.example.ac1.dto.AlunoResponseDTO;
import org.example.ac1.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponseDTO processarEncerramento(AlunoRequestDTO dto) {

        Aluno domainAluno = new Aluno(dto.nome(), dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        AlunoEntity entity = new AlunoEntity(dto.nome(), dto.cursoConcluido(), dto.mediaFinal());
        entity.setQuantidadeCursosExtras(domainAluno.getQuantidadeCursosExtras());
        entity.setMensagem(domainAluno.getMensagem()); // <-- Preenche a mensagem!

        AlunoEntity saved = alunoRepository.save(entity);
        return toResponseDTO(saved);
    }

    public AlunoResponseDTO acumularNovoCurso(Long id, AlunoRequestDTO dto) {
        AlunoEntity entity = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Aluno domainAluno = new Aluno(dto.nome(), dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        entity.setCursoConcluido(dto.cursoConcluido());
        entity.setMediaFinal(dto.mediaFinal());
        entity.acumularCursosExtras(domainAluno.getQuantidadeCursosExtras());
        entity.setMensagem(domainAluno.getMensagem()); // <-- Atualiza a mensagem!

        AlunoEntity updated = alunoRepository.save(entity);
        return toResponseDTO(updated);
    }

    public AlunoResponseDTO resgatarCursoExtra(Long id) {
        AlunoEntity entity = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (entity.getQuantidadeCursosExtras() <= 0) {
            throw new RuntimeException("Saldo insuficiente de cursos extras!");
        }

        entity.usarCursoExtra();
        entity.setMensagem("1 curso extra resgatado com sucesso!");

        AlunoEntity updated = alunoRepository.save(entity);
        return toResponseDTO(updated);
    }

    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public void deletar(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado para exclusão com ID: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private AlunoResponseDTO toResponseDTO(AlunoEntity entity) {
        return new AlunoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.isCursoConcluido(),
                entity.getMediaFinal(),
                entity.getQuantidadeCursosExtras(),
                entity.getQuantidadeCursosExtras() > 0,
                entity.getMensagem()
        );
    }
}
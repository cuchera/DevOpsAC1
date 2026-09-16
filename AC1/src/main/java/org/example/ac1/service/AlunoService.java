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
        Aluno domainAluno = new Aluno(dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        AlunoEntity entity = new AlunoEntity(dto.nome(), dto.cursoConcluido(), dto.mediaFinal());
        entity.setQuantidadeCursosExtras(domainAluno.getQuantidadeCursosExtras());

        AlunoEntity saved = alunoRepository.save(entity);
        return toResponseDTO(saved);
    }

    public AlunoResponseDTO acumularNovoCurso(Long id, AlunoRequestDTO dto) {
        AlunoEntity entity = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

        Aluno domainAluno = new Aluno(dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        // Atualiza nota/status do último curso e soma os novos cursos ao saldo existente
        entity.setCursoConcluido(dto.cursoConcluido());
        entity.setMediaFinal(dto.mediaFinal());
        entity.acumularCursosExtras(domainAluno.getQuantidadeCursosExtras());

        AlunoEntity updated = alunoRepository.save(entity);
        return toResponseDTO(updated);
    }

    public AlunoResponseDTO resgatarCursoExtra(Long id) {
        AlunoEntity entity = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

        if (entity.getQuantidadeCursosExtras() <= 0) {
            throw new RuntimeException("Aluno não possui saldo de cursos extras para resgatar!");
        }

        entity.usarCursoExtra(); // Subtrai 1 do saldo acumulado
        AlunoEntity updated = alunoRepository.save(entity);
        return toResponseDTO(updated);
    }

    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream().map(this::toResponseDTO).toList();
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
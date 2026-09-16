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
        return toResponseDTO(saved, domainAluno.temDireitoACursosExtras());
    }

    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream().map(entity -> {
            Aluno domainAluno = new Aluno(entity.isCursoConcluido(), entity.getMediaFinal());
            domainAluno.processarEncerramentoDoCurso();
            return toResponseDTO(entity, domainAluno.temDireitoACursosExtras());
        }).toList();
    }

    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        AlunoEntity entity = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + id));

        Aluno domainAluno = new Aluno(dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        entity.setNome(dto.nome());
        entity.setCursoConcluido(dto.cursoConcluido());
        entity.setMediaFinal(dto.mediaFinal());
        entity.setQuantidadeCursosExtras(domainAluno.getQuantidadeCursosExtras());

        AlunoEntity updated = alunoRepository.save(entity);
        return toResponseDTO(updated, domainAluno.temDireitoACursosExtras());
    }

    public void deletar(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado com o ID: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private AlunoResponseDTO toResponseDTO(AlunoEntity entity, boolean temDireito) {
        return new AlunoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.isCursoConcluido(),
                entity.getMediaFinal(),
                entity.getQuantidadeCursosExtras(),
                temDireito
        );
    }
}
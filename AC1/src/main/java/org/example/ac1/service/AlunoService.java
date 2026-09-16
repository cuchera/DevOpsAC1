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
        // Executa a regra do Domínio (TDD)
        Aluno domainAluno = new Aluno(dto.cursoConcluido(), dto.mediaFinal());
        domainAluno.processarEncerramentoDoCurso();

        // Mapeia para a Entidade JPA
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
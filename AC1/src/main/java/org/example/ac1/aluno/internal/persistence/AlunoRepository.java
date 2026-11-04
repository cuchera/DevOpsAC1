package org.example.ac1.aluno.internal.persistence;

import org.example.ac1.aluno.internal.persistence.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository
        extends JpaRepository<AlunoEntity, Long> {
}
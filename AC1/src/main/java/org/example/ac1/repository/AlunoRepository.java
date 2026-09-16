package org.example.ac1.repository;

import org.example.ac1.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository
        extends JpaRepository<AlunoEntity, Long> {
}
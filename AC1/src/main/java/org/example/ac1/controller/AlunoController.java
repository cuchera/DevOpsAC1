package org.example.ac1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ac1.dto.AlunoRequestDTO;
import org.example.ac1.dto.AlunoResponseDTO;
import org.example.ac1.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
@Tag(name = "Alunos", description = "Endpoints da gamificação de alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping("/processar-encerramento")
    @Operation(summary = "Processa o encerramento do curso e cadastra o aluno")
    public ResponseEntity<AlunoResponseDTO> processarEncerramento(@RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.processarEncerramento(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todos os alunos")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados e reavalia os critérios do aluno por ID")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(alunoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno do sistema por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
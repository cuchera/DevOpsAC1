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
    @Operation(summary = "1. Cadastra e processa o encerramento do primeiro curso")
    public ResponseEntity<AlunoResponseDTO> processarEncerramento(@RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.processarEncerramento(dto));
    }

    @PostMapping("/{id}/concluir-outro-curso")
    @Operation(summary = "2. Registra a conclusão de +1 curso para o aluno e acumula +3 extras no saldo")
    public ResponseEntity<AlunoResponseDTO> acumularNovoCurso(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(alunoService.acumularNovoCurso(id, dto));
    }

    @PostMapping("/{id}/resgatar-curso-extra")
    @Operation(summary = "3. Usa/resgata 1 curso extra do saldo acumulado do aluno")
    public ResponseEntity<AlunoResponseDTO> resgatarCursoExtra(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.resgatarCursoExtra(id));
    }

    @GetMapping
    @Operation(summary = "4. Lista todos os alunos e seus saldos acumulados")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "5. Exclui um aluno do sistema pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
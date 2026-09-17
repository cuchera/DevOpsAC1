package org.example.ac1.controller;

import org.example.ac1.dto.AlunoRequest;
import org.example.ac1.dto.AlunoResponse;
import org.example.ac1.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlunoResponse> listar() {
        return service.listar();
    }

    @PostMapping("/processar-encerramento")
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse cadastrar(
            @RequestBody AlunoRequest request) {

        return service.cadastrar(request);
    }

    @PostMapping("/{id}/concluir-outro-curso")
    public AlunoResponse concluirOutroCurso(
            @PathVariable("id") Long id,
            @RequestBody AlunoRequest request) {

        return service.concluirOutroCurso(id, request);
    }

    @PostMapping("/{id}/resgatar-curso-extra")
    public AlunoResponse resgatar(
            @PathVariable("id") Long id) {

        return service.resgatar(id);
    }
}
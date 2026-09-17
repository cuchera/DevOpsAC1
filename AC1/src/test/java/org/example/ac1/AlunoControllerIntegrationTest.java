package org.example.ac1;

import org.example.ac1.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:controller-tests;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AlunoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AlunoRepository repository;

    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        repository.deleteAll();
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void deveCadastrarListarAcumularEResgatarPelaApi() throws Exception {
        mvc.perform(post("/api/alunos/processar-encerramento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana",
                                  "cursoConcluido": true,
                                  "mediaFinal": 8.0
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Ana"))
                .andExpect(jsonPath("$.quantidadeCursosExtras").value(3));

        Long id = repository.findAll().get(0).getId();

        mvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ana"))
                .andExpect(jsonPath("$[0].quantidadeCursosExtras").value(3));

        mvc.perform(post("/api/alunos/{id}/concluir-outro-curso", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana",
                                  "cursoConcluido": true,
                                  "mediaFinal": 9.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mediaFinal").value(9.0))
                .andExpect(jsonPath("$.quantidadeCursosExtras").value(6));

        mvc.perform(post("/api/alunos/{id}/resgatar-curso-extra", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidadeCursosExtras").value(5));

        mvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantidadeCursosExtras").value(5));
    }

    @Test
    void deveExibirRecuperacaoEBloquearResgateSemSaldo() throws Exception {
        mvc.perform(post("/api/alunos/processar-encerramento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Gabriel",
                                  "cursoConcluido": true,
                                  "mediaFinal": 7.0
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantidadeCursosExtras").value(0))
                .andExpect(jsonPath("$.mensagem")
                        .value("Aluno tem direito a uma recuperação"));

        Long id = repository.findAll().get(0).getId();

        mvc.perform(post("/api/alunos/{id}/resgatar-curso-extra", id))
                .andExpect(status().isConflict());

        mvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantidadeCursosExtras").value(0));
    }

    @Test
    void deveRetornar400ParaMediaInvalida() throws Exception {
        mvc.perform(post("/api/alunos/processar-encerramento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Ana",
                                  "cursoConcluido": true,
                                  "mediaFinal": 11.0
                                }
                                """))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void deveRetornar404ParaAlunoInexistente() throws Exception {
        mvc.perform(post(
                        "/api/alunos/{id}/resgatar-curso-extra",
                        Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}
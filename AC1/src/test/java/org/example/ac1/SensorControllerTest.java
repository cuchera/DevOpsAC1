package org.example.ac1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:sensor-tests;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "ai.service.base-url=http://localhost:1"
})
class SensorControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private void enviar(String sensor, double temperatura) throws Exception {
        mvc.perform(post("/api/sensores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sensor\":\"" + sensor + "\",\"temperatura\":" + temperatura
                                + ",\"unidade\":\"C\",\"status\":\"NORMAL\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void listaEventosDoMaisRecenteParaOMaisAntigoComHorario() throws Exception {
        enviar("sensor-a", 21.0);
        enviar("sensor-b", 31.5);

        mvc.perform(get("/api/sensores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sensor").value("sensor-b"))
                .andExpect(jsonPath("$[0].temperatura").value(31.5))
                .andExpect(jsonPath("$[0].recebidoEm").isString())
                .andExpect(jsonPath("$[1].sensor").value("sensor-a"));
    }

    @Test
    void mantemSomenteOsUltimosVinteEventos() throws Exception {
        for (int i = 0; i < 25; i++) {
            enviar("lote-" + i, 20.0 + i);
        }

        mvc.perform(get("/api/sensores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(20))
                .andExpect(jsonPath("$[0].sensor").value("lote-24"));
    }

    @Test
    void statusInformaCoreUpEIaIndisponivel() throws Exception {
        mvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.core").value("UP"))
                .andExpect(jsonPath("$.iaDisponivel").value(false));
    }
}

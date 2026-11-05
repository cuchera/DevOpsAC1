package org.example.ac1;

import com.sun.net.httpserver.HttpServer;
import org.example.ac1.ia.internal.FastApiClient;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FastApiClientDisponivelTest {

    private static HttpServer servidorComHealth(int status) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/health", troca -> {
            troca.sendResponseHeaders(status, -1);
            troca.close();
        });
        server.start();
        return server;
    }

    @Test
    void disponivelQuandoHealthResponde200() throws Exception {
        HttpServer server = servidorComHealth(200);
        try {
            FastApiClient client = new FastApiClient(
                    "http://localhost:" + server.getAddress().getPort());
            assertTrue(client.disponivel());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void indisponivelQuandoHealthResponde500() throws Exception {
        HttpServer server = servidorComHealth(500);
        try {
            FastApiClient client = new FastApiClient(
                    "http://localhost:" + server.getAddress().getPort());
            assertFalse(client.disponivel());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void indisponivelQuandoNaoHaServidor() {
        assertFalse(new FastApiClient("http://localhost:1").disponivel());
    }

    @Test
    void indisponivelQuandoUrlEhInvalida() {
        assertFalse(new FastApiClient("url invalida").disponivel());
    }
}

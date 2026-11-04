package domainTest;

import org.example.ac1.recompensa.domain.CarteiraMoedas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteiraMoedasTest {

    @Test
    void deveAdicionarMoedas() {
        CarteiraMoedas carteira = new CarteiraMoedas();

        carteira.adicionarMoedas(3);

        assertEquals(3, carteira.getSaldo());
    }

    @Test
    void deveGastarMoedas() {
        CarteiraMoedas carteira = new CarteiraMoedas();
        carteira.adicionarMoedas(5);

        carteira.gastarMoedas(2);

        assertEquals(3, carteira.getSaldo());
    }

    @Test
    void naoDeveGastarMaisMoedasQueOSaldo() {
        CarteiraMoedas carteira = new CarteiraMoedas();
        carteira.adicionarMoedas(2);

        assertThrows(
                IllegalArgumentException.class,
                () -> carteira.gastarMoedas(3)
        );

        assertEquals(2, carteira.getSaldo());
    }
}
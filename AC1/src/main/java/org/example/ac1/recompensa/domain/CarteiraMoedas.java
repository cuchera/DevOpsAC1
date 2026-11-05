package org.example.ac1.recompensa.domain;

public class CarteiraMoedas {

    private int saldo;

    public void adicionarMoedas(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser positiva."
            );
        }

        saldo += quantidade;
    }

    public void gastarMoedas(int quantidade) {
        if (quantidade <= 0 || quantidade > saldo) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente ou quantidade inválida."
            );
        }

        saldo -= quantidade;
    }

    public int getSaldo() {
        return saldo;
    }
}
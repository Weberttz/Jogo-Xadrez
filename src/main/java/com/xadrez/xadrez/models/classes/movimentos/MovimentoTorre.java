package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

import java.util.ArrayList;
import java.util.List;

public class MovimentoTorre implements EstrategiaMovimento {

    private static final int[][] DIRECOES = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor) {
        return origem.getX() == destino.getX() || origem.getY() == destino.getY();
    }

    @Override
    public List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca) {
        List<Casa> movimentos = new ArrayList<>();
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        for (int[] dir : DIRECOES) {
            int x = origem.getX();
            int y = origem.getY();

            while (true) {
                x += dir[0];
                y += dir[1];

                if (!tabuleiro.dentroDoLimite(x, y)) break;

                Casa casaAux = tabuleiro.getCasa(x, y);

                if (casaAux.estaVazia()) {
                    movimentos.add(casaAux);
                } else {
                    if (casaAux.getPeca().getCor() != peca.getCor()) {
                        movimentos.add(casaAux);
                    }
                    break;
                }
            }
        }
        return movimentos;
    }
}

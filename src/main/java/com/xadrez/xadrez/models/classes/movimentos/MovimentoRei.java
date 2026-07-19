package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

import java.util.ArrayList;
import java.util.List;

public class MovimentoRei implements EstrategiaMovimento {
    private static final int[][] DIRECOES = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},   // retas
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}  // diagonais
    };

    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor) {
        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());
        int comprimentoVetor = distanciaX + distanciaY;

        return comprimentoVetor == 1 || comprimentoVetor == 2;
    }

    @Override
    public List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca) {
        List<Casa> movimentos = new ArrayList<>();
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        for (int[] dir : DIRECOES) {
            int x = origem.getX() + dir[0];
            int y = origem.getY() + dir[1];

            if (!tabuleiro.dentroDoLimite(x, y)) continue;

            Casa casaAux = tabuleiro.getCasa(x, y);

            if (casaAux.estaVazia() || casaAux.getPeca().getCor() != peca.getCor()) {
                movimentos.add(casaAux);
            }
        }

        return movimentos;
    }
}

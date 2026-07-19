package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

import java.util.ArrayList;
import java.util.List;

public class MovimentoCavalo implements EstrategiaMovimento {
    private static final int[][] SALTOS = {
            {1, 2}, {2, 1}, {2, -1}, {1, -2},
            {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}
    };

    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor){

        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());

        return (distanciaX + distanciaY == 3) && distanciaX != 0 && distanciaY != 0;
    }

    @Override
    public List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca) {
        List<Casa> movimentos = new ArrayList<>();
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        for (int[] salto : SALTOS) {
            int x = origem.getX() + salto[0];
            int y = origem.getY() + salto[1];

            if (!tabuleiro.dentroDoLimite(x, y)) continue;

            Casa casaAux = tabuleiro.getCasa(x, y);
            if (casaAux.estaVazia() || casaAux.getPeca().getCor() != peca.getCor()) {
                movimentos.add(casaAux);
            }
        }
        return movimentos;
    }
}

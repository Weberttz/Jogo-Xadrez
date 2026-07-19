package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

import java.util.ArrayList;
import java.util.List;

public class MovimentoPeao implements EstrategiaMovimento {
    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor) {
        int distanciaX = origem.getX() - destino.getX();
        int distanciaY = origem.getY() - destino.getY();

        if(cor.equals(Cor.PRETA)) {
            distanciaX *= -1;
            distanciaY *= -1;
        }

        return distanciaX == 1 || distanciaX == 2 || (distanciaX + distanciaY) == 2;
    }

    @Override
    public List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca) {
        List<Casa> movimentos = new ArrayList<>();
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        int direcao = (peca.getCor() == Cor.BRANCA) ? -1 : 1; // depende de como você orienta o tabuleiro

        // movimento simples pra frente
        int x1 = origem.getX() + direcao;
        int y1 = origem.getY();
        if (tabuleiro.dentroDoLimite(x1, y1) && tabuleiro.getCasa(x1, y1).estaVazia()) {
            movimentos.add(tabuleiro.getCasa(x1, y1));

            // movimento duplo se ainda não moveu
            int x2 = origem.getX() + 2 * direcao;
            if (peca.getQuantidadeMovimento() == 0
                    && tabuleiro.dentroDoLimite(x2, y1) && tabuleiro.getCasa(x2, y1).estaVazia()) {
                movimentos.add(tabuleiro.getCasa(x2, y1));
            }
        }

        // capturas diagonais
        for (int dy : new int[]{-1, 1}) {
            int xc = origem.getX() + direcao;
            int yc = origem.getY() + dy;
            if (tabuleiro.dentroDoLimite(xc, yc)) {
                Casa casaAux = tabuleiro.getCasa(xc, yc);
                if (!casaAux.estaVazia() && casaAux.getPeca().getCor() != peca.getCor()) {
                    movimentos.add(casaAux);
                }
                // aqui também entraria a lógica de en passant, se você já tiver
            }
        }

        return movimentos;
}
}

package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.classes.Tabuleiro;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoPeao implements EstrategiaMovimento {
    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Tabuleiro tabuleiro) {
        return origem.getX() > destino.getX();
    }
}

package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoRei implements EstrategiaMovimento {
    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor) {
        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());
        int comprimentoVetor = distanciaX + distanciaY;

        return comprimentoVetor == 1 || comprimentoVetor == 2;
    }
}

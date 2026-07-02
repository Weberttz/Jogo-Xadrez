package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

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
}

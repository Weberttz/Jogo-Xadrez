package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoPeao implements EstrategiaMovimento {
    @Override
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor) {
        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());

        //boolean naoMoveuAinda = (origem.getX() == 1 || origem.getX() == 6);

        return distanciaX == 1 || distanciaX == 2 || (distanciaX + distanciaY) == 2;
    }
}

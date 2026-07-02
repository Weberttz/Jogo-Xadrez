package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoCavalo implements EstrategiaMovimento {
    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor){

        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());

        return (distanciaX + distanciaY == 3) && distanciaX != 0 && distanciaY != 0;
    }
}

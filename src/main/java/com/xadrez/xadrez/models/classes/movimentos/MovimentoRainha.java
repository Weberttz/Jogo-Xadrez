package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoRainha implements EstrategiaMovimento {

    public boolean isMovimentoValido(Posicao origem, Posicao destino){
        EstrategiaMovimento estrategiaMovimentoBispo = new MovimentoBispo();
        EstrategiaMovimento estrategiaMovimentoTorre = new MovimentoTorre();

        return estrategiaMovimentoBispo.isMovimentoValido(origem, destino) ||
                estrategiaMovimentoTorre.isMovimentoValido(origem, destino);
    }
}

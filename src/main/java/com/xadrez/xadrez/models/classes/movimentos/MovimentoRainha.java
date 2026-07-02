package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoRainha implements EstrategiaMovimento {

    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor){
        EstrategiaMovimento estrategiaMovimentoBispo = new MovimentoBispo();
        EstrategiaMovimento estrategiaMovimentoTorre = new MovimentoTorre();

        return estrategiaMovimentoBispo.isMovimentoValido(origem, destino, cor) ||
                estrategiaMovimentoTorre.isMovimentoValido(origem, destino, cor);
    }
}

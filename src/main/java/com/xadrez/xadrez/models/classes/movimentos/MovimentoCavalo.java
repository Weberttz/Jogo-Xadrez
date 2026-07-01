package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class MovimentoCavalo implements EstrategiaMovimento {
    public boolean isMovimentoValido(Posicao origem, Posicao destino){
        return !(new MovimentoRainha().isMovimentoValido(origem, destino));
    }
}

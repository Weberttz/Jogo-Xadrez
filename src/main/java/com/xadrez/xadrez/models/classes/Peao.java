package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.classes.movimentos.MovimentoPeao;
import com.xadrez.xadrez.models.enums.Cor;

public class Peao extends Peca {

    public Peao(Cor cor){super("Peao", cor, new MovimentoPeao());}

    @Override
    public boolean mover(Posicao origem, Posicao destino){
        return this.getEstrategiaMovimento().isMovimentoValido(origem, destino);
    }
}

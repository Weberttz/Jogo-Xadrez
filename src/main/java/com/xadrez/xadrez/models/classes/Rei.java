package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.classes.movimentos.MovimentoRei;
import com.xadrez.xadrez.models.enums.Cor;

public class Rei extends Peca{

    public Rei(Cor cor){
        super("Rei", cor, new MovimentoRei());
    }

}

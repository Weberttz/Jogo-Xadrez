package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.classes.movimentos.MovimentoBispo;
import com.xadrez.xadrez.models.enums.Cor;

public class Bispo extends Peca{

    public Bispo(Cor cor){super("Bispo", cor, new MovimentoBispo());}

}

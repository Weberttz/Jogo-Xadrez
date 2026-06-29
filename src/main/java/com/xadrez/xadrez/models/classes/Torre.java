package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.classes.movimentos.MovimentoTorre;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class Torre extends Peca{

    public Torre(Cor cor){super("Torre", cor, new MovimentoTorre());}
}

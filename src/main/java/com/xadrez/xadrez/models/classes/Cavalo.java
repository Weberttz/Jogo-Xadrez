package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.classes.movimentos.MovimentoCavalo;
import com.xadrez.xadrez.models.enums.Cor;

public class Cavalo extends Peca{

    public Cavalo(Cor cor){ super("Cavalo", cor, new MovimentoCavalo());}
}

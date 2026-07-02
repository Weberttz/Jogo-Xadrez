package com.xadrez.xadrez.models.enums;

import com.xadrez.xadrez.models.classes.movimentos.*;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public enum Tipo {
    REI("rei",new MovimentoRei()),
    RAINHA("rainha", new MovimentoRainha()),
    CAVALO("cavalo", new MovimentoCavalo()),
    BISPO("bispo", new MovimentoBispo()),
    TORRE("torre", new MovimentoTorre()),
    PEAO("peao", new MovimentoPeao());

    private String nome;
    private final EstrategiaMovimento estrategiaMovimento;

    Tipo(String nome, EstrategiaMovimento estrategiaMovimento){
        this.nome = nome;
        this.estrategiaMovimento = estrategiaMovimento;
    }
    public EstrategiaMovimento getEstrategiaMovimento() {return estrategiaMovimento;}
    public String getNome(){return this.nome;}
}

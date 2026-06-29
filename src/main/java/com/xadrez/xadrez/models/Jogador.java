package com.xadrez.xadrez.models;

import com.xadrez.xadrez.models.enums.Cores;

public class Jogador {
    private String nome;
    private Cores cor;

    public Jogador(String nome, Cores cor){
        this.nome = nome;
        this.cor = cor;
    }

}

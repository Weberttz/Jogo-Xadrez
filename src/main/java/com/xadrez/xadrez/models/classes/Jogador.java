package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;

public class Jogador {
    private String nome;
    private Cor cor;
    private int quantidadePecasEmJogo;

    public Jogador(String nome, Cor cor){
        this.nome = nome;
        this.cor = cor;
        this.quantidadePecasEmJogo = 16;
    }

    public String getNome() {return this.nome;}
    public Cor getCor() {return this.cor;}
    public int getQuantidadePecasEmJogo() {return this.quantidadePecasEmJogo;}
}

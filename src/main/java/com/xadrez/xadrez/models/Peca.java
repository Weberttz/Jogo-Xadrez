package com.xadrez.xadrez.models;

import com.xadrez.xadrez.models.enums.Cores;

public abstract class Peca {

    private String nome;
    private Cores cor;

    public Peca(String nome, Cores cor){this.nome = nome;this.cor = cor;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public Cores getCor() {return cor;}
    public void setCor(Cores cor) {this.cor = cor;}
}

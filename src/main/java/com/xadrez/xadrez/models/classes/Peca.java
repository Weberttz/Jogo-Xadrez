package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public abstract class Peca {

    private String nome;
    private final Cor cor;
    private Posicao posicao;
    private EstrategiaMovimento estrategiaMovimento;

    public Peca(String nome, Cor cor, EstrategiaMovimento estrategiaMovimento) {
        this.nome = nome;
        this.cor = cor;
        this.estrategiaMovimento = estrategiaMovimento;
    }

    public String getNome() {return nome;}
    public Posicao getPosicao() {return posicao;}
    public Cor getCor(){return cor;}
    public void setPosicao(Posicao posicao) {this.posicao = posicao;}
}

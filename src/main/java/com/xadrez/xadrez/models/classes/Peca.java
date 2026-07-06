package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.Tipo;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class Peca {

    private final String nome;
    private final Cor cor;
    private Posicao posicao;
    private Tipo tipo;
    private int quantidadeMovimentos;

    public Peca(Tipo tipo, Cor cor) {
        this.cor = cor;
        this.tipo = tipo;
        this.nome = tipo.getNome();
        this.quantidadeMovimentos = 0;
    }

    public boolean mover(Posicao origem, Posicao destino){
        return this.getEstrategiaMovimento().isMovimentoValido(origem, destino, this.cor);
    }

    public String getNome() {return this.nome;}
    public Posicao getPosicao() {return this.posicao;}
    public Cor getCor(){return this.cor;}
    public Tipo getTipo(){return this.tipo;}
    public int getQuantidadeMovimento(){return this.quantidadeMovimentos;}
    public EstrategiaMovimento getEstrategiaMovimento(){return this.tipo.getEstrategiaMovimento();}
    public void setPosicao(Posicao posicao) {this.posicao = posicao;}
    public void setQuantidadeMovimento(int quantidadeMovimento){this.quantidadeMovimentos = quantidadeMovimento;}
}

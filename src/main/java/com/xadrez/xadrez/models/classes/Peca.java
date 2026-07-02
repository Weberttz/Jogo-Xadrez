package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.Tipo;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class Peca {

    private final String nome;
    private final Cor cor;
    private Posicao posicao;
    private Tipo tipo;
    private int quantidadeMovimento;

    public Peca(Tipo tipo, Cor cor) {
        this.cor = cor;
        this.tipo = tipo;
        this.nome = tipo.getNome();
        this.quantidadeMovimento = 0;
    }

    public boolean mover(Posicao origem, Posicao destino){
        return this.getEstrategiaMovimento().isMovimentoValido(origem, destino, this.cor);
    }

    public String getNome() {return nome;}
    public Posicao getPosicao() {return posicao;}
    public Cor getCor(){return cor;}
    public Tipo getTipo(){return tipo;}
    public int getQuantidadeMovimento(){return quantidadeMovimento;}
    public EstrategiaMovimento getEstrategiaMovimento(){return tipo.getEstrategiaMovimento();}
    public void setPosicao(Posicao posicao) {this.posicao = posicao;}
    public void setQuantidadeMovimento(int quantidadeMovimento){this.quantidadeMovimento = quantidadeMovimento;}
}

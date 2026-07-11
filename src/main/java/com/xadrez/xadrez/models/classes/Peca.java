package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.TipoPeca;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public class Peca {

    private String nome;
    private final Cor cor;
    private TipoPeca tipoPeca;
    private int quantidadeMovimentos;

    public Peca(TipoPeca tipoPeca, Cor cor) {
        this.cor = cor;
        this.tipoPeca = tipoPeca;
        this.nome = tipoPeca.getNome();
        this.quantidadeMovimentos = 0;
    }

    public boolean mover(Posicao origem, Posicao destino){
        return this.getEstrategiaMovimento().isMovimentoValido(origem, destino, this.cor);
    }

    public void aumentarQuantidadeDeMovimentos(){
        this.quantidadeMovimentos++;
    }

    public String getNome() {return this.nome;}
    public Cor getCor(){return this.cor;}
    public TipoPeca getTipo(){return this.tipoPeca;}
    public int getQuantidadeMovimento(){return this.quantidadeMovimentos;}
    public EstrategiaMovimento getEstrategiaMovimento(){return this.tipoPeca.getEstrategiaMovimento();}
    public void setTipoPeca(TipoPeca tipoPeca){this.tipoPeca = tipoPeca; this.nome = tipoPeca.getNome();}
}

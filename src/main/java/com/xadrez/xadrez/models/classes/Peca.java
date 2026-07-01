package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

public abstract class Peca {

    private final String nome;
    private final Cor cor;
    private Posicao posicao;
    private final EstrategiaMovimento estrategiaMovimento;

    public Peca(String nome, Cor cor, EstrategiaMovimento estrategiaMovimento) {
        this.nome = nome;
        this.cor = cor;
        this.estrategiaMovimento = estrategiaMovimento;
    }

    public boolean mover(Posicao origem, Posicao destino) throws MovimentoInvalidoException {
        return this.getEstrategiaMovimento().isMovimentoValido(origem, destino);
    }

    public String getNome() {return nome;}
    public Posicao getPosicao() {return posicao;}
    public Cor getCor(){return cor;}
    public EstrategiaMovimento getEstrategiaMovimento(){return estrategiaMovimento;}
    public void setPosicao(Posicao posicao) {this.posicao = posicao;}
}

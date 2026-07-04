package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;

import java.util.ArrayList;

public class Jogo {

    private ArrayList<Jogador> jogadores;
    private Tabuleiro tabuleiro;
    private Cor corTurnoAtual;
    private Jogador jogadorAtual;

    public Jogo(){
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();

        Jogador jogador1 = new Jogador("Mario", Cor.BRANCA);
        Jogador jogador2 = new Jogador("Luigi", Cor.PRETA);

        jogadores.add(jogador1);
        jogadores.add(jogador2);

        this.jogadorAtual = jogador1;
        this.corTurnoAtual = jogador1.getCor();
    }

    public ArrayList<Jogador> getJogadores() {return this.jogadores;}
    public Jogador getJogadorAtual(){return this.jogadorAtual;}
    public Tabuleiro getTabuleiro() {return this.tabuleiro;}
    public Cor getCorTurnoAtual(){return this.corTurnoAtual;}
    public void setCorTurnoAtual(Cor corTurnoAtual ){ this.corTurnoAtual = corTurnoAtual;}
    public void setJogadorAtual(Jogador jogadorAtual){ this.jogadorAtual = jogadorAtual;}
}

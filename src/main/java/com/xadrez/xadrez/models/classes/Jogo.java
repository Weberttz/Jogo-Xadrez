package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;

import java.util.ArrayList;

public class Jogo {

    private final ArrayList<Jogador> jogadores;
    private final ArrayList<String> logs;
    private final Tabuleiro tabuleiro;
    private Cor corTurnoAtual;
    private Jogador jogadorAtual;

    public Jogo(){
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();
        jogadores.add(new Jogador("Brancas", Cor.BRANCA));
        jogadores.add(new Jogador("Pretas", Cor.PRETA));
        this.jogadorAtual = jogadores.getFirst();
        this.corTurnoAtual = jogadorAtual.getCor();
        this.logs = new ArrayList<>();
    }

    public ArrayList<Jogador> getJogadores() {return this.jogadores;}
    public Jogador getJogadorAtual(){return this.jogadorAtual;}
    public Tabuleiro getTabuleiro() {return this.tabuleiro;}
    public Cor getCorTurnoAtual(){return this.corTurnoAtual;}
    public ArrayList<String> getLogs(){ return this.logs;}
    public void setCorTurnoAtual(Cor corTurnoAtual ){ this.corTurnoAtual = corTurnoAtual;}
    public void setJogadorAtual(Jogador jogadorAtual){ this.jogadorAtual = jogadorAtual;}
}

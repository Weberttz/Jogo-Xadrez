package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;

import java.util.ArrayList;

public class Jogo {

    private final ArrayList<Jogador> jogadores;
    private final ArrayList<String> logs;
    private final Tabuleiro tabuleiro;
    private Cor corTurnoAtual;
    private Jogador jogadorAtual;
    private int turno;


    public Jogo(){
        this.turno = 1;
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();

        this.jogadores.add(new Jogador("Brancas", Cor.BRANCA));
        this.jogadores.add(new Jogador("Pretas", Cor.PRETA));

        this.jogadorAtual = jogadores.getFirst();
        this.corTurnoAtual = jogadorAtual.getCor();
        this.logs = new ArrayList<>();
    }

    public void mudarTurno(){
        this.corTurnoAtual = (corTurnoAtual == Cor.BRANCA)? Cor.PRETA : Cor.BRANCA;
        if(corTurnoAtual.equals(Cor.BRANCA))
            this.jogadorAtual = jogadores.getFirst();
        else
            this.jogadorAtual = jogadores.getLast();

        this.turno++;
    }

    public void salvarCasaDoRei(Cor corRei, Casa casa){
        if(corRei.equals(Cor.BRANCA)) this.tabuleiro.setCasasDosReis(0, casa);
        else this.tabuleiro.setCasasDosReis(1, casa);
    }

    public Jogador getJogadorAtual(){return this.jogadorAtual;}
    public Tabuleiro getTabuleiro() {return this.tabuleiro;}
    public Cor getCorTurnoAtual(){return this.corTurnoAtual;}
    public ArrayList<String> getLogs(){ return this.logs;}
    public int getTurno(){return this.turno;}
}

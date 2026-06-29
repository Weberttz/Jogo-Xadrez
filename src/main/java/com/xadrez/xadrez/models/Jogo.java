package com.xadrez.xadrez.models;

import com.xadrez.xadrez.models.enums.Cores;

import java.util.ArrayList;

public class Jogo {

    private ArrayList<Jogador> jogadores;
    private Tabuleiro tabuleiro;

    public Jogo(){
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();

        Jogador jogador1 = new Jogador("Jogador 1",Cores.BRANCA);
        Jogador jogador2 = new Jogador("Jogador 2",Cores.PRETA);
    }

    public ArrayList<Jogador> getJogadores() {return jogadores;}
    public Tabuleiro getTabuleiro() {return tabuleiro;}
}

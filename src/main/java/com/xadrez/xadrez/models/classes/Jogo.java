package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;

import java.util.ArrayList;

public class Jogo {

    private ArrayList<Jogador> jogadores;
    private Tabuleiro tabuleiro;
    private Cor corTurnoAtual;

    public Jogo(){
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();

        Jogador jogador1 = new Jogador("Jogador 1", Cor.BRANCA);
        Jogador jogador2 = new Jogador("Jogador 2", Cor.PRETA);

        jogadores.add(jogador1);
        jogadores.add(jogador2);
    }

    public ArrayList<Jogador> getJogadores() {return jogadores;}
    public Tabuleiro getTabuleiro() {return tabuleiro;}
}

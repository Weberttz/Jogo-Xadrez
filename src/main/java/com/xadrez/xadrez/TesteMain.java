package com.xadrez.xadrez;

import com.xadrez.xadrez.models.classes.Jogo;

public class TesteMain {

    public static void main(String[] args){
        Jogo jogo = new Jogo();
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();
        jogo.getTabuleiro().imprimirTabuleiro();
    }
}

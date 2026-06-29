package com.xadrez.xadrez.models;

import com.xadrez.xadrez.models.enums.Cores;

public class Tabuleiro {

    private Casa[][] casas = new Casa[8][8];

    public void inicializarTabuleiro(){
        final int dimensao = 8;
        for(int linha = 0; linha < dimensao; linha++){
            for(int coluna = 0; coluna < dimensao; coluna++){
                Casa casa = new Casa(linha, coluna);
                casas[linha][coluna] = casa;
            }
        }
    }

    public void colocarPecas(){
        System.out.println("teste");
        final int dimensao = 8;

        Peao peao = new Peao(Cores.BRANCA);

        for(int linha = 0; linha < dimensao; linha++){
            for(int coluna = 0; coluna < dimensao; coluna++){
                casas[linha][coluna].setPeca(peao);
            }
        }
    }

    public void imprimirTabuleiro(){
        final int dimensao = 8;
        for(int linha = 0; linha < dimensao; linha++){
            for(int coluna = 0; coluna < dimensao; coluna++){
                System.out.print(casas[linha][coluna].getPeca().getNome().charAt(0) + " ");
            }
            System.out.print("\n");
        }
    }

    public Casa getCasa(int linha, int coluna) {return casas[linha][coluna];}
}

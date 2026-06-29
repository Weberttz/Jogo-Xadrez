package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import javafx.scene.control.Tab;

public class Tabuleiro {

    private Casa[][] casas;

    public Tabuleiro(){
        this.casas = new Casa[8][8];
    }

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
        final int dimensao = 8;
        int linha1 = 0, linha8 = 7, linha2 = 1, linha7 = 6;

        for(int coluna = 0; coluna < dimensao; coluna++){
            casas[linha2][coluna].setPeca(new Peao(Cor.PRETA));
            casas[linha7][coluna].setPeca(new Peao(Cor.BRANCA));
        }
        for (int coluna = 0; coluna < dimensao; coluna++) {
            int somaModSete = (linha1 + coluna) % 7;
            switch (somaModSete) {
                case 0:
                    casas[linha1][coluna].setPeca(new Torre(Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Torre(Cor.BRANCA));
                    break;
                case 1:
                case 6:
                    casas[linha1][coluna].setPeca(new Cavalo(Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Cavalo(Cor.BRANCA));
                    break;
                case 2:
                case 5:
                    casas[linha1][coluna].setPeca(new Bispo(Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Bispo(Cor.BRANCA));
                    break;
                case 3:
                    casas[linha1][coluna].setPeca(new Rainha(Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Rainha(Cor.BRANCA));
                    break;
                case 4:
                    casas[linha1][coluna].setPeca(new Rei(Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Rei(Cor.BRANCA));
                    break;
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

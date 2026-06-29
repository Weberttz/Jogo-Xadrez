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
        System.out.println("teste");
        final int dimensao = 8;

        Torre torreBranca = new Torre(Cor.BRANCA);
        Torre torrePreta = new Torre(Cor.PRETA);
        Peao peaoBranco = new Peao(Cor.BRANCA);
        Peao peaoPreto = new Peao(Cor.PRETA);

        for(int coluna = 0; coluna < dimensao; coluna++){
            casas[1][coluna].setPeca(peaoPreto);
            casas[6][coluna].setPeca(peaoBranco);
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

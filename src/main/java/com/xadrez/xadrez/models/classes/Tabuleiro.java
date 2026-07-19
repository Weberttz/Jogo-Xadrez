package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.TipoPeca;

import java.util.ArrayList;

public class Tabuleiro {

    private Casa[][] casas;
    private final ArrayList<Casa> casasDosReis;

    public Tabuleiro(){
        this.casas = new Casa[8][8];
        this.casasDosReis = new ArrayList<>();
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
            casas[linha2][coluna].setPeca(new Peca(TipoPeca.PEAO, Cor.PRETA));
            casas[linha2][coluna].setEstaVazia(false);
            casas[linha7][coluna].setPeca(new Peca(TipoPeca.PEAO, Cor.BRANCA));
            casas[linha7][coluna].setEstaVazia(false);
        }
        for (int coluna = 0; coluna < dimensao; coluna++) {
            int somaModSete = (linha1 + coluna) % 7;
            switch (somaModSete) {
                case 0:
                    casas[linha1][coluna].setPeca(new Peca(TipoPeca.TORRE, Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Peca(TipoPeca.TORRE, Cor.BRANCA));
                    break;
                case 1:
                case 6:
                    casas[linha1][coluna].setPeca(new Peca(TipoPeca.CAVALO, Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Peca(TipoPeca.CAVALO, Cor.BRANCA));
                    break;
                case 2:
                case 5:
                    casas[linha1][coluna].setPeca(new Peca(TipoPeca.BISPO, Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Peca(TipoPeca.BISPO, Cor.BRANCA));
                    break;
                case 3:
                    casas[linha1][coluna].setPeca(new Peca(TipoPeca.RAINHA, Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Peca(TipoPeca.RAINHA, Cor.BRANCA));
                    break;
                case 4:
                    casas[linha1][coluna].setPeca(new Peca(TipoPeca.REI, Cor.PRETA));
                    casas[linha8][coluna].setPeca(new Peca(TipoPeca.REI, Cor.BRANCA));
                    this.casasDosReis.add(casas[linha8][coluna]);
                    this.casasDosReis.add(casas[linha1][coluna]);
                    break;
            }
            casas[linha1][coluna].setEstaVazia(false);
            casas[linha8][coluna].setEstaVazia(false);
        }
    }

    public void imprimirTabuleiro(){
        final int dimensao = 8;
        System.out.println("\n\n\n\n");
        for(int linha = 0; linha < dimensao; linha++){
            for(int coluna = 0; coluna < dimensao; coluna++){
                if(casas[linha][coluna].getPeca() != null)
                    System.out.print(casas[linha][coluna].getPeca().getNome().charAt(0) + " ");
                else
                    System.out.print("  ");
            }
            System.out.print("\n");
        }
    }

    public boolean dentroDoLimite(int x, int y){
        boolean dentroEmX = (0 <= x && x <= 7);
        boolean dentroEmY = (0 <= y && y <= 7);

        return dentroEmX && dentroEmY;
    }

    public Casa getCasa(int linha, int coluna) {
        if(linha >= 0 && linha < 8 && coluna >=0 && coluna < 8) return casas[linha][coluna];
        return null;
    }
    public Casa getCasaDoRei(Cor corRei){
        return (corRei.equals(Cor.BRANCA))? this.casasDosReis.getFirst() : this.casasDosReis.getLast();
    }
    public void setCasasDosReis(int index, Casa casa){this.casasDosReis.set(index, casa);}
}

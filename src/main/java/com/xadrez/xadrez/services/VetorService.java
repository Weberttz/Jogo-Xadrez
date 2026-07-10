package com.xadrez.xadrez.services;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Direcao;
import com.xadrez.xadrez.models.classes.Posicao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VetorService {

    public int calcularDirecaoVetorEmX(int origemX, int destinoX){
        if(origemX == destinoX) return 0;
        return (origemX < destinoX)? 1 : -1;
    }

    public int calcularDirecaoVetorEmY(int origemY, int destinoY){
        if(origemY == destinoY) return 0;
        return (origemY < destinoY)? 1 : -1;
    }

    @NotNull
    public static ArrayList<Direcao> getDirecoes(Casa casa) {
        Direcao direcaoNorte = new Direcao("direcaoNorte", casa.getX(), -1, 0);
        Direcao direcaoSul = new Direcao("direcaoSul", 7 - casa.getX(), 1, 0);
        Direcao direcaoOeste = new Direcao("direcaoOeste", casa.getY(), 0, -1);
        Direcao direcaoLeste = new Direcao("direcaoLeste", 7 - casa.getY(), 0, 1);

        return new ArrayList<>(){{
            add(direcaoNorte); add(direcaoSul); add(direcaoOeste); add(direcaoLeste);
            add(new Direcao("direcaoNoroeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoOeste.getComprimento()), -1, -1));
            add(new Direcao("direcaoNordeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoLeste.getComprimento()), -1, 1));
            add(new Direcao("direcaoSudoeste",
                    Math.min(direcaoSul.getComprimento(), direcaoOeste.getComprimento()), 1, -1));
            add(new Direcao("direcaoSudeste",
                    Math.min(direcaoSul.getComprimento(), direcaoLeste.getComprimento()), 1, 1));
        }};
    }

    @NotNull
    public static List<Posicao> getPosicoes(Casa casa){
        int x = casa.getX();
        int y = casa.getY();
        List<Posicao> posicoes = Arrays.asList(
                new Posicao(x + 1, y + 2),
                new Posicao(x + 1, y - 2),
                new Posicao(x - 1, y + 2),
                new Posicao(x - 1, y - 2),
                new Posicao(x + 2, y + 1),
                new Posicao(x + 2, y - 1),
                new Posicao(x - 2, y + 1),
                new Posicao(x - 2, y - 1)
        );
        return posicoes.stream()
                .filter(p -> p.getX() >= 0 && p.getX() <= 7 && p.getY() >= 0 && p.getY() <= 7)
                .toList();
    }

    @NotNull
    public static List<Posicao> getPosicoesRei(){
        int posicaoYRei = 4;

        List<Posicao> posicoes = Arrays.asList(
                new Posicao( 0, posicaoYRei + 2),
                new Posicao( 0, posicaoYRei - 2),
                new Posicao( 7, posicaoYRei + 2),
                new Posicao( 7, posicaoYRei - 2)
        );

        return posicoes;
    }

    @NotNull
    public static List<Posicao> getPosicoesTorre(){
        int posicaoYTorre1 = 0;
        int posicaoYTorre2 = 7;

        List<Posicao> posicoes = Arrays.asList(
                new Posicao( 0, posicaoYTorre2 - 2),
                new Posicao( 0, posicaoYTorre1 + 3),
                new Posicao( 7, posicaoYTorre2 - 2),
                new Posicao( 7, posicaoYTorre1 + 3)
        );

        return posicoes;
    }
}

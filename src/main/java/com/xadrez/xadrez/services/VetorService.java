package com.xadrez.xadrez.services;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Direcao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VetorService {

    public int calcularDirecaoVetorEmX(int origemX, int destinoX){
        if(origemX == destinoX) return 0;
        return (origemX < destinoX)? 1 : -1;
    }

    public int calcularDirecaoVetorEmY(int origemY, int destinoY){
        if(origemY == destinoY) return 0;
        return (origemY < destinoY)? 1 : -1;
    }

    public int calcularComprimentoMax(ArrayList<Direcao> direcoes){
        return direcoes.stream().mapToInt(Direcao::getComprimento).max().orElse(0);
    }

    @NotNull
    public static ArrayList<Direcao> getDirecoes(Casa casa) {
        Direcao direcaoNorte = new Direcao("direcaoNorte", casa.getX(), false, -1, 0);
        Direcao direcaoSul = new Direcao("direcaoSul", 7 - casa.getX(), false, 1, 0);
        Direcao direcaoOeste = new Direcao("direcaoOeste", casa.getY(), false, 0, -1);
        Direcao direcaoLeste = new Direcao("direcaoLeste", 7 - casa.getY(), false, 0, 1);

        return new ArrayList<>(){{
            add(direcaoNorte); add(direcaoSul); add(direcaoOeste); add(direcaoLeste);
            add(new Direcao("direcaoNoroeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoOeste.getComprimento()), false, -1, -1));
            add(new Direcao("direcaoNordeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoLeste.getComprimento()), false, -1, 1));
            add(new Direcao("direcaoSudoeste",
                    Math.min(direcaoSul.getComprimento(), direcaoOeste.getComprimento()), false, 1, -1));
            add(new Direcao("direcaoSudeste",
                    Math.min(direcaoSul.getComprimento(), direcaoLeste.getComprimento()), false, 1, 1));
        }};
    }
}

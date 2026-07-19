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
}

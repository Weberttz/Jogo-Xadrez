package com.xadrez.xadrez.services;

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

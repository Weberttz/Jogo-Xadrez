package com.xadrez.xadrez.models.classes;

public class Direcao {

    private int comprimento;
    private String nome;
    private int direcaoX;
    private int direcaoY;

    public Direcao(String nome, int comprimento, int direcaoX, int direcaoY){
        this.nome = nome;
        this.comprimento = comprimento;
        this.direcaoX = direcaoX;
        this.direcaoY = direcaoY;
    }

    public String getNome(){return this.nome;}
    public int getComprimento() {return this.comprimento;}
    public int getDirecaoX(){return this.direcaoX;}
    public int getDirecaoY(){return this.direcaoY;}
}

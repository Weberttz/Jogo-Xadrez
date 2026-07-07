package com.xadrez.xadrez.models.classes;

public class Direcao {

    private int comprimento;
    private String direcao;
    private boolean status;
    private int direcaoX;
    private int direcaoY;

    public Direcao(String direcao, int comprimento, boolean status, int direcaoX, int direcaoY){
        this.direcao = direcao;
        this.comprimento = comprimento;
        this.status = status;
        this.direcaoX = direcaoX;
        this.direcaoY = direcaoY;
    }

    public int getComprimento() {return this.comprimento;}
    public String getDirecao() {return this.direcao;}
    public boolean getStatus() {return this.status;}
    public int getDirecaoX(){return this.direcaoX;}
    public int getDirecaoY(){return this.direcaoY;}
}

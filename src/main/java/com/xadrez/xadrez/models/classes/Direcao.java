package com.xadrez.xadrez.models.classes;

public class Direcao {

    private int comprimento;
    private String direcao;
    private boolean status;

    public Direcao(String direcao, int comprimento, boolean status){
        this.direcao = direcao;
        this.comprimento = comprimento;
        this.status = status;
    }

    public int getComprimento() {return this.comprimento;}
    public String getDirecao() {return this.direcao;}
    public boolean getStatus() {return this.status;}
}

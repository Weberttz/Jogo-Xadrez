package com.xadrez.xadrez.models;

public class Casa {
    private int x;
    private int y;
    private Peca peca;

    public Casa(int x, int y){
        this.x = x;
        this.y = y;
        this.peca = null;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void setPeca(Peca peca) {this.peca = peca;}
    public Peca getPeca() {return peca;}
}

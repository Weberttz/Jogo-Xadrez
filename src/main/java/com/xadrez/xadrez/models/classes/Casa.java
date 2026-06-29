package com.xadrez.xadrez.models.classes;

public class Casa {
    private int x;
    private int y;
    private Peca peca;
    private boolean estaVazia;

    public Casa(int x, int y){
        this.x = x;
        this.y = y;
        this.estaVazia = true;
        this.peca = null;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void setPeca(Peca peca) {this.peca = peca;}
    public Peca getPeca() {return peca;}
    public boolean estaVazia() {return estaVazia;}
}

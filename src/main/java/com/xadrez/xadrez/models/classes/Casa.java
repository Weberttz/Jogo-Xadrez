package com.xadrez.xadrez.models.classes;

public class Casa {
    private int x;
    private int y;
    private Peca peca;
    private boolean estaVazia;
    private Posicao posicao;

    public Casa(int x, int y){
        this.x = x;
        this.y = y;
        this.estaVazia = true;
        this.peca = null;
        this.posicao = new Posicao(x, y);
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public void setPeca(Peca peca) {this.peca = peca;}
    public Peca getPeca() {return this.peca;}
    public Posicao getPosicao(){return this.posicao;}
    public boolean estaVazia() {return this.estaVazia;}
    public boolean setEstaVazia(boolean status){return estaVazia=status;}
}

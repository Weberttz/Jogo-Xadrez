package com.xadrez.xadrez.models.enums;

public enum Cores {
    BRANCA("#eeeed2"),
    PRETA("#769656");

    private final String estilo;

    Cores(String estilo){this.estilo = estilo;}
    public String getEstilo() {return estilo;}
}

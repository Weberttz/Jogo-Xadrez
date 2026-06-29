package com.xadrez.xadrez.models.enums;

public enum Cor {
    BRANCA("#eeeed2"),
    PRETA("#769656");

    private final String estilo;

    Cor(String estilo){this.estilo = estilo;}
    public String getEstilo() {return estilo;}
}

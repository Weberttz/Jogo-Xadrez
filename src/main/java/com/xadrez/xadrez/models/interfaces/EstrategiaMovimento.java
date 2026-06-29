package com.xadrez.xadrez.models.interfaces;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.classes.Tabuleiro;
import javafx.scene.control.Tab;

public interface EstrategiaMovimento {
    boolean isMovimentoValido(Posicao origem, Posicao destino, Tabuleiro tabuleiro);
}

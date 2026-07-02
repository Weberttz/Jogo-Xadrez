package com.xadrez.xadrez.models.interfaces;

import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.classes.Tabuleiro;
import com.xadrez.xadrez.models.enums.Cor;
import javafx.scene.control.Tab;

public interface EstrategiaMovimento {
    boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor);
}

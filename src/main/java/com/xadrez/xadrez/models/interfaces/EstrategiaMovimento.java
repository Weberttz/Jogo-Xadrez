package com.xadrez.xadrez.models.interfaces;

import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import javafx.scene.control.Tab;

import java.util.List;

public interface EstrategiaMovimento {
    boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor);
    List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca);
}

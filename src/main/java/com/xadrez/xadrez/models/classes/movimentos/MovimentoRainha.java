package com.xadrez.xadrez.models.classes.movimentos;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.TipoPeca;
import com.xadrez.xadrez.models.interfaces.EstrategiaMovimento;

import java.util.ArrayList;
import java.util.List;

public class MovimentoRainha implements EstrategiaMovimento {

    public boolean isMovimentoValido(Posicao origem, Posicao destino, Cor cor){
        EstrategiaMovimento estrategiaMovimentoBispo = new MovimentoBispo();
        EstrategiaMovimento estrategiaMovimentoTorre = new MovimentoTorre();

        return estrategiaMovimentoBispo.isMovimentoValido(origem, destino, cor) ||
                estrategiaMovimentoTorre.isMovimentoValido(origem, destino, cor);
    }
    @Override
    public List<Casa> gerarMovimentosPossiveis(Jogo jogo, Casa origem, Peca peca) {
        List<Casa> movimentos = new ArrayList<>();

        movimentos.addAll(new Peca(TipoPeca.TORRE, peca.getCor()).
                getEstrategiaMovimento().gerarMovimentosPossiveis(jogo, origem, peca));
        movimentos.addAll(new Peca(TipoPeca.BISPO, peca.getCor()).
                getEstrategiaMovimento().gerarMovimentosPossiveis(jogo, origem, peca));

        return movimentos;
    }
}

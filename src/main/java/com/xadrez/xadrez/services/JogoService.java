package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;

public class JogoService {

    public void inicializarNovoJogo(Jogo jogo) {
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();
        jogo.setCorTurnoAtual(Cor.BRANCA);
    }

    public void jogarTurno(Jogo jogo, Casa casaOrigem,
                           Casa casaDestino){
        Posicao origem = new Posicao(casaOrigem.getX(), casaOrigem.getY());
        Posicao destino = new Posicao(casaDestino.getX(), casaDestino.getY());
        Peca peca;

        try {
            peca = casaOrigem.getPeca();
            if(peca == null || peca.getEstrategiaMovimento() == null) throw new Exception("peça nula!");

            boolean movimentoValido = peca.mover(origem, destino);

            if(movimentoValido){
                casaOrigem.setPeca(null); casaOrigem.setEstaVazia(true);
                casaDestino.setPeca(peca); casaDestino.setEstaVazia(false);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        mudarTurno(jogo);
    }

    private void mudarTurno(Jogo jogo){
        Cor corTurnoAtual = jogo.getCorTurnoAtual();
        if (corTurnoAtual == Cor.BRANCA)
            jogo.setCorTurnoAtual(Cor.PRETA);
        else
            jogo.setCorTurnoAtual(Cor.BRANCA);
    }
}

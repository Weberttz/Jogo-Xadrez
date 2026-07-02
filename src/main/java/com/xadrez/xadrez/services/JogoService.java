package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.Tipo;

public class JogoService {

    public void inicializarNovoJogo(Jogo jogo) {
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();
        jogo.setCorTurnoAtual(Cor.BRANCA);
    }

    public boolean podeSelecionar(Jogo jogo, Casa casa) {
        if (casa.estaVazia()) return false;
        return casa.getPeca().getCor().equals(jogo.getCorTurnoAtual());
    }

    public void jogarTurno(Jogo jogo, Casa casaOrigem,
                           Casa casaDestino){
        Peca peca;
        try {
            peca = casaOrigem.getPeca();
            if(peca == null) throw new Exception("peça nula!");

            if(validarMovimento(jogo, peca, casaOrigem, casaDestino)){
                casaOrigem.setPeca(null); casaOrigem.setEstaVazia(true);
                casaDestino.setPeca(peca); casaDestino.setEstaVazia(false);
            }else throw new MovimentoInvalidoException(peca);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        mudarTurno(jogo);
    }

    private boolean validarMovimento(Jogo jogo, Peca peca,
                                     Casa casaOrigem, Casa casaDestino){
        Posicao origem = new Posicao(casaOrigem.getX(), casaOrigem.getY());
        Posicao destino = new Posicao(casaDestino.getX(), casaDestino.getY());

        boolean movimentoValido = peca.mover(origem, destino);
        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());

        if(peca.getTipo().equals(Tipo.PEAO) && movimentoValido){
            if (distanciaX + distanciaY == 2){
                if(!casaDestino.estaVazia())
                    return !casaDestino.getPeca().getCor().equals(jogo.getCorTurnoAtual());
                else
                    return false;
            }
        }

        return movimentoValido;
    }

    private void mudarTurno(Jogo jogo){
        Cor corTurnoAtual = jogo.getCorTurnoAtual();
        if (corTurnoAtual == Cor.BRANCA)
            jogo.setCorTurnoAtual(Cor.PRETA);
        else
            jogo.setCorTurnoAtual(Cor.BRANCA);
    }
}

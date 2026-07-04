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

            peca.setQuantidadeMovimento(peca.getQuantidadeMovimento() + 1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        mudarTurno(jogo);
    }

    private boolean validarMovimento(Jogo jogo, Peca peca,
                                     Casa casaOrigem, Casa casaDestino){
        Posicao origem = casaOrigem.getPosicao();
        Posicao destino = casaDestino.getPosicao();

        boolean movimentoValido = peca.mover(origem, destino);

        if(!movimentoValido) return false;

        if(peca.getTipo().equals(Tipo.PEAO)){
           return verificarAtaquePeao(peca, casaOrigem, casaDestino);
        }

        if(!peca.getTipo().equals(Tipo.CAVALO) && !peca.getTipo().equals(Tipo.REI)
                && verificarColisao(jogo,origem, destino)) return false;

        if(casaDestino.getPeca() != null)
            return !casaDestino.getPeca().getCor().equals(jogo.getCorTurnoAtual());

        return true;
    }

    private boolean verificarColisao(Jogo jogo, Posicao origem, Posicao destino){

        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());
        int comprimentoVetor = Math.max(distanciaX, distanciaY);

        Tabuleiro tabuleiro = jogo.getTabuleiro();

        int dirX = calcularDirecaoVetorEmX(origem.getX(), destino.getX());
        int dirY = calcularDirecaoVetorEmY(origem.getY(), destino.getY());

        for(int i = 1; i<comprimentoVetor; i++){
            Casa casaAux = tabuleiro.getCasa(origem.getX() + dirX * i , origem.getY() + dirY * i);
            if(!casaAux.estaVazia()) {
                return true;
            }
        }

        return false;
    }

    private boolean verificarAtaquePeao(Peca peca, Casa casaOrigem, Casa casaDestino){
        int distanciaX = Math.abs(casaOrigem.getPosicao().getX() - casaDestino.getPosicao().getX());
        int distanciaY = Math.abs(casaOrigem.getPosicao().getY() - casaDestino.getPosicao().getY());
        int comprimento = distanciaX + distanciaY;

        if(comprimento == 1)
            return casaDestino.estaVazia();
        else if (comprimento == 2 && peca.getQuantidadeMovimento() == 0)
            return true;
        else if(distanciaX == 1 && distanciaY == 1)
            return !casaDestino.estaVazia() && !casaDestino.getPeca().getCor().equals(peca.getCor());

        return false;
    }

    private int calcularDirecaoVetorEmX(int origemX, int destinoX){
        if(origemX == destinoX) return 0;
        return (origemX < destinoX)? 1 : -1;
    }

    private int calcularDirecaoVetorEmY(int origemY, int destinoY){
        if(origemY == destinoY) return 0;
        return (origemY < destinoY)? 1 : -1;
    }

    private void mudarTurno(Jogo jogo){
        Cor corTurnoAtual = jogo.getCorTurnoAtual();
        if (corTurnoAtual == Cor.BRANCA) {
            jogo.setCorTurnoAtual(Cor.PRETA);
            jogo.setJogadorAtual(jogo.getJogadores().getLast());
        }
        else {
            jogo.setCorTurnoAtual(Cor.BRANCA);
            jogo.setJogadorAtual(jogo.getJogadores().getFirst());
        }
    }
}

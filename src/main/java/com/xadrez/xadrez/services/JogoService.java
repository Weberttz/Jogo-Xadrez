package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.StatusJogo;
import com.xadrez.xadrez.models.enums.Tipo;

import java.util.ArrayList;
import java.util.List;

public class JogoService {

    public void inicializarNovoJogo(Jogo jogo) {
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();
    }

    public void jogarTurno(Jogo jogo, Casa casaOrigem,
                           Casa casaDestino){
        Peca peca = null;
        Peca pecaAnteior = casaDestino.getPeca();
        try {
            peca = casaOrigem.getPeca();
            if(peca == null) throw new Exception("peça nula!");

            if(!validarMovimento(jogo, peca, casaOrigem, casaDestino))
                throw new MovimentoInvalidoException(peca);

            realizarPreMove(jogo, peca, casaOrigem, casaDestino);

            if(peca.getTipo().equals(Tipo.REI)
                    && peca.getQuantidadeMovimento() == 0) verificarRoque(jogo, peca, casaDestino);

            if(verificarAtaqueAoRei(jogo))
                throw new MovimentoInvalidoException(peca);

            peca.aumentarQuantidadeDeMovimentos();
        } catch (Exception e){
            System.out.println(e.getMessage());
            desfazerPreMove(jogo, peca, pecaAnteior,casaOrigem, casaDestino);
            return;
        }

        LogsService logsService = new LogsService();
        jogo.getLogs().add(logsService.formarStringDeLog(jogo, peca, casaDestino));
        jogo.mudarTurno();

        if(verificarAtaqueAoRei(jogo))
            jogo.getLogs().add(logsService.formarStringDeXeque(jogo));
        else
            jogo.mudarStatusJogo(StatusJogo.NORMAL);
    }

    private boolean validarMovimento(Jogo jogo, Peca peca,
                                     Casa casaOrigem, Casa casaDestino){
        Posicao origem = casaOrigem.getPosicao();
        Posicao destino = casaDestino.getPosicao();

        boolean movimentoValido = peca.mover(origem, destino);

        if(!movimentoValido) return false;

        if(peca.getTipo().equals(Tipo.PEAO))
           return verificarMovimentoDoPeao(peca, casaOrigem, casaDestino);

        if(peca.getTipo().equals(Tipo.REI))
            return verificarMovimentoDoRei(peca, casaOrigem, casaDestino);

        if(!peca.getTipo().equals(Tipo.CAVALO) && !peca.getTipo().equals(Tipo.REI)
                && verificarColisao(jogo,origem, destino)) return false;

        if(casaDestino.getPeca() != null)
            return !casaDestino.getPeca().getCor().equals(jogo.getCorTurnoAtual());

        return true;
    }

    private boolean verificarColisao(Jogo jogo, Posicao origem, Posicao destino){

        VetorService vetorService = new VetorService();

        int distanciaX = Math.abs(origem.getX() - destino.getX());
        int distanciaY = Math.abs(origem.getY() - destino.getY());
        int comprimentoVetor = Math.max(distanciaX, distanciaY);

        Tabuleiro tabuleiro = jogo.getTabuleiro();

        int dirX = vetorService.calcularDirecaoVetorEmX(origem.getX(), destino.getX());
        int dirY = vetorService.calcularDirecaoVetorEmY(origem.getY(), destino.getY());

        for(int i = 1; i<comprimentoVetor; i++){
            Casa casaAux = tabuleiro.getCasa(origem.getX() + dirX * i , origem.getY() + dirY * i);
            if(!casaAux.estaVazia()) {
                return true;
            }
        }

        return false;
    }

    private boolean verificarMovimentoDoPeao(Peca peca, Casa casaOrigem, Casa casaDestino){
        int distanciaX = Math.abs(casaOrigem.getPosicao().getX() - casaDestino.getPosicao().getX());
        int distanciaY = Math.abs(casaOrigem.getPosicao().getY() - casaDestino.getPosicao().getY());
        int comprimento = distanciaX + distanciaY;

        if(comprimento == 1)
            return casaDestino.estaVazia();
        else if(distanciaX == 1 && distanciaY == 1)
            return !casaDestino.estaVazia() && !casaDestino.getPeca().getCor().equals(peca.getCor());

        return comprimento == 2 && peca.getQuantidadeMovimento() == 0 && distanciaX > 0 && casaDestino.estaVazia();
    }

    private boolean verificarMovimentoDoRei(Peca peca, Casa casaOrigem, Casa casaDestino){
        int distanciaX = Math.abs(casaOrigem.getPosicao().getX() - casaDestino.getPosicao().getX());
        int distanciaY = Math.abs(casaOrigem.getPosicao().getY() - casaDestino.getPosicao().getY());
        int comprimento = distanciaX + distanciaY;

        return comprimento == 2 && peca.getQuantidadeMovimento() == 0 && distanciaY > 0 || comprimento == 1;
    }

    private boolean verificarAtaqueAoRei(Jogo jogo){
        Casa casa = jogo.getTabuleiro().getCasaDoRei(jogo.getCorTurnoAtual());
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        Peca pecaDoRei = casa.getPeca();

        if(casa.estaVazia()){System.out.println("Casa vazia"); return false;}

        ArrayList<Direcao> direcoes = VetorService.getDirecoes(casa);
        List<Posicao> posicoes = VetorService.getPosicoes(casa);

        for(Posicao posicao : posicoes){
           Casa casaAux = tabuleiro.getCasa(posicao.getX(), posicao.getY());
           Peca peca = casaAux.getPeca();
           if(!casaAux.estaVazia()){
               if(peca.getTipo().equals(Tipo.CAVALO)
                       && !peca.getCor().equals(pecaDoRei.getCor()))
                   return true;
           }
        }

        for(Direcao direcao : direcoes){
            System.out.println(direcao.getNome() + " " + direcao.getComprimento());
            for(int comprimento=1; comprimento<=direcao.getComprimento(); comprimento++){
                Casa casaAux = tabuleiro.getCasa(casa.getX() + comprimento * direcao.getDirecaoX(),
                        casa.getY() + comprimento * direcao.getDirecaoY());

                Peca peca = casaAux.getPeca();

                if(!casaAux.estaVazia()) {
                    if ((validarMovimento(jogo, peca, casa, casaAux) || validarMovimento(jogo, peca, casaAux, casa))
                            && !peca.getCor().equals(pecaDoRei.getCor())) {
                        return true;
                    }
                    else
                        break;
                }
            }
        }

        return false;
    }

    private void realizarPreMove(Jogo jogo, Peca peca, Casa casaOrigem, Casa casaDestino){
        casaOrigem.setPeca(null);
        casaOrigem.setEstaVazia(true);
        casaDestino.setPeca(peca);
        casaDestino.setEstaVazia(false);
        if(peca.getTipo().equals(Tipo.REI)) jogo.salvarCasaDoRei(peca, casaDestino);
    }

    private void desfazerPreMove(Jogo jogo, Peca peca, Peca pecaAnterior, Casa casaOrigem, Casa casaDestino){
        casaDestino.setPeca(pecaAnterior);
        casaDestino.setEstaVazia(true);
        casaOrigem.setPeca(peca);
        casaOrigem.setEstaVazia(false);

        if(peca.getTipo().equals(Tipo.REI)) jogo.salvarCasaDoRei(peca, casaOrigem);
    }

    private void verificarRoque(Jogo jogo, Peca peca, Casa casaDestino){
        if(!peca.getTipo().equals(Tipo.REI)) return;

        Tabuleiro tabuleiro = jogo.getTabuleiro();

        Posicao posicaoFinal = casaDestino.getPosicao();

        int indice = 0;
        List<Posicao> posicoesRei = VetorService.getPosicoesRei();
        List<Posicao> posicoesTorre = VetorService.getPosicoesTorre();

        for(Posicao posicao : posicoesRei){
            if(posicaoFinal.getX() == posicao.getX()
                && posicaoFinal.getY() == posicao.getY()) {

                Posicao posicaoDestinoTorre = posicoesTorre.get(indice);
                realizarRoque(posicaoDestinoTorre, posicao, tabuleiro);
                break;
            }
            indice++;
        }
    }

    private void realizarRoque(Posicao posicaoDestinoTorre, Posicao posicaDoRei, Tabuleiro tabuleiro){
        int distancia;

        if(posicaDoRei.getY() == 2) distancia = -2;
        else distancia = 1;

        Casa casaOrigemTorre = tabuleiro.getCasa(posicaDoRei.getX(), posicaDoRei.getY() + distancia);
        Casa casaDestinoTorre = tabuleiro.getCasa(posicaoDestinoTorre.getX(), posicaoDestinoTorre.getY());

        Peca peca = casaOrigemTorre.getPeca();
        casaOrigemTorre.setEstaVazia(true);
        casaOrigemTorre.setPeca(null);
        casaDestinoTorre.setEstaVazia(false);
        casaDestinoTorre.setPeca(peca);
    }
}

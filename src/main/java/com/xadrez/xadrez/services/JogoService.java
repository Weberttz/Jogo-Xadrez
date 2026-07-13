package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.StatusJogo;
import com.xadrez.xadrez.models.enums.TipoPeca;
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
        ArrayList<String> logs = jogo.getLogs();
        LogsService logsService = new LogsService();
        boolean fim = false;

        try {
            peca = casaOrigem.getPeca();
            if(peca == null) throw new Exception("peça nula!");

            if(!validarMovimento(jogo, peca, casaOrigem, casaDestino))
                throw new MovimentoInvalidoException(peca);

            realizarPreMove(jogo, peca, casaOrigem, casaDestino);

            if(peca.getTipo().equals(TipoPeca.REI) && peca.getQuantidadeMovimento() == 0) verificarRoque(jogo, peca, casaDestino);

            if(peca.getTipo().equals(TipoPeca.PEAO) && (casaDestino.getX() == 0 || casaDestino.getX() == 7)) {
                peca.setTipoPeca(new DialogoService().escolherPromocao());
                logs.add(logsService.formarStringDePromocao(jogo, peca.getTipo()));
            }

            if(verificarAtaqueAoRei(jogo)) throw new MovimentoInvalidoException(peca);

            peca.aumentarQuantidadeDeMovimentos();
        } catch (Exception e){
            desfazerPreMove(jogo, peca, pecaAnteior, casaOrigem, casaDestino);
            System.out.println(e.getMessage());
            return;
        }

        logs.add(logsService.formarStringDeLog(jogo, peca, casaDestino));
        jogo.mudarTurno();

        if(verificarAtaqueAoRei(jogo)) {
            logs.add(logsService.formarStringDeXeque(jogo));
            jogo.mudarStatusJogo(StatusJogo.XEQUE);
            if (verificarXequeMate(jogo)) {
                new DialogoService().chamarDialogoXequeMate();
                jogo.mudarStatusJogo(StatusJogo.XEQUE_MATE);
            }
        }
        else {
            jogo.mudarStatusJogo(StatusJogo.NORMAL);
        }

        jogo.getTabuleiro().imprimirTabuleiro();
    }

    private boolean validarMovimento(Jogo jogo, Peca peca,
                                     Casa casaOrigem, Casa casaDestino){
        Posicao origem = casaOrigem.getPosicao();
        Posicao destino = casaDestino.getPosicao();

        boolean movimentoValido = peca.mover(origem, destino);

        if(!movimentoValido) return false;

        if(peca.getTipo().equals(TipoPeca.PEAO))
           return verificarMovimentoDoPeao(peca, casaOrigem, casaDestino);

        if(peca.getTipo().equals(TipoPeca.REI))
            return verificarMovimentoDoRei(peca, casaOrigem, casaDestino);

        if(!peca.getTipo().equals(TipoPeca.CAVALO) && verificarColisao(jogo, origem, destino)) return false;

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

        if(comprimento == 1) {
            if (!casaDestino.estaVazia() && !casaDestino.getPeca().getCor().equals(peca.getCor()))
                return true;
            else if(casaDestino.estaVazia()) return true;
        }

        return  comprimento == 2 && peca.getQuantidadeMovimento() == 0 && distanciaY > 0 || (distanciaX == 1 &&
                distanciaY == 1);
    }

    private boolean verificarAtaqueAoRei(Jogo jogo){
        return encontrarCasaDaPecaQueAtacaORei(jogo) != null;
    }

    private Casa encontrarCasaDaPecaQueAtacaORei(Jogo jogo){
        Casa casaRei = jogo.getTabuleiro().getCasaDoRei(jogo.getCorTurnoAtual());
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        Peca pecaDoRei = casaRei.getPeca();

        if(casaRei.estaVazia()){System.out.println("Casa vazia"); return null;}

        ArrayList<Direcao> direcoes = VetorService.getDirecoes(casaRei);
        List<Posicao> posicoes = VetorService.getPosicoes(casaRei);

        for(Posicao posicao : posicoes){
           Casa casaAux = tabuleiro.getCasa(posicao.getX(), posicao.getY());
           Peca peca = casaAux.getPeca();
           if(!casaAux.estaVazia()){
               if(peca.getTipo().equals(TipoPeca.CAVALO)
                       && !peca.getCor().equals(pecaDoRei.getCor())) {
                   return casaAux;
               }
           }
        }

        for(Direcao direcao : direcoes){
            for(int comprimento=1; comprimento<=direcao.getComprimento(); comprimento++){
                Casa casaAux = tabuleiro.getCasa(casaRei.getX() + comprimento * direcao.getDirecaoX(),
                        casaRei.getY() + comprimento * direcao.getDirecaoY());

                Peca peca = casaAux.getPeca();

                if(!casaAux.estaVazia()) {
                    if ((validarMovimento(jogo, peca, casaRei, casaAux) || validarMovimento(jogo, peca, casaAux, casaRei))
                            && !peca.getCor().equals(pecaDoRei.getCor())) {
                        return casaAux;
                    }
                    else
                        break;
                }
            }
        }

        return null;
    }

    private void realizarPreMove(Jogo jogo, Peca peca, Casa casaOrigem, Casa casaDestino){
        casaOrigem.setPeca(null);
        casaOrigem.setEstaVazia(true);
        casaDestino.setPeca(peca);
        casaDestino.setEstaVazia(false);
        if(peca.getTipo().equals(TipoPeca.REI)) jogo.salvarCasaDoRei(peca, casaDestino);
    }

    private void desfazerPreMove(Jogo jogo, Peca peca, Peca pecaAnterior, Casa casaOrigem, Casa casaDestino){
        casaDestino.setPeca(pecaAnterior);

        if(casaDestino.getPeca() == null)
            casaDestino.setEstaVazia(true);

        casaOrigem.setPeca(peca);
        casaOrigem.setEstaVazia(false);

        if(peca.getTipo().equals(TipoPeca.REI)) jogo.salvarCasaDoRei(peca, casaOrigem);
    }

    private void verificarRoque(Jogo jogo, Peca peca, Casa casaDestino){
        if(!peca.getTipo().equals(TipoPeca.REI)) return;

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

        if(peca != null && peca.getQuantidadeMovimento() == 0) {
            casaOrigemTorre.setEstaVazia(true);
            casaOrigemTorre.setPeca(null);
            casaDestinoTorre.setEstaVazia(false);
            casaDestinoTorre.setPeca(peca);
        }
    }

    private boolean verificarXequeMate(Jogo jogo){
        Casa casaAtaque = encontrarCasaDaPecaQueAtacaORei(jogo);
        Casa casaRei = jogo.getTabuleiro().getCasaDoRei(jogo.getCorTurnoAtual());

        Peca pecaRei = casaRei.getPeca();

        Tabuleiro tabuleiro = jogo.getTabuleiro();
        ArrayList<Direcao> direcoes = VetorService.getDirecoes(casaRei);

        for(Direcao direcao : direcoes){
            Casa casaAux;
            int x = casaRei.getX() + direcao.getDirecaoX();
            int y = casaRei.getY() + direcao.getDirecaoY();

            if(dentroDoLimite(x, y)) casaAux = tabuleiro.getCasa(x, y);
            else continue;

            Peca pecaAnterior = casaAux.getPeca();

            if(casaAux.estaVazia()) realizarPreMove(jogo, pecaRei, casaRei, casaAux);
            else continue;

            if(verificarAtaqueAoRei(jogo)) desfazerPreMove(jogo, pecaRei, pecaAnterior, casaRei, casaAux);
            else {
                desfazerPreMove(jogo, pecaRei, pecaAnterior, casaRei, casaAux);
                return false;
            }
        }

        List<Casa> casasDePecasDeDefesa = jogo.encontrarCasasDePecasPelaCor(jogo.getCorTurnoAtual());

        VetorService vetorService = new VetorService();
        int direcaoX = vetorService.calcularDirecaoVetorEmX(casaRei.getX(), casaAtaque.getX());
        int direcaoY = vetorService.calcularDirecaoVetorEmY(casaRei.getY(), casaAtaque.getY());
        int comprimento = vetorService.calcularComprimentoVetor(casaRei.getPosicao(), casaAtaque.getPosicao());

        for(Casa casa : casasDePecasDeDefesa){
            Peca pecaDeDefesa = casa.getPeca();

            for(int c = 1; c <= comprimento; c++){
                Casa casaAux;
                int x = casaRei.getX() + direcaoX * c;
                int y = casaRei.getY() + direcaoY * c;

                if(dentroDoLimite(x, y)) casaAux = tabuleiro.getCasa(x,y);
                else continue;

                if(validarMovimento(jogo, pecaDeDefesa, casa, casaAux) && !verificarAtaqueAoRei(jogo))
                    return false;
            }
        }

        return true;
    }

    private boolean dentroDoLimite(int x, int y){
        boolean dentroEmX = (0 <= x && x <= 7);
        boolean dentroEmY = (0 <= y && y <= 7);

        return dentroEmX && dentroEmY;
    }
}

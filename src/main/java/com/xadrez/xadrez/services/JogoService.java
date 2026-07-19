package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
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

            if(peca.getTipo().equals(TipoPeca.REI)) executarMovimentoDaTorreNoRoque(jogo, casaOrigem, casaDestino);

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

        if(peca.getTipo().equals(TipoPeca.REI)) {
            if(!verificarMovimentoDoRei(peca, casaOrigem, casaDestino)) return false;

            int distanciaY = Math.abs(origem.getY() - destino.getY());
            if(distanciaY == 2) return validarRoque(jogo, casaOrigem, casaDestino);

            return true;
        }

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

        if(distanciaX <= 1 && distanciaY <= 1 && (distanciaX + distanciaY > 0)){
            if(casaDestino.estaVazia()) return true;
            return !casaDestino.getPeca().getCor().equals(peca.getCor());
        }

        return distanciaX == 0 && distanciaY == 2 && peca.getQuantidadeMovimento() == 0;
    }

    private boolean verificarAtaqueAoRei(Jogo jogo){
        return encontrarCasaDaPecaQueAtacaORei(jogo) != null;
    }

    private Casa encontrarCasaDaPecaQueAtacaORei(Jogo jogo){
        Casa casaRei = jogo.getTabuleiro().getCasaDoRei(jogo.getCorTurnoAtual());
        if(casaRei.estaVazia()) return null;

        Cor corRei = casaRei.getPeca().getCor();
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(!tabuleiro.dentroDoLimite(x, y)) continue;

                Casa casaAux = tabuleiro.getCasa(x, y);
                if(casaAux.estaVazia()) continue;

                Peca peca = casaAux.getPeca();
                if(peca.getCor().equals(corRei)) continue;

                if(pecaAtacaCasa(jogo, peca, casaAux, casaRei)) return casaAux;
            }
        }

        return null;
    }

    private boolean pecaAtacaCasa(Jogo jogo, Peca peca, Casa casaOrigem, Casa casaDestino){
        if(peca.getTipo().equals(TipoPeca.REI)){
            int distanciaX = Math.abs(casaOrigem.getX() - casaDestino.getX());
            int distanciaY = Math.abs(casaOrigem.getY() - casaDestino.getY());
            return distanciaX <= 1 && distanciaY <= 1 && (distanciaX + distanciaY > 0);
        }

        boolean formatoValido = peca.mover(casaOrigem.getPosicao(), casaDestino.getPosicao());
        if(!formatoValido) return false;

        if(peca.getTipo().equals(TipoPeca.CAVALO)) return true;

        if(peca.getTipo().equals(TipoPeca.PEAO)){
            int distanciaX = Math.abs(casaOrigem.getX() - casaDestino.getX());
            int distanciaY = Math.abs(casaOrigem.getY() - casaDestino.getY());
            if(distanciaX != 1 || distanciaY != 1) return false;

            int direcaoEsperada = peca.getCor().equals(Cor.BRANCA) ? -1 : 1;
            int direcaoReal = casaDestino.getX() - casaOrigem.getX();
            return direcaoReal == direcaoEsperada;
        }

        return !verificarColisao(jogo, casaOrigem.getPosicao(), casaDestino.getPosicao());
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

    private boolean validarRoque(Jogo jogo, Casa casaOrigem, Casa casaDestino){
        if(verificarAtaqueAoRei(jogo)) return false;

        Peca rei = casaOrigem.getPeca();
        int direcao = (casaDestino.getY() - casaOrigem.getY()) > 0 ? 1 : -1;
        Casa casaIntermediaria = jogo.getTabuleiro().getCasa(casaOrigem.getX(), casaOrigem.getY() + direcao);

        if(!casaIntermediaria.estaVazia()) return false;

        if(direcao == -1){
            Casa casaExtra = jogo.getTabuleiro().getCasa(casaOrigem.getX(), casaOrigem.getY() - 3);
            if(!casaExtra.estaVazia()) return false;
        }

        Peca pecaAnteriorIntermediaria = casaIntermediaria.getPeca();
        realizarPreMove(jogo, rei, casaOrigem, casaIntermediaria);
        boolean passaPorXeque = verificarAtaqueAoRei(jogo);
        desfazerPreMove(jogo, rei, pecaAnteriorIntermediaria, casaOrigem, casaIntermediaria);

        if(passaPorXeque) return false;

        Peca pecaAnteriorDestino = casaDestino.getPeca();
        realizarPreMove(jogo, rei, casaOrigem, casaDestino);
        boolean terminaEmXeque = verificarAtaqueAoRei(jogo);
        desfazerPreMove(jogo, rei, pecaAnteriorDestino, casaOrigem, casaDestino);

        return !terminaEmXeque;
    }

    private void executarMovimentoDaTorreNoRoque(Jogo jogo, Casa casaOrigemRei, Casa casaDestinoRei){
        int distanciaY = casaDestinoRei.getPosicao().getY() - casaOrigemRei.getPosicao().getY();
        if(Math.abs(distanciaY) != 2) return; // não foi roque

        int x = casaDestinoRei.getX();
        int yTorreOrigem = distanciaY > 0 ? 7 : 0;
        int yTorreDestino = distanciaY > 0 ? casaDestinoRei.getY() - 1 : casaDestinoRei.getY() + 1;

        Tabuleiro tabuleiro = jogo.getTabuleiro();
        Casa casaOrigemTorre = tabuleiro.getCasa(x, yTorreOrigem);
        Casa casaDestinoTorre = tabuleiro.getCasa(x, yTorreDestino);

        Peca torre = casaOrigemTorre.getPeca();

        if(torre != null && torre.getQuantidadeMovimento() == 0) {
            casaOrigemTorre.setEstaVazia(true);
            casaOrigemTorre.setPeca(null);
            casaDestinoTorre.setEstaVazia(false);
            casaDestinoTorre.setPeca(torre);
            torre.aumentarQuantidadeDeMovimentos();
        }
    }

    private boolean verificarXequeMate(Jogo jogo){
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        List<Casa> casasComPecas = jogo.encontrarCasasDePecasPelaCor(jogo.getCorTurnoAtual());

        for(Casa casaOrigem : casasComPecas){
            Peca peca = casaOrigem.getPeca();

            for(int x = 0; x < 8; x++){
                for(int y = 0; y < 8; y++){
                    if(x == casaOrigem.getX() && y == casaOrigem.getY()) continue;

                    Casa casaDestino = tabuleiro.getCasa(x, y);

                    if(!validarMovimento(jogo, peca, casaOrigem, casaDestino)) continue;

                    Peca pecaCapturada = casaDestino.getPeca();

                    realizarPreMove(jogo, peca, casaOrigem, casaDestino);
                    boolean continuaEmXeque = verificarAtaqueAoRei(jogo);
                    desfazerPreMove(jogo, peca, pecaCapturada, casaOrigem, casaDestino);

                    if(!continuaEmXeque) return false;
                }
            }
        }

        return true;
    }
}

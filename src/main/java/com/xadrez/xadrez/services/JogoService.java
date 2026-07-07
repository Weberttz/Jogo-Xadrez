package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.Tipo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class JogoService {

    public void inicializarNovoJogo(Jogo jogo) {
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();
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

        jogo.getLogs().add(formarStringDeLog(jogo, peca, casaDestino));
        jogo.mudarTurno();
        if(verificarAtaqueAoRei(jogo))
            System.out.println("Rei sendo da cor: " + jogo.getCorTurnoAtual() + " sendo atacado");
    }

    private boolean validarMovimento(Jogo jogo, Peca peca,
                                     Casa casaOrigem, Casa casaDestino){
        Posicao origem = casaOrigem.getPosicao();
        Posicao destino = casaDestino.getPosicao();

        boolean movimentoValido = peca.mover(origem, destino);

        if(!movimentoValido) return false;

        if(peca.getTipo().equals(Tipo.PEAO)){
           return verificarMovimentoDoPeao(peca, casaOrigem, casaDestino);
        }

        if(peca.getTipo().equals(Tipo.REI)) jogo.salvarCasaDoRei(peca.getCor(), casaDestino);

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
        else if (comprimento == 2 && peca.getQuantidadeMovimento() == 0 && distanciaX > 0)
            return true;
        else if(distanciaX == 1 && distanciaY == 1)
            return !casaDestino.estaVazia() && !casaDestino.getPeca().getCor().equals(peca.getCor());

        return false;
    }

    private boolean verificarAtaqueAoRei(Jogo jogo){
        Casa casa = jogo.getTabuleiro().getCasaDoRei(jogo.getCorTurnoAtual());
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        Peca pecaDoRei = casa.getPeca();

        if(casa.estaVazia()) return false;

        ArrayList<Direcao> direcoes = getDirecoes(casa);

        for(Direcao direcao : direcoes){
            for(int comprimento=1; comprimento<direcao.getComprimento(); comprimento++){
                Casa casaAux = tabuleiro.getCasa(casa.getX() + comprimento * direcao.getDirecaoX(),
                        casa.getY() + comprimento * direcao.getDirecaoY());

                Peca peca = casaAux.getPeca();

                if(!casaAux.estaVazia()) {
                    if (peca.mover(casaAux.getPosicao(), casa.getPosicao())
                            && !peca.getCor().equals(pecaDoRei.getCor())) {
                        System.out.println(peca.getNome());
                        return true;
                    }
                    else
                        break;
                }
            }
        }

        return false;
    }

    @NotNull
    private static ArrayList<Direcao> getDirecoes(Casa casa) {
        Direcao direcaoNorte = new Direcao("direcaoNorte", casa.getX(), false, -1, 0);
        Direcao direcaoSul = new Direcao("direcaoSul", 7 - casa.getX(), false, 1, 0);
        Direcao direcaoOeste = new Direcao("direcaoOeste", casa.getY(), false, 0, -1);
        Direcao direcaoLeste = new Direcao("direcaoLeste", 7 - casa.getY(), false, 0, 1);

        return new ArrayList<>(){{
            add(direcaoNorte); add(direcaoSul); add(direcaoOeste); add(direcaoLeste);
            add(new Direcao("direcaoNoroeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoOeste.getComprimento()), false, -1, -1));
            add(new Direcao("direcaoNordeste",
                    Math.min(direcaoNorte.getComprimento(), direcaoLeste.getComprimento()), false, -1, 1));
            add(new Direcao("direcaoSudoeste",
                    Math.min(direcaoSul.getComprimento(), direcaoOeste.getComprimento()), false, 1, -1));
            add(new Direcao("direcaoSudeste",
                    Math.min(direcaoSul.getComprimento(), direcaoLeste.getComprimento()), false, 1, 1));
        }};
    }

    private String formarStringDeLog(Jogo jogo, Peca peca, Casa casaDestino){
        String strLetra = peca.getNome().substring(0, 1).toUpperCase();
        String strCor = String.format("(" + peca.getCor() + ")");

        char[] colunas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String strDestino = String.format(colunas[casaDestino.getY()] + "" + (8 - casaDestino.getX()));

        String strTurno = String.format(jogo.getTurno() + " - ");

        return strTurno + strLetra + strDestino + " " + strCor;
    }
}

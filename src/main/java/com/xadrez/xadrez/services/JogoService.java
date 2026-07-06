package com.xadrez.xadrez.services;

import com.xadrez.xadrez.exceptions.MovimentoInvalidoException;
import com.xadrez.xadrez.models.classes.*;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.Tipo;
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

        if(casa.estaVazia()) return false;

        Peca peca = casa.getPeca();

        HashMap<String ,Direcao> direcoes = new HashMap<>();

        direcoes.put("direcaoNorte" ,new Direcao("direcaoNorte", casa.getX(), false));
        direcoes.put("direcaoSul" , new Direcao("direcaoSul", 7 - casa.getX(), false));
        direcoes.put("direcaoOeste", new Direcao("direcaoOeste", casa.getY(), false));
        direcoes.put("direcaoLeste", new Direcao("direcaoLeste", 7 - casa.getY(), false));

        int aux = Math.min(direcoes.get("direcaoNorte").getComprimento(), direcoes.get("direcaoOeste").getComprimento());
        direcoes.put("direcaoNoroeste" ,new Direcao("direcaoNoroeste", aux, false));
        aux = Math.min(direcoes.get("direcaoNorte").getComprimento(), direcoes.get("direcaoLeste").getComprimento());
        direcoes.put("direcaoNordeste", new Direcao("direcaoNordeste", aux, false));
        aux = Math.min(direcoes.get("direcaoSul").getComprimento(), direcoes.get("direcaoOeste").getComprimento());
        direcoes.put("direcaoSudoeste", new Direcao("direcaoSudoeste", aux, false));
        aux = Math.min(direcoes.get("direcaoSul").getComprimento(), direcoes.get("direcaoLeste").getComprimento());
        direcoes.put("direcaoSudeste", new Direcao("direcaoSudoste", aux, false));

        int comprimentoMax = direcoes.values().stream()
                .mapToInt(Direcao::getComprimento).max().orElse(0);

        Tabuleiro tabuleiro = jogo.getTabuleiro();

        int direcaoVetor = (peca.getCor().equals(Cor.BRANCA)) ? -1: 1;

        for(int i=1; i<comprimentoMax; i++){
            Casa casaAux = tabuleiro.getCasa(casa.getX() + i * direcaoVetor, casa.getY());

            if(!casaAux.estaVazia()) {
                if (casaAux.getPeca().getCor().equals(peca.getCor())) return false;
                else if (casaAux.getPeca().mover(casaAux.getPosicao(), casa.getPosicao())) return true;
            }
        }

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

    private String formarStringDeLog(Jogo jogo, Peca peca, Casa casaDestino){
        String strLetra = peca.getNome().substring(0, 1).toUpperCase();
        String strCor = String.format("(" + peca.getCor() + ")");

        char[] colunas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String strDestino = String.format(colunas[casaDestino.getY()] + "" + (8 - casaDestino.getX()));

        String strTurno = String.format(jogo.getTurno() + " - ");

        return strTurno + strLetra + strDestino + " " + strCor;
    }
}

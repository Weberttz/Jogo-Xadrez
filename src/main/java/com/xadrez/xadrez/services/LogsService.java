package com.xadrez.xadrez.services;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.StatusJogo;

public class LogsService {
    public String formarStringDeLog(Jogo jogo, Peca peca, Casa casaDestino){
        String strLetra = peca.getNome().substring(0, 1).toUpperCase();
        String strCor = String.format("(" + peca.getCor() + ")");

        char[] colunas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String strDestino = String.format(colunas[casaDestino.getY()] + "" + (8 - casaDestino.getX()));

        String strTurno = String.format(jogo.getTurno() + " - ");

        return strTurno + strLetra + strDestino + " " + strCor;
    }

    public String formarStringDeXeque(Jogo jogo){
        String cor = (jogo.getCorTurnoAtual().equals(Cor.BRANCA))? "branco" : "preto";
        jogo.mudarStatusJogo(StatusJogo.XEQUE);
        return String.format(jogo.getTurno() - 1 + " - Rei " + cor + " em Xeque!");
    }
}

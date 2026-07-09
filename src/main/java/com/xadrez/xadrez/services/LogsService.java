package com.xadrez.xadrez.services;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;

public class LogsService {
    public String formarStringDeLog(Jogo jogo, Peca peca, Casa casaDestino){
        String strLetra = peca.getNome().substring(0, 1).toUpperCase();
        String strCor = String.format("(" + peca.getCor() + ")");

        char[] colunas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String strDestino = String.format(colunas[casaDestino.getY()] + "" + (8 - casaDestino.getX()));

        String strTurno = String.format(jogo.getTurno() + " - ");

        return strTurno + strLetra + strDestino + " " + strCor;
    }
}

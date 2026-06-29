package com.xadrez.xadrez.exceptions;

import com.xadrez.xadrez.models.classes.Peao;

public class MovimentoInvalidoException extends Exception {
    public MovimentoInvalidoException(Peao peao){
        String mensagem = String.format("Movimento inválido %s", peao.getNome());
        super(mensagem);
    };
}

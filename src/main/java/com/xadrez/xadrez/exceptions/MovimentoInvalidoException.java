package com.xadrez.xadrez.exceptions;

import com.xadrez.xadrez.models.classes.Peca;

public class MovimentoInvalidoException extends Exception {
    public MovimentoInvalidoException(Peca peca){
        String mensagem = String.format("Movimento inválido %s", peca.getNome());
        super(mensagem);
    };
}

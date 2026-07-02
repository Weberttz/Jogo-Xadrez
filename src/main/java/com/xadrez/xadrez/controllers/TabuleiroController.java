package com.xadrez.xadrez.controllers;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.StatusClique;
import com.xadrez.xadrez.services.JogoService;
import com.xadrez.xadrez.views.TabuleiroView;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.util.Objects;

public class TabuleiroController {

    @FXML
    private GridPane tabuleiroGrid;
    private JogoService jogoService;
    private TabuleiroView tabuleiroView;
    private Jogo jogo;
    private StatusClique statusClique;

    private Casa casaOrigem = null;

    @FXML
    public void initialize(){
        this.statusClique = StatusClique.NAO_CLICOU;
        this.jogo = new Jogo();
        this.jogoService = new JogoService();
        this.jogoService.inicializarNovoJogo(jogo);

        this.tabuleiroView = new TabuleiroView(tabuleiroGrid ,jogo);
        this.tabuleiroView.configurarDimensoesGrid();
        this.tabuleiroView.criarTabuleiro();
        this.tabuleiroView.atualizarTabuleiro();

        this.tabuleiroView.setOnCasaClicada(this::processarClique);
    }

    public void processarClique(int linha, int coluna){

        System.out.println("Clicou em " + linha + " " + coluna);

        Casa casa = jogo.getTabuleiro().getCasa(linha, coluna);

        if(statusClique.equals(StatusClique.NAO_CLICOU) && !casa.estaVazia()){
             Cor corPecaClicada = casa.getPeca().getCor();
             if(corPecaClicada.equals(jogo.getCorTurnoAtual())) {
                 casaOrigem = casa;
                 statusClique = StatusClique.CLICOU;
                 System.out.println("peça teclada!");
                 return;
             }
        }

        Cor corPecaEscolhida = null;
        if(!casa.estaVazia()) {
            corPecaEscolhida = casa.getPeca().getCor();
        }

        if ((casa.estaVazia() || !Objects.equals(corPecaEscolhida, jogo.getCorTurnoAtual()))
                    && statusClique.equals(StatusClique.CLICOU)) {
                jogoService.jogarTurno(jogo, casaOrigem, casa);
                statusClique = StatusClique.NAO_CLICOU;
                casaOrigem = null;
        }


        tabuleiroView.atualizarTabuleiro();
        //jogo.getTabuleiro().imprimirTabuleiro();
    }
}

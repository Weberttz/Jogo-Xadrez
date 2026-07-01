package com.xadrez.xadrez.controllers;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.classes.Posicao;
import com.xadrez.xadrez.services.JogoService;
import com.xadrez.xadrez.views.TabuleiroView;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class TabuleiroController {

    @FXML
    private GridPane tabuleiroGrid;
    private JogoService jogoService;
    private TabuleiroView tabuleiroView;
    private Jogo jogo;

    private Casa casaOrigem = null;

    @FXML
    public void initialize(){
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

        if(casaOrigem == null) {
            casaOrigem = casa;
            return;
        }

        if(casa.estaVazia()) {
            jogoService.jogarTurno(jogo, casaOrigem, casa);
            casaOrigem = null;
        }

        tabuleiroView.atualizarTabuleiro();
        jogo.getTabuleiro().imprimirTabuleiro();
    }
}

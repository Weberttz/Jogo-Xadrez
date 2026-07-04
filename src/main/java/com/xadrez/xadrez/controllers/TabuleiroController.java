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
        Casa casa = jogo.getTabuleiro().getCasa(linha, coluna);

        if (statusClique.equals(StatusClique.NAO_CLICOU)) {
            if (jogoService.podeSelecionar(jogo, casa)) {
                casaOrigem = casa;
                statusClique = StatusClique.CLICOU;
            }
        } else {
            informar(casaOrigem, casa);
            jogoService.jogarTurno(jogo, casaOrigem, casa);
            statusClique = StatusClique.NAO_CLICOU;
            casaOrigem = null;
        }

        tabuleiroView.atualizarTabuleiro();
    }

    private void informar(Casa casaOrigem, Casa casaDestino){
        System.out.println(casaOrigem.getPeca().getNome() + " de: " + casaOrigem.getX() + "," + casaOrigem.getY() +
                " para: " + casaDestino.getX() + "," + casaDestino.getY());
    }
}

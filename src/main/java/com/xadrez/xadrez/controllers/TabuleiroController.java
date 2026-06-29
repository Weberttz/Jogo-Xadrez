package com.xadrez.xadrez.controllers;

import com.xadrez.xadrez.models.enums.Cores;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class TabuleiroController {

    @FXML
    private GridPane tabuleiroGrid;

    private StackPane[][] matrizCasas = new StackPane[8][8];

    @FXML
    public void initialize(){
        configurarDimensoesGrid();
        criarTabuleiro(tabuleiroGrid);
    }

    private void configurarDimensoesGrid() {
        tabuleiroGrid.setMinSize(400, 400);
        // Força as 8 colunas e 8 linhas a dividirem o espaço igualmente
        for (int i = 0; i < 8; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS); // Estica na horizontal
            tabuleiroGrid.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS); // Estica na vertical
            tabuleiroGrid.getRowConstraints().add(rc);
        }
    }

    private void criarTabuleiro(GridPane tabuleiroGrid){
        for(int linha=0; linha<8; linha++){
            for(int coluna = 0; coluna < 8; coluna++){
                StackPane casa = new StackPane();

                String cor = ((linha + coluna) % 2 == 0)? Cores.PRETA.getEstilo() : Cores.BRANCA.getEstilo();
                casa.setStyle("-fx-background-color: " + cor + ";");

                final int l = linha;
                final int c = coluna;

                casa.setOnMouseClicked(mouseEvent -> tratarMouseClique(l, c));

                matrizCasas[coluna][linha] = casa;
                tabuleiroGrid.add(casa, coluna, linha);
            }
        }
    }

    private void tratarMouseClique(int linha, int coluna){
        System.out.println("Clicou em " + linha + " " + coluna);
    }
}

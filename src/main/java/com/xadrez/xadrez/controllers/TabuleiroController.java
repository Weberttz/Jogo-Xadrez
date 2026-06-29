package com.xadrez.xadrez.controllers;

import com.xadrez.xadrez.models.classes.Casa;
import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.enums.Cor;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public class TabuleiroController {

    @FXML
    private GridPane tabuleiroGrid;

    private StackPane[][] matrizCasas = new StackPane[8][8];

    private Jogo jogo;

    @FXML
    public void initialize(){
        this.jogo = new Jogo();
        configurarDimensoesGrid();
        criarTabuleiro(tabuleiroGrid);
    }

    private void configurarDimensoesGrid() {
        tabuleiroGrid.setMinSize(480, 480);
        // Força as 8 colunas e 8 linhas a dividirem o espaço igualmente
        for (int i = 0; i < 8; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS); // Estica na horizontal
            tabuleiroGrid.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS); // Estica na vertical
            tabuleiroGrid.getRowConstraints().add(rc);
        }
        tabuleiroGrid.setHgap(0); // Remove espaço horizontal entre as casas
        tabuleiroGrid.setVgap(0); // Remove espaço vertical entre as casas
        // Centraliza o tabuleiro inteiro na tela
        tabuleiroGrid.setAlignment(Pos.CENTER);
    }


    private void criarTabuleiro(GridPane tabuleiroGrid){
        jogo.getTabuleiro().inicializarTabuleiro();
        jogo.getTabuleiro().colocarPecas();

        for(int linha=0; linha<8; linha++){
            for(int coluna = 0; coluna < 8; coluna++){
                StackPane casa = new StackPane();
                casa.setPrefSize(60, 60);
                casa.setMinSize(60, 60);
                casa.setMaxSize(60, 60);

                Peca peca = jogo.getTabuleiro().getCasa(linha, coluna).getPeca();

                String cor = ((linha + coluna) % 2 == 0)? Cor.PRETA.getEstilo() : Cor.BRANCA.getEstilo();
                casa.setStyle("-fx-background-color: " + cor + ";");

                if(peca != null) {
                    String nomePeca = peca.getNome().toLowerCase();
                    String corPeca = (peca.getCor().name().equals(Cor.BRANCA.name())) ? "branco" : "preto";

                    Image imagemPeca = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().
                            getResourceAsStream("/imagens/" + nomePeca + "-" + corPeca + ".png"))));
                    ImageView imageViewPeca = new ImageView(imagemPeca);

                    imageViewPeca.setFitWidth(50);
                    imageViewPeca.setFitHeight(50);
                    imageViewPeca.setPreserveRatio(true);
                    casa.getChildren().add(imageViewPeca);
                }

                final int l = linha;
                final int c = coluna;

                casa.setOnMouseClicked(mouseEvent -> tratarMouseClique(l, c));

                matrizCasas[coluna][linha] = casa;
                tabuleiroGrid.add(casa, coluna, linha);
                GridPane.setHalignment(casa, HPos.CENTER);
                GridPane.setValignment(casa, VPos.CENTER);
            }
        }
    }

    private void tratarMouseClique(int linha, int coluna){
        Casa casa = jogo.getTabuleiro().getCasa(linha, coluna);
        System.out.println("Clicou em " + casa.getPeca().getNome().charAt(0));
    }
}

package com.xadrez.xadrez.views;

import com.xadrez.xadrez.models.classes.Jogo;
import com.xadrez.xadrez.models.classes.Peca;
import com.xadrez.xadrez.models.enums.Cor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.Objects;
import java.util.function.BiConsumer;

public class TabuleiroView {

    private GridPane tabuleiroGrid;
    private ListView<String> logListView;
    private StackPane[][] matrizCasas;
    private Label labelJogadorAtual;
    private Jogo jogo;

    private BiConsumer<Integer, Integer> onCasaClicada;

    public TabuleiroView(GridPane tabuleiroGrid, ListView<String> logListView, Label
            labelJogadorAtual, Jogo jogo){
        this.jogo = jogo;
        this.tabuleiroGrid = tabuleiroGrid;
        this.matrizCasas = new StackPane[8][8];
        this.logListView = logListView;
        this.labelJogadorAtual = labelJogadorAtual;

        ObservableList<String> logs = FXCollections.observableArrayList();
        this.logListView.setItems(logs);
    }

    public void configurarDimensoesGrid() {
        tabuleiroGrid.setMinSize(480, 480);
        tabuleiroGrid.setPrefSize(480, 480);
        tabuleiroGrid.setMaxSize(480, 480);

        for (int i = 0; i < 8; i++) {
            ColumnConstraints cc = new ColumnConstraints(60);
            tabuleiroGrid.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints(60);
            tabuleiroGrid.getRowConstraints().add(rc);
        }
        tabuleiroGrid.setHgap(0); // Remove espaço horizontal entre as casas
        tabuleiroGrid.setVgap(0); // Remove espaço vertical entre as casas
        tabuleiroGrid.setAlignment(Pos.CENTER);
    }

    public void criarTabuleiro(){
        for(int linha=0; linha<8; linha++){
            for(int coluna = 0; coluna < 8; coluna++){
                StackPane casa = new StackPane();
                casa.setPrefSize(60, 60);
                casa.setMinSize(60, 60);
                casa.setMaxSize(60, 60);

                String cor = ((linha + coluna) % 2 == 0)? Cor.BRANCA.getEstilo() : Cor.PRETA.getEstilo();
                casa.setStyle("-fx-background-color: " + cor + ";");

                final int l = linha;
                final int c = coluna;

                casa.setOnMouseClicked(mouseEvent -> {
                    if (onCasaClicada != null) {
                        onCasaClicada.accept(l, c);
                    }
                });

                matrizCasas[linha][coluna] = casa;
                tabuleiroGrid.add(casa, coluna, linha);
                GridPane.setHalignment(casa, HPos.CENTER);
                GridPane.setValignment(casa, VPos.CENTER);
            }
        }
    }

    public void atualizarTabuleiro(){
        for(int linha = 0; linha<8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                StackPane casa = matrizCasas[linha][coluna];
                Peca peca = jogo.getTabuleiro().getCasa(linha, coluna).getPeca();

                casa.getChildren().removeIf(node -> node instanceof ImageView);

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
            }
        }
        atualizarLabel();
        atualizarLista();
    }

    private void atualizarLabel(){
        labelJogadorAtual.setText("Vez de: " + jogo.getJogadorAtual().getNome());
    }

    private void atualizarLista(){
        ObservableList<String> logs = FXCollections.observableArrayList(jogo.getLogs());
        logListView.setItems(logs);
    }

    public void setOnCasaClicada(BiConsumer<Integer, Integer> onCasaClicada) {
        this.onCasaClicada = onCasaClicada;
    }
}

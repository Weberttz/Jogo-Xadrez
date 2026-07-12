package com.xadrez.xadrez.services;

import com.xadrez.xadrez.models.enums.TipoPeca;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class DialogoService {

    public TipoPeca escolherPromocao() {
        List<String> opcoes = List.of("Rainha", "Torre", "Bispo", "Cavalo");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(opcoes.get(0), opcoes);
        dialog.setTitle("Promoção de peão");
        dialog.setHeaderText("Escolha a peça para promoção");
        dialog.setContentText("Peça:");

        Optional<String> resultado = dialog.showAndWait();

        return resultado.map(this::converterParaTipoPeca)
                .orElse(TipoPeca.RAINHA);
    }

    private TipoPeca converterParaTipoPeca(String escolha) {
        return switch (escolha) {
            case "Torre" -> TipoPeca.TORRE;
            case "Bispo" -> TipoPeca.BISPO;
            case "Cavalo" -> TipoPeca.CAVALO;
            default -> TipoPeca.RAINHA;
        };
    }

    public void chamarDialogoXequeMate(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Xeque-Mate");
        alerta.setHeaderText("Jogo Finalizado");
        alerta.setContentText("Acabou");
        alerta.showAndWait();
    }
}

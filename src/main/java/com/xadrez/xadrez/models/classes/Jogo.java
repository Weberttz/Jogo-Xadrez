package com.xadrez.xadrez.models.classes;

import com.xadrez.xadrez.models.enums.Cor;
import com.xadrez.xadrez.models.enums.StatusJogo;

import java.util.ArrayList;
import java.util.List;

public class Jogo {

    private final ArrayList<Jogador> jogadores;
    private final ArrayList<String> logs;
    private final Tabuleiro tabuleiro;
    private Cor corTurnoAtual;
    private Jogador jogadorAtual;
    private int turno;
    private StatusJogo statusJogo;

    public Jogo(){
        this.turno = 1;
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();
        this.statusJogo = StatusJogo.NORMAL;

        this.jogadores.add(new Jogador("Brancas", Cor.BRANCA));
        this.jogadores.add(new Jogador("Pretas", Cor.PRETA));

        this.jogadorAtual = jogadores.getFirst();
        this.corTurnoAtual = jogadorAtual.getCor();
        this.logs = new ArrayList<>();
    }

    public void mudarTurno(){
        this.corTurnoAtual = (corTurnoAtual == Cor.BRANCA)? Cor.PRETA : Cor.BRANCA;
        if(corTurnoAtual.equals(Cor.BRANCA))
            this.jogadorAtual = jogadores.getFirst();
        else
            this.jogadorAtual = jogadores.getLast();

        this.turno++;
    }

    public void salvarCasaDoRei(Peca pecaRei, Casa casaDestino){
        if(pecaRei.getCor().equals(Cor.BRANCA)) this.tabuleiro.setCasasDosReis(0, casaDestino);
        else this.tabuleiro.setCasasDosReis(1, casaDestino);
    }

    public List<Casa> encontrarCasasDePecasPelaCor(Cor cor){
        int dimensao = 8;
        List<Casa> listaDeCasas = new ArrayList<>();

        for(int linha = 0; linha < dimensao; linha++){
            for(int coluna = 0; coluna < dimensao; coluna++){
                Casa casa = tabuleiro.getCasa(linha, coluna);

                if(!casa.estaVazia() && casa.getPeca().getCor().equals(cor))
                        listaDeCasas.add(casa);
            }
        }

        return listaDeCasas;
    }

    public void mudarStatusJogo(StatusJogo statusJogo){
        this.statusJogo = statusJogo;
    }

    public Jogador getJogadorAtual(){return this.jogadorAtual;}
    public Tabuleiro getTabuleiro() {return this.tabuleiro;}
    public Cor getCorTurnoAtual(){return this.corTurnoAtual;}
    public ArrayList<String> getLogs(){ return this.logs;}
    public int getTurno(){return this.turno;}
    public StatusJogo getStatusJogo(){ return this.statusJogo;}
}

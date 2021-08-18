package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class BolosAdicionadosVitrineDiarioModel implements Serializable {

    private String id;
    private String data;
    private String nomeBoloAdicionado;
    private int quantBolosAdicionados;
    private double custoTotalBolosAdicionados;
    private double valorTotalVendaBolosAdicionados;


    public BolosAdicionadosVitrineDiarioModel() {
    }

    public BolosAdicionadosVitrineDiarioModel(String id, String nomeBoloAdicionado, int quantBolosAdicionados, double custoTotalBolosAdicionados, double valorTotalVendaBolosAdicionados, String data) {

        this.id = id;
        this.nomeBoloAdicionado = nomeBoloAdicionado;
        this.quantBolosAdicionados = quantBolosAdicionados;
        this.custoTotalBolosAdicionados = custoTotalBolosAdicionados;
        this.valorTotalVendaBolosAdicionados = valorTotalVendaBolosAdicionados;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeBoloAdicionado() {
        return nomeBoloAdicionado;
    }

    public void setNomeBoloAdicionado(String nomeBoloAdicionado) {
        this.nomeBoloAdicionado = nomeBoloAdicionado;
    }

    public int getQuantBolosAdicionados() {
        return quantBolosAdicionados;
    }

    public void setQuantBolosAdicionados(int quantBolosAdicionados) {
        this.quantBolosAdicionados = quantBolosAdicionados;
    }

    public double getCustoTotalBolosAdicionados() {
        return custoTotalBolosAdicionados;
    }

    public void setCustoTotalBolosAdicionados(double custoTotalBolosAdicionados) {
        this.custoTotalBolosAdicionados = custoTotalBolosAdicionados;
    }

    public double getValorTotalVendaBolosAdicionados() {
        return valorTotalVendaBolosAdicionados;
    }

    public void setValorTotalVendaBolosAdicionados(double valorTotalVendaBolosAdicionados) {
        this.valorTotalVendaBolosAdicionados = valorTotalVendaBolosAdicionados;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

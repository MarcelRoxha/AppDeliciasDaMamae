package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class BolosAdicionadosVitrine implements Serializable {

    private String identificador;
    private String nomeBolo;
    private String enderecoFotoSalva;
    private double valorVenda;
    private double custoBoloAdicionado;


    public BolosAdicionadosVitrine() {
    }

    public BolosAdicionadosVitrine(String id, String nomeBolo, String enderecoFotoSalva, double valorVenda,  double custoBoloAdicionado) {
        this.identificador = id;
        this.nomeBolo = nomeBolo;
        this.enderecoFotoSalva = enderecoFotoSalva;
        this.valorVenda = valorVenda;
        this.custoBoloAdicionado = custoBoloAdicionado;
    }

    public String getId() {
        return identificador;
    }

    public void setId(String id) {
        this.identificador = id;
    }

    public String getNomeBolo() {
        return nomeBolo;
    }

    public void setNomeBolo(String nomeBolo) {
        this.nomeBolo = nomeBolo;
    }

    public String getEnderecoFotoSalva() {
        return enderecoFotoSalva;
    }

    public void setEnderecoFotoSalva(String enderecoFotoSalva) {
        this.enderecoFotoSalva = enderecoFotoSalva;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public double getCustoBoloAdicionado() {
        return custoBoloAdicionado;
    }

    public void setCustoBoloAdicionado(double custoBoloAdicionado) {
        this.custoBoloAdicionado = custoBoloAdicionado;
    }
}

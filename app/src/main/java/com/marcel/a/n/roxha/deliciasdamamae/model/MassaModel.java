package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class MassaModel implements Serializable {

    private String id;
    private String nomeMassa;
    private int quantRendeMassa;
    private double custoMassa;
    private double valorVendaMassa;
    private int quantMassaLojaVenda;

    public MassaModel() {
    }

    public MassaModel(String id, String nomeMassa, int quantRendeMassa, double custoMassa, double valorVendaMassa, int quantMassaLojaVenda) {
        this.id = id;
        this.nomeMassa = nomeMassa;
        this.quantRendeMassa = quantRendeMassa;
        this.custoMassa = custoMassa;
        this.valorVendaMassa = valorVendaMassa;
        this.quantMassaLojaVenda = quantMassaLojaVenda;
    }



}

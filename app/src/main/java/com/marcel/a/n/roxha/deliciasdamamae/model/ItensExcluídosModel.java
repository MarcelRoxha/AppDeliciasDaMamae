package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ItensExcluídosModel implements Serializable {


    //Variaveis CAIXA_MENSAL:
    private String idCaixaMensal;
    private int mesReferencia;
    private int quantTotalBoloAdicionadoMensal;
    private double totalGastoMensal;
    private double valorTotalBolosVendidosMensal;
    private double valorTotalCustoBoloVendidoMensal;

    //Variaveis Item_Estoque:
    private String nomeItem;
    private String quantItem;
    private String quantPacote;
    private String quantUsadoReceita;
    private String unidMedida;
    private String unidReceita;
    private String valorFracionado;
    private String valorItem;
    private String valorItemPorReceita;
    private String versionEstoque;

    //Variaveis Receitas_complestas:
    private String idReceita;
    private String nomeReceita;
    private String quantRendimentoReceita;
    private String valorTotalReceita;

    //Variaveis Bolos_Cadastrados_venda:
    private String custoBolo;
    private String enderecoFoto;
    private String nomeBolo;
    private String quantBoloVenda;
    private String valorVenda;
    private String verificaCameraGaleria;

    //Variaveis BOLOS_VENDIDOS;
    private double custoBoloVendido;
    private String idReferenciaBoloVendido;
    private String nomeBoloVendido;
    private double valorVendaBoloVendido;

    //Variaveis BOLOS_EXPOSTOS_VITRINE:
    private String identificadorBoloExpostoVitrine;
    private String nomeBoloExpostoVitrine;
    private String enderecoFotoSalvaExpostoVitrine;
    private double valorVendaBoloExpostoVitrine;
    private double custoBoloAdicionadoExpostoVitrine;

    public ItensExcluídosModel() {
    }

    public String getIdCaixaMensal() {
        return idCaixaMensal;
    }

    public void setIdCaixaMensal(String idCaixaMensal) {
        this.idCaixaMensal = idCaixaMensal;
    }

    public int getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(int mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public int getQuantTotalBoloAdicionadoMensal() {
        return quantTotalBoloAdicionadoMensal;
    }

    public void setQuantTotalBoloAdicionadoMensal(int quantTotalBoloAdicionadoMensal) {
        this.quantTotalBoloAdicionadoMensal = quantTotalBoloAdicionadoMensal;
    }

    public double getTotalGastoMensal() {
        return totalGastoMensal;
    }

    public void setTotalGastoMensal(double totalGastoMensal) {
        this.totalGastoMensal = totalGastoMensal;
    }

    public double getValorTotalBolosVendidosMensal() {
        return valorTotalBolosVendidosMensal;
    }

    public void setValorTotalBolosVendidosMensal(double valorTotalBolosVendidosMensal) {
        this.valorTotalBolosVendidosMensal = valorTotalBolosVendidosMensal;
    }

    public double getValorTotalCustoBoloVendidoMensal() {
        return valorTotalCustoBoloVendidoMensal;
    }

    public void setValorTotalCustoBoloVendidoMensal(double valorTotalCustoBoloVendidoMensal) {
        this.valorTotalCustoBoloVendidoMensal = valorTotalCustoBoloVendidoMensal;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getQuantItem() {
        return quantItem;
    }

    public void setQuantItem(String quantItem) {
        this.quantItem = quantItem;
    }

    public String getQuantPacote() {
        return quantPacote;
    }

    public void setQuantPacote(String quantPacote) {
        this.quantPacote = quantPacote;
    }

    public String getQuantUsadoReceita() {
        return quantUsadoReceita;
    }

    public void setQuantUsadoReceita(String quantUsadoReceita) {
        this.quantUsadoReceita = quantUsadoReceita;
    }

    public String getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }

    public String getUnidReceita() {
        return unidReceita;
    }

    public void setUnidReceita(String unidReceita) {
        this.unidReceita = unidReceita;
    }

    public String getValorFracionado() {
        return valorFracionado;
    }

    public void setValorFracionado(String valorFracionado) {
        this.valorFracionado = valorFracionado;
    }

    public String getValorItem() {
        return valorItem;
    }

    public void setValorItem(String valorItem) {
        this.valorItem = valorItem;
    }

    public String getValorItemPorReceita() {
        return valorItemPorReceita;
    }

    public void setValorItemPorReceita(String valorItemPorReceita) {
        this.valorItemPorReceita = valorItemPorReceita;
    }

    public String getVersionEstoque() {
        return versionEstoque;
    }

    public void setVersionEstoque(String versionEstoque) {
        this.versionEstoque = versionEstoque;
    }

    public String getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(String idReceita) {
        this.idReceita = idReceita;
    }

    public String getNomeReceita() {
        return nomeReceita;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita;
    }

    public String getQuantRendimentoReceita() {
        return quantRendimentoReceita;
    }

    public void setQuantRendimentoReceita(String quantRendimentoReceita) {
        this.quantRendimentoReceita = quantRendimentoReceita;
    }

    public String getValorTotalReceita() {
        return valorTotalReceita;
    }

    public void setValorTotalReceita(String valorTotalReceita) {
        this.valorTotalReceita = valorTotalReceita;
    }

    public String getCustoBolo() {
        return custoBolo;
    }

    public void setCustoBolo(String custoBolo) {
        this.custoBolo = custoBolo;
    }

    public String getEnderecoFoto() {
        return enderecoFoto;
    }

    public void setEnderecoFoto(String enderecoFoto) {
        this.enderecoFoto = enderecoFoto;
    }

    public String getNomeBolo() {
        return nomeBolo;
    }

    public void setNomeBolo(String nomeBolo) {
        this.nomeBolo = nomeBolo;
    }

    public String getQuantBoloVenda() {
        return quantBoloVenda;
    }

    public void setQuantBoloVenda(String quantBoloVenda) {
        this.quantBoloVenda = quantBoloVenda;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getVerificaCameraGaleria() {
        return verificaCameraGaleria;
    }

    public void setVerificaCameraGaleria(String verificaCameraGaleria) {
        this.verificaCameraGaleria = verificaCameraGaleria;
    }

    public double getCustoBoloVendido() {
        return custoBoloVendido;
    }

    public void setCustoBoloVendido(double custoBoloVendido) {
        this.custoBoloVendido = custoBoloVendido;
    }

    public String getIdReferenciaBoloVendido() {
        return idReferenciaBoloVendido;
    }

    public void setIdReferenciaBoloVendido(String idReferenciaBoloVendido) {
        this.idReferenciaBoloVendido = idReferenciaBoloVendido;
    }

    public String getNomeBoloVendido() {
        return nomeBoloVendido;
    }

    public void setNomeBoloVendido(String nomeBoloVendido) {
        this.nomeBoloVendido = nomeBoloVendido;
    }

    public double getValorVendaBoloVendido() {
        return valorVendaBoloVendido;
    }

    public void setValorVendaBoloVendido(double valorVendaBoloVendido) {
        this.valorVendaBoloVendido = valorVendaBoloVendido;
    }

    public String getIdentificadorBoloExpostoVitrine() {
        return identificadorBoloExpostoVitrine;
    }

    public void setIdentificadorBoloExpostoVitrine(String identificadorBoloExpostoVitrine) {
        this.identificadorBoloExpostoVitrine = identificadorBoloExpostoVitrine;
    }

    public String getNomeBoloExpostoVitrine() {
        return nomeBoloExpostoVitrine;
    }

    public void setNomeBoloExpostoVitrine(String nomeBoloExpostoVitrine) {
        this.nomeBoloExpostoVitrine = nomeBoloExpostoVitrine;
    }

    public String getEnderecoFotoSalvaExpostoVitrine() {
        return enderecoFotoSalvaExpostoVitrine;
    }

    public void setEnderecoFotoSalvaExpostoVitrine(String enderecoFotoSalvaExpostoVitrine) {
        this.enderecoFotoSalvaExpostoVitrine = enderecoFotoSalvaExpostoVitrine;
    }

    public double getValorVendaBoloExpostoVitrine() {
        return valorVendaBoloExpostoVitrine;
    }

    public void setValorVendaBoloExpostoVitrine(double valorVendaBoloExpostoVitrine) {
        this.valorVendaBoloExpostoVitrine = valorVendaBoloExpostoVitrine;
    }

    public double getCustoBoloAdicionadoExpostoVitrine() {
        return custoBoloAdicionadoExpostoVitrine;
    }

    public void setCustoBoloAdicionadoExpostoVitrine(double custoBoloAdicionadoExpostoVitrine) {
        this.custoBoloAdicionadoExpostoVitrine = custoBoloAdicionadoExpostoVitrine;
    }
}

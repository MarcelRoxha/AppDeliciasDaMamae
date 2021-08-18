package com.marcel.a.n.roxha.deliciasdamamae.model;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ItemEstoqueDAO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*import com.marcel.a.n.roxha.deliciasdamamae.helper.Base64Custom;*/

public class ItemEstoqueModel implements Serializable {

//Atributos classe Item Estoque Model
    String id;
    String nameItem;
    String valorItem;
    String quantItem;
    String unidMedida;
    String unidReceita;
    String valorFracionado;
    String valorItemPorReceita;
    String quantPacote;
    String quantUsadaReceita;
//Atributo final para fazer alterações em massa
    final String IdEstoque = "Estoque_DeliciasDaMamae";

    Context context;


//Construtor vazio
    public ItemEstoqueModel() {

    }

//Construtor completo
    public ItemEstoqueModel(String nameItem, String valorItem, String quantItem, String unidMedida, String unidReceita){

            this.nameItem = nameItem;
            this.valorItem = valorItem;
            this.quantItem = quantItem;
            this.unidMedida = unidMedida;
            this.unidReceita = unidReceita;
}
//------------------------------>Getters and Setters<-------------------------------START

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionEstoque() {
        return IdEstoque;
    }





    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getValorItem() {
        return valorItem;
    }

    public void setValorItem(String valorItem) {
        this.valorItem = valorItem;
    }

    public String getQuantItem() {
        return quantItem;
    }

    public void setQuantItem(String quantItem) {
        this.quantItem = quantItem;
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

    public String getQuantPacote() {
        return quantPacote;
    }

    public String getQuantUsadaReceita() {
        return quantUsadaReceita;
    }

    public void setQuantUsadaReceita(String quantUsadaReceita) {
        this.quantUsadaReceita = quantUsadaReceita;
    }

    public void setQuantPacote(String quantPacote) {
        this.quantPacote = quantPacote;
    }

    public String getCalcularValorFracionado() {
        return valorFracionado;
    }
    public String getValorItemPorReceita() {
        return valorItemPorReceita;
    }

    //------------------------------>Getters and Setters<-------------------------------END


    //Methodo para calcular o valor Fracionado por item:
    public String calcularValorFracionado() {


        double valor = Double.parseDouble(getValorItem());
        double quantidadePct = Double.parseDouble(getQuantPacote());
        double unidMedida = Double.parseDouble(getUnidMedida());

        double totalPorPacote = quantidadePct * unidMedida;
        double resultado = Double.valueOf(valor / totalPorPacote);

        valorFracionado = String.valueOf(resultado);

        return valorFracionado;
    }


    //Methodo para calcular o valor do item por receita de acordo com as informações passadas:
    public String valorItemPorReceita() {


        double valorFracionado = Double.parseDouble(calcularValorFracionado());
        double quantUsadaReceita = Double.parseDouble(getQuantUsadaReceita());

        double result = valorFracionado * quantUsadaReceita;

        valorItemPorReceita = String.valueOf(result);

        return valorItemPorReceita;
    }



}

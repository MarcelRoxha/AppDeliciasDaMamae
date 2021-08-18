package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.NovaReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class IngredienteModel implements Serializable {


    String identificadorIngredienteReceita;
    String nomeIngrediente;
    String valorIngredienteReceita;
    String quantUsadaReceitaIngrediente;


    public IngredienteModel() {
    }

    public IngredienteModel(String identificadorIngredienteReceita, String nomeIngrediente, String valorIngredienteReceita, String quantUsadaReceitaIngrediente) {
        this.identificadorIngredienteReceita = identificadorIngredienteReceita;
        this.nomeIngrediente = nomeIngrediente;
        this.valorIngredienteReceita = valorIngredienteReceita;
        this.quantUsadaReceitaIngrediente = quantUsadaReceitaIngrediente;
    }

    public String getIdentificadorIngredienteReceita() {
        return identificadorIngredienteReceita;
    }

    public void setIdentificadorIngredienteReceita(String identificadorIngredienteReceita) {
        this.identificadorIngredienteReceita = identificadorIngredienteReceita;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public String getValorIngredienteReceita() {
        return valorIngredienteReceita;
    }

    public void setValorIngredienteReceita(String valorIngredienteReceita) {
        this.valorIngredienteReceita = valorIngredienteReceita;
    }

    public String getQuantUsadaReceitaIngrediente() {
        return quantUsadaReceitaIngrediente;
    }

    public void setQuantUsadaReceitaIngrediente(String quantUsadaReceitaIngrediente) {
        this.quantUsadaReceitaIngrediente = quantUsadaReceitaIngrediente;
    }


    public void salvarIngredienteReceita(IngredienteModel ingredienteModel){



    }
}

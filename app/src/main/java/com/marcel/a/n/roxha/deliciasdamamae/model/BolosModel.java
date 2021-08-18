package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BolosModel implements Serializable {

    private String nomeBolo;
    private String custoBolo;
    private String valorVenda;
    private String quantBoloVenda;
    private String enderecoFoto;
    private int verificaCameraGaleria;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private String referenciaFotoStorege;
    public  final static String NOME_CHAVE_REFERENCE = "Bolos_Feitos";

    public BolosModel() {
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

    public String getCustoBolo() {
        return custoBolo;
    }

    public void setCustoBolo(String custoBolo) {
        this.custoBolo = custoBolo;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getQuantBoloVenda() {
        return quantBoloVenda;
    }

    public void setQuantBoloVenda(String quantBoloVenda) {
        this.quantBoloVenda = quantBoloVenda;
    }

    public int getVerificaCameraGaleria() {
        return verificaCameraGaleria;
    }

    public void setVerificaCameraGaleria(int verificaCameraGaleria) {
        this.verificaCameraGaleria = verificaCameraGaleria;
    }

    public void salvarBolo(){

       reference = db.collection(NOME_CHAVE_REFERENCE);

        Map<String, Object> boloFeitoSalvo = new HashMap<>();
        boloFeitoSalvo.put("nomeBolo", getNomeBolo());
        boloFeitoSalvo.put("custoBolo", getCustoBolo());
        boloFeitoSalvo.put("valorVenda", getValorVenda());
        boloFeitoSalvo.put("quantBoloVenda", getQuantBoloVenda());

        reference.add(boloFeitoSalvo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public String getReferenciaFotoStorege() {
        return referenciaFotoStorege;
    }

    public void setReferenciaFotoStorege(String referenciaFotoStorege) {
        this.referenciaFotoStorege = referenciaFotoStorege;
    }
}

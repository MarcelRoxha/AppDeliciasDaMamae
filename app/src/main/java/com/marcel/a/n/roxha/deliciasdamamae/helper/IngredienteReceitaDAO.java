package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

import java.util.Collections;

public class IngredienteReceitaDAO implements InterfaceIngredienteReceitaDAO {

    /*Conexão firebase (Banco de dados)*/
    FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceReceita = db.collection("Receitas_completas");


    /*Variaveis para tratar os valores do front-end <-*/
    private String idRecuperadoReceita = null;
    private String idRecuperadoIngrediente = null;
    private String nomeReceitaRecuperado = null;

    //->Receita completa firebase
    private String nomeReceitaBanco;


    private boolean resultadoVerifica;


    /*Construtor*/
    public Context context;

    public IngredienteReceitaDAO (Context context){
        this.context = context;
    }


    @Override
    public boolean adicionarIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ItemEstoqueModel itemEstoqueModel) {



        return false;
    }

    @Override
    public boolean removerIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ItemEstoqueModel itemEstoqueModel) {


        idRecuperadoReceita = idReceitaCompleta;
        idRecuperadoIngrediente = idIngrediente;
        nomeReceitaRecuperado = nomReceita;

        if(idReceitaCompleta != null){

            referenceReceita.document(idRecuperadoReceita).collection(nomeReceitaRecuperado).document(idIngrediente).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    if(idRecuperadoReceita != null){
                            referenceReceita.document(idRecuperadoReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    }


                    resultadoVerifica = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    resultadoVerifica = false;
                }
            });

        }
        return resultadoVerifica;
    }
}

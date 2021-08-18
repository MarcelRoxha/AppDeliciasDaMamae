package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.HashMap;
import java.util.Map;

public class ReceitaCompletaDAO implements InterfaceReceitaCompletaDAO {

    //Instanciar Banco de dados;

    //-------->Firestore
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceReceitasCompletas = firebaseFirestore.collection("Receitas_completas");
    //-------->Realtime
    DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getReference().child("Receitas_completas");

    //Atributo Contexto
   private Context context;

    //Atributo Para estrutura de decisão
    boolean resultadoAdd;
    boolean resultadoUpdate;
    boolean resultadoAddIngrediente;
    boolean resultadoDelet;

    //Construtor


    public ReceitaCompletaDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean salvarReceita(ReceitaModel receitaModel) {

        try {

            Map<String, Object> receitaSalva = new HashMap<>();

            receitaSalva.put("nomeReceita",receitaModel. getNomeReceita());
            receitaSalva.put("quantRendimentoReceita", receitaModel.getQuantRendimentoReceita());
            receitaSalva.put("valorTotalReceita", receitaModel.getValorTotalReceita());

            referenceReceitasCompletas.add(receitaSalva).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Map<String, Object> receitaAtualizaId = new HashMap<>();

                    receitaAtualizaId.put("idReceita",documentReference.getId());
                    receitaAtualizaId.put("nomeReceita",receitaModel. getNomeReceita());
                    receitaAtualizaId.put("quantRendimentoReceita", receitaModel.getQuantRendimentoReceita());
                    receitaAtualizaId.put("valorTotalReceita", receitaModel.getValorTotalReceita());

                    referenceReceitasCompletas.document(documentReference.getId()).update(receitaAtualizaId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(context, "Sucesso ao salvar Receita", Toast.LENGTH_SHORT).show();
                            resultadoAdd = true;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, "Erro ao salvar Receita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    resultadoAdd = false;
                }
            });


        }catch (Exception e){
            Log.i("Error Exception ", e.getMessage());
            resultadoAdd = false;
        }
        return resultadoAdd;
    }

    @Override
    public boolean atualizarReceita(String id, ReceitaModel receitaModel) {

        String idRecuperado = id;

        if(id != null){

            try {

                Map<String, Object> receitaAtualizada = new HashMap<>();

                receitaAtualizada.put("idReceita", receitaModel.getIdReceita());
                receitaAtualizada.put("nomeReceita", receitaModel.getNomeReceita());
                receitaAtualizada.put("quantRendimentoReceita",receitaModel.getQuantRendimentoReceita() );
                receitaAtualizada.put("valorTotalReceita", receitaModel.getValorTotalReceita());
                receitaAtualizada.put("valorTotalIngredientes", receitaModel.getValoresIngredientes());
                receitaAtualizada.put("porcentagemServico", receitaModel.getPorcentagemServico());

                referenceReceitasCompletas.document(id).update(receitaAtualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Sucesso ao atualizar receita", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Não foi possível atualizar a receita", Toast.LENGTH_SHORT).show();
                        Log.i("Erro de Exception firebase : " , e.getMessage());
                    }
                });


            }catch (Exception e){
                Log.i("Erro de Exception : " , e.getMessage());

            }
        }else {
            Log.i("Erro id não recuperado : " , id);
        }
        return false;
    }


    @Override
    public boolean deletarReceitaBanco(String idReceitaModel, ReceitaModel receitaModel) {
        return false;
    }
}

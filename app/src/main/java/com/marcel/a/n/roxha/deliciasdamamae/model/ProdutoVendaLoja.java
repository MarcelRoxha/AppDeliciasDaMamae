package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.MainActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProdutoVendaLoja implements Serializable {

    private String id;
    private String nome;
    private String valorVenda;
    private String custoProduto;
    private Context context;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference reference = db.collection("PRODUTO_PARA_VENDA_LOJA");


    public ProdutoVendaLoja(Context context) {
        this.context = context;
    }

    public ProdutoVendaLoja(String id, String nome, String valorVenda, String custoProduto) {
        this.id = id;
        this.nome = nome;
        this.valorVenda = valorVenda;
        this.custoProduto = custoProduto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getCustoProduto() {
        return custoProduto;
    }

    public void setCustoProduto(String custoProduto) {
        this.custoProduto = custoProduto;
    }

    public void salvarProdutoVendaLoja(){

        Map<String, Object> salvarProdutoVendaLoja = new HashMap<>();
        salvarProdutoVendaLoja.put("id", getId());
        salvarProdutoVendaLoja.put("nome", getNome());
        salvarProdutoVendaLoja.put("valorVenda", getValorVenda());
        salvarProdutoVendaLoja.put("custoProduto", getCustoProduto());

        reference.add(salvarProdutoVendaLoja).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(context, "Sucesso ao salvar o produto para venda na loja", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Erro ao salvar o produto para venda na loja", Toast.LENGTH_SHORT).show();

            }
        });



    }
}

package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CaixaMensalModel implements Serializable {

    private String identificadorCaixaMensal;
    private int mesReferencia;
    private int quantTotalBolosAdicionadosMensal;
    private double valorTotalBolosVendidosMensal;
    private double valorTotalCustosBolosVendidosMensal;
    private double totalGastoMensal;
    public Context context;

    public CaixaMensalModel() {



    }

    public String getIdentificadorCaixaMensal() {
        return identificadorCaixaMensal;
    }

    public void setIdentificadorCaixaMensal(String id) {
        this.identificadorCaixaMensal = id;
    }

    public int getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(int mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public int getQuantTotalBolosAdicionadosMensal() {
        return quantTotalBolosAdicionadosMensal;
    }

    public void setQuantTotalBolosAdicionadosMensal(int quantTotalBolosAdicionadosMensal) {
        this.quantTotalBolosAdicionadosMensal = quantTotalBolosAdicionadosMensal;
    }

    public double getValorTotalBolosVendidosMensal() {
        return valorTotalBolosVendidosMensal;
    }

    public void setValorTotalBolosVendidosMensal(double valorTotalBolosVendidosMensal) {
        this.valorTotalBolosVendidosMensal = valorTotalBolosVendidosMensal;
    }

    public double getValorTotalCustosBolosVendidosMensal() {
        return valorTotalCustosBolosVendidosMensal;
    }

    public void setValorTotalCustosBolosVendidosMensal(double valorTotalCustosBolosVendidosMensal) {
        this.valorTotalCustosBolosVendidosMensal = valorTotalCustosBolosVendidosMensal;
    }

    public double getTotalGastoMensal() {
        return totalGastoMensal;
    }

    public void setTotalGastoMensal(double totalGastoMensal) {
        this.totalGastoMensal = totalGastoMensal;
    }



    public void  processaVendaBolo(Context context, String idBoloDelete, String id, int mesReferencia, int quantTotalBolosAdicionadosMensal,
                                   double valorTotalBolosVendidosMensal, double valorTotalCustosBolosVendidosMensal,double totalGastoMensal){
            this.context = context;
            String idBoloVitrineDelete = idBoloDelete;
            String idRecebido = id;
            int mesRecebido = mesReferencia;
            double totalRecebido = quantTotalBolosAdicionadosMensal;
            double totalVenda = valorTotalBolosVendidosMensal;
            double custoRecebido = valorTotalCustosBolosVendidosMensal;
            double totalmes = totalGastoMensal;


        Map<String, Object> montanteAtualiza = new HashMap<>();

        montanteAtualiza.put("identificadorCaixaMensal", idRecebido);
        montanteAtualiza.put("mesReferencia", mesRecebido);
        montanteAtualiza.put("quantTotalBolosAdicionadosMensal", totalRecebido);
        montanteAtualiza.put("valorTotalBolosVendidosMensal", totalVenda);
        montanteAtualiza.put("valorTotalCustosBolosVendidosMensal", custoRecebido);
        montanteAtualiza.put("totalGastoMensal", totalmes);

        FirebaseFirestore.getInstance().collection("CAIXA_MENSAL").document(idRecebido).update(montanteAtualiza).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                FirebaseFirestore.getInstance().collection("BOLOS_EXPOSTOS_VITRINE").document(idBoloVitrineDelete).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Intent intent = new Intent(context, LojaActivity.class);
                        context.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("Exclusão erro bolo vitrine:", e.getMessage());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}

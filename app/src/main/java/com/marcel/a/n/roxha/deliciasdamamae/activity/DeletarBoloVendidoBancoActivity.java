package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CaixaMensalModel;

import java.util.HashMap;
import java.util.Map;

public class DeletarBoloVendidoBancoActivity extends AppCompatActivity {

    //componentes da tela

    //----------------->Textos
    private TextView texto_nome_bolo;
    private TextView texto_valor_venda_bolo;
    private TextView texto_valor_custo_bolo;

    //------------------>Botões
    private Button botao_cancelar_exclusao;
    private Button botao_confirmar_exclusao;


    //Acesso ao Banco

    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceBoloVendido = firebaseFirestore.collection("BOLOS_VENDIDOS");
    CollectionReference referenceMontante = firebaseFirestore.collection("CAIXA_MENSAL");




    //Variavéis temporárias

    //--------------------->FIREBASE
    public String idMontante = null;
    public String idBoloVendido = null;

    //--------------------->DADOS DO BOLO VENDIDO

    public String nomeBoloVendido;
    public double valorVenda;
    public String textoValorVenda;
    public double custoBolo;
    public String textoValorCusto;


    //--------------------->DADOS DO MONTANTE ATUAL

    public int mesReferencia;
    public int quantTotalBolosAdicionadosMensal;
    public double valorTotalBolosVendidosMensal;
    public double valorTotalCustosBolosVendidosMensal;
    public double totalGastoMensal;

    public double resultadoValorVendar;
    public double resultadoValorcusto;
    public double resultadoValorTotalGasto;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_deletar_bolo_vendido_banco);

        idBoloVendido = getIntent().getStringExtra("idBoloVendido");
        idMontante = getIntent().getStringExtra("idMontanteAtual");

        texto_nome_bolo = findViewById(R.id.texto_nome_bolo_vendido_delete_id);
        texto_valor_venda_bolo = findViewById(R.id.texto_valor_venda_bolo_vendido_delete_id);
        texto_valor_custo_bolo = findViewById(R.id.texto_valor_custo_bolo_vendido_delete_id);

        botao_cancelar_exclusao = findViewById(R.id.botao_cancelar_exclusao_bolo_vendido_banco_id);
        botao_confirmar_exclusao = findViewById(R.id.botao_confirmar_deletar_bolo_vendido_banco_id);



        if(idBoloVendido != null && idMontante != null){
            referenceBoloVendido.document(idBoloVendido).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    BoloVendidoModel boloVendidoModel = documentSnapshot.toObject(BoloVendidoModel.class);

                    nomeBoloVendido = boloVendidoModel.getNomeBolo();
                    valorVenda = boloVendidoModel.getVelorVenda();
                    custoBolo = boloVendidoModel.getCustoBolo();

                    textoValorVenda = String.valueOf(valorVenda);
                    textoValorCusto = String.valueOf(custoBolo);

                    texto_nome_bolo.setText(nomeBoloVendido);
                    texto_valor_venda_bolo.setText(textoValorVenda);
                    texto_valor_custo_bolo.setText(textoValorCusto);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DeletarBoloVendidoBancoActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        botao_confirmar_exclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                referenceMontante.document(idMontante).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        CaixaMensalModel caixaMensalModel = documentSnapshot.toObject(CaixaMensalModel.class);

                            mesReferencia = caixaMensalModel.getMesReferencia();
                            quantTotalBolosAdicionadosMensal= caixaMensalModel.getQuantTotalBolosAdicionadosMensal();
                            valorTotalBolosVendidosMensal = caixaMensalModel.getValorTotalBolosVendidosMensal();
                            valorTotalCustosBolosVendidosMensal = caixaMensalModel.getValorTotalCustosBolosVendidosMensal();
                            totalGastoMensal = caixaMensalModel.getTotalGastoMensal();

                            processaExclusao(valorTotalBolosVendidosMensal, valorTotalCustosBolosVendidosMensal, totalGastoMensal, valorVenda, custoBolo);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        });

        botao_cancelar_exclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeletarBoloVendidoBancoActivity.this, BolosVendidosActivity2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void processaExclusao(
            double valorTotalBolosVendidosMensal, double valorTotalCustosBolosVendidosMensal,
            double totalGastoMensal, double valorVenda, double custoBolo) {


        resultadoValorVendar = valorTotalBolosVendidosMensal - valorVenda;
        resultadoValorcusto = valorTotalCustosBolosVendidosMensal - custoBolo;
        resultadoValorTotalGasto = totalGastoMensal - custoBolo;


        atualizarMontante(resultadoValorVendar, resultadoValorcusto, resultadoValorTotalGasto);




    }

    private void atualizarMontante(double resultadoValorVendar, double resultadoValorcusto, double resultadoValorTotalGasto) {

        double atualizaValorVendar = resultadoValorVendar;
        double atualizaValorCusto = resultadoValorcusto;
        double atualizaValorTotalGasto = resultadoValorTotalGasto;
        quantTotalBolosAdicionadosMensal--;


        Map<String, Object> Atualiza = new HashMap<>();
        Atualiza.put("identificadorCaixaMensal", idMontante);
        Atualiza.put("mesReferencia", mesReferencia);
        Atualiza.put("quantTotalBolosAdicionadosMensal", quantTotalBolosAdicionadosMensal);
        Atualiza.put("valorTotalBolosVendidosMensal", atualizaValorVendar);
        Atualiza.put("valorTotalCustosBolosVendidosMensal", atualizaValorCusto);
        Atualiza.put("totalGastoMensal", atualizaValorTotalGasto);

        referenceMontante.document(idMontante).update(Atualiza).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

               referenceBoloVendido.document(idBoloVendido).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Intent intent = new Intent(DeletarBoloVendidoBancoActivity.this, BolosVendidosActivity2.class);
                       startActivity(intent);
                       finish();
                   }
               });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DeletarBoloVendidoBancoActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
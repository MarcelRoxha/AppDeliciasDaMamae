package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosCadastradosVendaAddVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrineDiarioModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CaixaMensalModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdicionarBoloVitrineActivity2 extends AppCompatActivity {


    //Componentes de tela

    private RecyclerView recyclerViewListaBolosCadastradosVenda;
    private RecyclerView recyclerViewListaBolosAdicionadosExpostosVitrine;

    //Banco de dados
    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference reference = db.collection("Bolos_Cadastrados_venda");
    private FirebaseFirestore dbBolosAdicionadosVitrine = ConfiguracaoFirebase.getFirestor();
    private CollectionReference refBolosAdicionadosVitrine = dbBolosAdicionadosVitrine.collection("BOLOS_EXPOSTOS_VITRINE");
    private CollectionReference CAIXA_MENSAL_REFERENCIA = FirebaseFirestore.getInstance().collection("CAIXA_MENSAL");


    //Variaveis


    private String dataHoje;

    private String mesTexto;
    private double custoConvertido;
    private int mesAtual;
    private int verificaCaixaMensalCriado = 0;
    private int verifica = 0;

    private String idCaixaMensalRecuperado;
    private String textoVerifica;

    //Classes

    private BolosCadastradosVendaAddVitrineAdapter addVitrineAdapter;
    private BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter expostosAdapter;

    //Data

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date hoje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_adicionar_bolo_vitrine2);

        hoje = new Date();

        dataHoje = dateFormat.format(hoje.getTime());


        mesAtual = hoje.getMonth() + 01;

        mesTexto = String.valueOf(mesAtual);
        idCaixaMensalRecuperado = getIntent().getStringExtra("idMontanteRecuperado");


        recyclerViewListaBolosCadastradosVenda = findViewById(R.id.recyclerListaBolosCadastradoVenda_id);
        recyclerViewListaBolosAdicionadosExpostosVitrine = findViewById(R.id.recycler_lista_bolos_adicionado_vitrine_id);

    }

    public void verificarBolosAdicionadosVitrine() {

        refBolosAdicionadosVitrine.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    textoVerifica = snapshot.getId();
                }

                if (textoVerifica != null) {
                    carregarListaBolosAdicionadosExpostosVitrine();
                    expostosAdapter.startListening();
                    recyclerViewListaBolosAdicionadosExpostosVitrine.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewListaBolosAdicionadosExpostosVitrine.setVisibility(View.GONE);
                    ;

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void carregarListaBolosCadastrados() {

        Query query = reference.orderBy("nomeBolo", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<BolosModel> options = new FirestoreRecyclerOptions.Builder<BolosModel>()
                .setQuery(query, BolosModel.class)
                .build();


        addVitrineAdapter = new BolosCadastradosVendaAddVitrineAdapter(options, getApplicationContext());

        recyclerViewListaBolosCadastradosVenda.setHasFixedSize(true);
        recyclerViewListaBolosCadastradosVenda.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewListaBolosCadastradosVenda.setAdapter(addVitrineAdapter);
        recyclerViewListaBolosCadastradosVenda.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        addVitrineAdapter.setOnItemClickListerner(new IngredienteAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {


                BolosModel boloAdd = documentSnapshot.toObject(BolosModel.class);
                String nomeBoloAdd = boloAdd.getNomeBolo();
                String valorVenda = boloAdd.getValorVenda();
                String enderecoFoto = boloAdd.getEnderecoFoto();
                int verificaFoto = boloAdd.getVerificaCameraGaleria();
                String custoRecuperado = boloAdd.getCustoBolo();


                double valorConvert = Double.parseDouble(valorVenda);


                carregarAlertDialogQuantBolosAdd(nomeBoloAdd, enderecoFoto, valorConvert, custoRecuperado);

            }
        });

    }

    private void carregarListaBolosAdicionadosExpostosVitrine() {

        Query query = refBolosAdicionadosVitrine.orderBy("nomeBolo", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<BolosAdicionadosVitrine> options = new FirestoreRecyclerOptions.Builder<BolosAdicionadosVitrine>()
                .setQuery(query, BolosAdicionadosVitrine.class)
                .build();


        expostosAdapter = new BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter(options);

        recyclerViewListaBolosAdicionadosExpostosVitrine.setHasFixedSize(true);
        recyclerViewListaBolosAdicionadosExpostosVitrine.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewListaBolosAdicionadosExpostosVitrine.setAdapter(expostosAdapter);
        recyclerViewListaBolosAdicionadosExpostosVitrine.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        expostosAdapter.setOnItemClickListerner(new BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Toast.makeText(AdicionarBoloVitrineActivity2.this, "Teste clique", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder alertBoloExposto = new AlertDialog.Builder(AdicionarBoloVitrineActivity2.this);
                alertBoloExposto.setTitle("REMOVER ITEM DA LISTA");
                alertBoloExposto.setMessage("Deseja remover o item da lista? ");
                alertBoloExposto.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        expostosAdapter.deletarBolosAdicionadoIndividual(position);
                        Toast.makeText(AdicionarBoloVitrineActivity2.this, "Item removido da lista com sucesso", Toast.LENGTH_SHORT).show();


                    }
                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AdicionarBoloVitrineActivity2.this, "Cancelado", Toast.LENGTH_SHORT).show();

                    }
                });

                alertBoloExposto.show();
                alertBoloExposto.create();

            }
        });


    }

    private void carregarAlertDialogQuantBolosAdd(String nome, String endereco, double valor, String custo) {


        String nomeBoloAdicionadoVitrine = nome;
        String enderecoFotoBoloAdicionadoVitrine = endereco;
        double valorBoloAdicionadoVitrine = valor;
        String custoBoloAdicionadoVitrine = custo;
        converterCusto(custoBoloAdicionadoVitrine);


        AlertDialog.Builder alert = new AlertDialog.Builder(AdicionarBoloVitrineActivity2.this);
        alert.setTitle("QUANTIDADE ADICIONADAR");
        alert.setMessage("Digite a quantidade que deseja adicionar há vitrine, e aperte CONFIRMAR");
        EditText editText = new EditText(AdicionarBoloVitrineActivity2.this);
        alert.setView(editText);
        alert.setCancelable(false);
        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editText.getText().toString() != null) {

                    String quantDigitado = editText.getText().toString();
                    int quantDigitadoConvert = Integer.parseInt(quantDigitado);

                    if (quantDigitadoConvert == 0) {
                        Toast.makeText(AdicionarBoloVitrineActivity2.this, "Valor invalido, não é possível adicionar 0 bolo na vitrine. Favor insira uma quantidade valida", Toast.LENGTH_SHORT).show();
                    } else {

                        for (int cont = 0; cont < quantDigitadoConvert; cont++) {

                            BolosAdicionadosVitrine bolosAdicionadosVitrine = new BolosAdicionadosVitrine();
                            bolosAdicionadosVitrine.setNomeBolo(nomeBoloAdicionadoVitrine);
                            bolosAdicionadosVitrine.setEnderecoFotoSalva(enderecoFotoBoloAdicionadoVitrine);
                            bolosAdicionadosVitrine.setValorVenda(valorBoloAdicionadoVitrine);
                            bolosAdicionadosVitrine.setCustoBoloAdicionado(getCustoConvertido());

                            Map<String, Object> bolosAdicionadosExpostosVitrine = new HashMap<>();
                            bolosAdicionadosExpostosVitrine.put("identificador", idCaixaMensalRecuperado);
                            bolosAdicionadosExpostosVitrine.put("nomeBolo", bolosAdicionadosVitrine.getNomeBolo());
                            bolosAdicionadosExpostosVitrine.put("enderecoFotoSalva", bolosAdicionadosVitrine.getEnderecoFotoSalva());
                            bolosAdicionadosExpostosVitrine.put("valorVenda", bolosAdicionadosVitrine.getValorVenda());
                            bolosAdicionadosExpostosVitrine.put("custoBoloAdicionado", bolosAdicionadosVitrine.getCustoBoloAdicionado());

                            refBolosAdicionadosVitrine.add(bolosAdicionadosExpostosVitrine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                        }
                        verificarBolosAdicionadosVitrine();
                        carregarListaBolosAdicionadosExpostosVitrine();
                        expostosAdapter.startListening();
                        if (quantDigitadoConvert > 1) {
                            Toast.makeText(AdicionarBoloVitrineActivity2.this, "Bolos adicionados", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(AdicionarBoloVitrineActivity2.this, "Bolo adicionado", Toast.LENGTH_SHORT).show();
                        }
                    }


                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.show();
        alert.create();

    }

    private void converterCusto(String custo) {
        String custoRecebido = custo.replaceAll(",", ".");


        double custoConvert = Double.parseDouble(custoRecebido);
        this.custoConvertido = custoConvert;

    }

    private double getCustoConvertido() {
        return custoConvertido;
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaBolosCadastrados();
        carregarListaBolosAdicionadosExpostosVitrine();
        addVitrineAdapter.startListening();
        verificarBolosAdicionadosVitrine();
        expostosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        addVitrineAdapter.stopListening();
        expostosAdapter.stopListening();
    }

}
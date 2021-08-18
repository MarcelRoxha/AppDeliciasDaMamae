package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosExpostosVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosConfecionadosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosConfecionadosExpostosVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.BolosParaVendaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.BolosProntosVendaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.BolosVitrineLojaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitaNovaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitasProntasFragment;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CaixaMensalModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LojaActivity extends AppCompatActivity {

    //componentes de tela

    private TextView data;


    private RecyclerView recyclerViewLista_bolos_prontos_vendas;
    private Button botaoAdicionarBoloVitrine;
    private ImageView imagemCasoNaoTemBolosAdicionadosVitrine;
    private TextView textoCasoNaoTemBolosAdicionadosVitrine;
    private TextView textoBolosExpostosVitrine;
    private TextView texto_data;
    private Button telabolosvendidos;

    //adapter
    private BolosConfecionadosExpostosVitrineAdapter adapter;
    private BolosAdicionadosExpostosVitrineAdapter expostoAdapter;

    //Bonco de dados

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference refCaixa = db.collection("CAIXA_MENSAL");
    private CollectionReference refBolosExpostosVitrine = db.collection("BOLOS_EXPOSTOS_VITRINE");


    //Variaveis
    private Date date;
    private String dataAtual;
    private Date hoje = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    private String idRecuperadoMontanteCasoExista;
    private String idRecuperadoMontanteMensalCriado;
    private String idIdentificado;
    private int numMesReferencia;
    private int verificaCaixaMensalCriado;
    public int verificaBolosExpostosVitrine;
    public DocumentSnapshot snapshot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_loja);

        //Identificando componentes de tela
        texto_data = findViewById(R.id.texto_data_caixa_id);
        recyclerViewLista_bolos_prontos_vendas = findViewById(R.id.recycler_bolos_prontos_venda_id);
        botaoAdicionarBoloVitrine = findViewById(R.id.botao_adicionar_bolo_vitrine_id);
        imagemCasoNaoTemBolosAdicionadosVitrine = findViewById(R.id.imagemCasoNaoTemBolosAdicionadoVitrineId);
        textoCasoNaoTemBolosAdicionadosVitrine = findViewById(R.id.textoCasoNaoTenhaBoloAdicionadoVitrineId);
        textoBolosExpostosVitrine = findViewById(R.id.textView_expostos_na_vitrine_id);
        telabolosvendidos = findViewById(R.id.botao_bolos_vendidos_id);

        //Instanciando as classes
        date = new Date();
        dataAtual = simpleDateFormat.format(date.getTime());
        texto_data.setText(dataAtual);
        numMesReferencia = 1 + date.getMonth();


        //metodos que precisam ser carregados primeiro.
        verificaBolosExpostosVitrineAdicionados();
        verificarMontanteCaixaCriado();


        texto_data = findViewById(R.id.texto_data_caixa_id);




        botaoAdicionarBoloVitrine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(verificaCaixaMensalCriado == 2){

                    Intent intent = new Intent(LojaActivity.this, AdicionarBoloVitrineActivity2.class);
                    intent.putExtra("idMontanteRecuperado",idRecuperadoMontanteCasoExista);
                    startActivity(intent);
                    finish();
                }else{
                    carregarAlertMontanteNaoCriado();
                }


            }
        });

        telabolosvendidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LojaActivity.this, BolosVendidosActivity2.class);
                intent.putExtra("idMontanteRecuperado",idRecuperadoMontanteCasoExista);
                startActivity(intent);
                finish();


            }
        });


    }

    private void carregarAlertMontanteNaoCriado() {

        AlertDialog.Builder alertMontanteNaoCriado = new AlertDialog.Builder(LojaActivity.this);
        alertMontanteNaoCriado.setTitle("MONTANTE DO MÊS NÃO CRIADO");
        alertMontanteNaoCriado.setMessage("Atenção, você está prestes a adicionar bolos em sua vitrine virtual, contudo, \n" +
                "só é possível adicionar bolos há vitrine se o Caixa do Mês estiver criado. \n" +
                "Vamos criar um Caixa referente a esse Mês?\n" +
                "Basta selecionar CRIAR ");
        alertMontanteNaoCriado.setCancelable(false);

        alertMontanteNaoCriado.setPositiveButton("CRIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CaixaMensalModel caixaMensalModel = new CaixaMensalModel();
                caixaMensalModel.setMesReferencia(numMesReferencia);
                caixaMensalModel.setTotalGastoMensal(0);
                caixaMensalModel.setQuantTotalBolosAdicionadosMensal(0);
                caixaMensalModel.setValorTotalCustosBolosVendidosMensal(0);
                caixaMensalModel.setValorTotalBolosVendidosMensal(0);


                Map<String, Object> caixaMensalCriado = new HashMap<>();
                caixaMensalCriado.put("identificadorCaixaMensal", caixaMensalModel.getIdentificadorCaixaMensal());
                caixaMensalCriado.put("mesReferencia", caixaMensalModel.getMesReferencia());
                caixaMensalCriado.put("quantTotalBolosAdicionadosMensal", caixaMensalModel.getQuantTotalBolosAdicionadosMensal());
                caixaMensalCriado.put("valorTotalBolosVendidosMensal", caixaMensalModel.getValorTotalBolosVendidosMensal());
                caixaMensalCriado.put("valorTotalCustosBolosVendidosMensal", caixaMensalModel.getValorTotalCustosBolosVendidosMensal());
                caixaMensalCriado.put("totalGastoMensal", caixaMensalModel.getTotalGastoMensal());


                refCaixa.add(caixaMensalCriado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        idRecuperadoMontanteMensalCriado = documentReference.getId();

                        atualizarIdMontanteCriado(idRecuperadoMontanteMensalCriado);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


alertMontanteNaoCriado.create();
alertMontanteNaoCriado.show();
    }

    private void atualizarIdMontanteCriado(String idRecuperadoMontanteMensalCriado) {

        String idAtualizar = idRecuperadoMontanteMensalCriado;

        if(idAtualizar != null){


            CaixaMensalModel caixaMensalModelAtualizar = new CaixaMensalModel();
            caixaMensalModelAtualizar.setIdentificadorCaixaMensal(idAtualizar);
            caixaMensalModelAtualizar.setMesReferencia(numMesReferencia);
            caixaMensalModelAtualizar.setTotalGastoMensal(0);
            caixaMensalModelAtualizar.setQuantTotalBolosAdicionadosMensal(0);
            caixaMensalModelAtualizar.setValorTotalCustosBolosVendidosMensal(0);
            caixaMensalModelAtualizar.setValorTotalBolosVendidosMensal(0);

            Map<String, Object> caixaMensalCriadoAtualizado = new HashMap<>();

            caixaMensalCriadoAtualizado.put("identificadorCaixaMensal", caixaMensalModelAtualizar.getIdentificadorCaixaMensal());
            caixaMensalCriadoAtualizado.put("mesReferencia", caixaMensalModelAtualizar.getMesReferencia());
            caixaMensalCriadoAtualizado.put("quantTotalBolosAdicionadosMensal", caixaMensalModelAtualizar.getQuantTotalBolosAdicionadosMensal());
            caixaMensalCriadoAtualizado.put("valorTotalBolosVendidosMensal", caixaMensalModelAtualizar.getValorTotalBolosVendidosMensal());
            caixaMensalCriadoAtualizado.put("valorTotalCustosBolosVendidosMensal", caixaMensalModelAtualizar.getValorTotalCustosBolosVendidosMensal());
            caixaMensalCriadoAtualizado.put("totalGastoMensal", caixaMensalModelAtualizar.getTotalGastoMensal());


            refCaixa.document(idAtualizar).update(caixaMensalCriadoAtualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Intent intent = new Intent(LojaActivity.this, AdicionarBoloVitrineActivity2.class);
                    intent.putExtra("idMontanteRecuperado",idAtualizar);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    public void carregarListaBolosExpostosVitrine() {



            Query query = refBolosExpostosVitrine.orderBy("nomeBolo", Query.Direction.ASCENDING);



                FirestoreRecyclerOptions<BolosAdicionadosVitrine> options = new FirestoreRecyclerOptions.Builder<BolosAdicionadosVitrine>()
                        .setQuery(query, BolosAdicionadosVitrine.class)
                        .build();


                expostoAdapter = new BolosAdicionadosExpostosVitrineAdapter(options, getApplicationContext());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                recyclerViewLista_bolos_prontos_vendas.setHasFixedSize(true);
                recyclerViewLista_bolos_prontos_vendas.setLayoutManager(layoutManager);
                recyclerViewLista_bolos_prontos_vendas.setAdapter(expostoAdapter);
                recyclerViewLista_bolos_prontos_vendas.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);                    }
                });

                expostoAdapter.setOnItemClickListerner(new BolosAdicionadosExpostosVitrineAdapter.OnItemClickLisener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                        BolosAdicionadosVitrine boloClicado = documentSnapshot.toObject(BolosAdicionadosVitrine.class);
                        String idClicado = documentSnapshot.getId();
                        String nomeClicado = boloClicado.getNomeBolo();
                        String enderecoFotoClicado = boloClicado.getEnderecoFotoSalva();
                        double valorClicado = boloClicado.getValorVenda();
                        double custoClicado = boloClicado.getCustoBoloAdicionado();

                        localizarIdReceita(nomeClicado, idClicado);



                    }
                });

                expostoAdapter.setOnItemLongClickListerner(new BolosAdicionadosExpostosVitrineAdapter.OnLongClickListener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                       AlertDialog.Builder alert = new AlertDialog.Builder(LojaActivity.this);
                       alert.setTitle("ATENÇÃO");
                       alert.setMessage("Você realmente deseja remover esse item da sua vitrine virtual? ");
                       alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {


                               expostoAdapter.deletarItemIndividual(position);
                               Toast.makeText(LojaActivity.this, "Item removido", Toast.LENGTH_SHORT).show();
                              // verificaBolosExpostosVitrineAdicionados();
                               onStart();

                           }
                       }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Toast.makeText(LojaActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                           }
                       });

                       alert.show();
                       alert.create();

                    }
                });




    }

    private void localizarIdReceita(String nomeClicado, String idRecuperado) {

        String nomePassado = nomeClicado;
        String idPassado = idRecuperado;

        FirebaseFirestore.getInstance().collection("Receitas_completas").whereEqualTo("nomeReceita", nomePassado).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot : snapshotList){
                    idIdentificado = snapshot.getId();
                }

                Intent telaFinalizarVenda = new Intent(LojaActivity.this, FinalizarVendaBoloExpostoVitrineActivity.class);
                telaFinalizarVenda.putExtra("idBoloVenda",idPassado);
                telaFinalizarVenda.putExtra("idReceitaBolo", idIdentificado);
                telaFinalizarVenda.putExtra("idMontate", idRecuperadoMontanteCasoExista);
                startActivity(telaFinalizarVenda);
                finish();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public void verificarMontanteCaixaCriado() {


        refCaixa.whereEqualTo("mesReferencia", numMesReferencia).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    idRecuperadoMontanteCasoExista = snapshot.getId();
                }

                if (idRecuperadoMontanteCasoExista == null) {
                    verificaCaixaMensalCriado = 1;


                } else if(idRecuperadoMontanteCasoExista != null) {

                    verificaCaixaMensalCriado = 2;
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    public void verificaBolosExpostosVitrineAdicionados(){

        refBolosExpostosVitrine.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if(snapshotList.size() > 0){

                    verificaBolosExpostosVitrine = 1;

                    imagemCasoNaoTemBolosAdicionadosVitrine.setVisibility(View.GONE);
                    textoCasoNaoTemBolosAdicionadosVitrine.setVisibility(View.GONE);
                    recyclerViewLista_bolos_prontos_vendas.setVisibility(View.VISIBLE);

                }else{
                    verificaBolosExpostosVitrine = 2;

                    recyclerViewLista_bolos_prontos_vendas.setVisibility(View.GONE);
                    imagemCasoNaoTemBolosAdicionadosVitrine.setVisibility(View.VISIBLE);
                    textoCasoNaoTemBolosAdicionadosVitrine.setVisibility(View.VISIBLE);


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
            verificaBolosExpostosVitrineAdicionados();
            verificarMontanteCaixaCriado();

            carregarListaBolosExpostosVitrine();
            expostoAdapter.startListening();








    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
    }

    @Override
    protected void onStop() {
        super.onStop();
        expostoAdapter.stopListening();
    }
}
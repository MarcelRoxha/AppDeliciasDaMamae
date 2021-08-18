package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ListaIngredientesReceitaFinalizarCompraAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;
import com.marcel.a.n.roxha.deliciasdamamae.model.CaixaMensalModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FinalizarVendaBoloExpostoVitrineActivity extends AppCompatActivity {

    //Todos os componentes da tela

    private ImageView imagemBoloSalvoFinalizarVenda;
    private Button botaoFinalizarVendaIfood;
    private Button botaoFinalizarVendaLoja;

    private TextView textoValorVendaCadastradoBolo;
    private TextView textoCustoCadastradoBolo;
    private TextView textoNomeToolbarBolo;
    private RecyclerView recyclerListaIngredientesReceita;

    //Componentes toolbar
    private ImageView botao_voltar_tela;

    //Banco de datos

    FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceBolo = db.collection("BOLOS_EXPOSTOS_VITRINE");
    CollectionReference referenceReceita = db.collection("Receitas_completas");
    CollectionReference referenciaListaIngredienteReceita;
    CollectionReference referenceCaixaMensal = db.collection("CAIXA_MENSAL");

    //Variareis

    private String idBoloRecuperado;
    private String idReceitaRecuperada;
    private String idMontanteRecuperado;
    private String nomeBoloRecuperado;
    private String enderecoFotoBolo;
    private double custoBolo;
    private double vendaBolo;

    //Classes..

    private ListaIngredientesReceitaFinalizarCompraAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_finalizar_venda_bolo_exposto_vitrine);

        getSupportActionBar().hide();

        //Identificando componentes na tela...

        imagemBoloSalvoFinalizarVenda = findViewById(R.id.foto_salva_bolo_finalizar_venda_id);
        botaoFinalizarVendaIfood = findViewById(R.id.botao_venda_ifood_id);
        botaoFinalizarVendaLoja = findViewById(R.id.botao_venda_loja_id);
        textoValorVendaCadastradoBolo = findViewById(R.id.valor_venda_salvo_bolo_finalizar_venda_id);
        textoCustoCadastradoBolo = findViewById(R.id.custo_salvo_bolo_finalizar_venda_id);
        botao_voltar_tela = findViewById(R.id.botao_voltar_tela_id);
        textoNomeToolbarBolo = findViewById(R.id.texto_nome_bolo_toolbar_id);
        recyclerListaIngredientesReceita = findViewById(R.id.recycler_ingredientes_bolo_vendido_id);

        //Dados vindo da LojaActivity..

        idBoloRecuperado = getIntent().getStringExtra("idBoloVenda");
        idReceitaRecuperada = getIntent().getStringExtra("idReceitaBolo");
        idMontanteRecuperado = getIntent().getStringExtra("idMontate");

        Toast.makeText(this, "id recuperado montante: " + idMontanteRecuperado, Toast.LENGTH_SHORT).show();

        //Componentes  de tela - toolbar
        botao_voltar_tela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaLoja = new Intent(FinalizarVendaBoloExpostoVitrineActivity.this, LojaActivity.class);
                startActivity(voltarTelaLoja);
                finish();
            }
        });

        //Componentes  de tela - toolbar
        botaoFinalizarVendaIfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carregarAlertFinalizarVendaIfood();

            }
        });

        //Metodos
        carregarInformacoesBolo(idBoloRecuperado);
    }

    private void carregarAlertFinalizarVendaIfood() {

        AlertDialog.Builder alertFinalizarVendaIfood = new AlertDialog.Builder(FinalizarVendaBoloExpostoVitrineActivity.this);
        alertFinalizarVendaIfood.setTitle("VENDA FEITA PELO IFOOD");
        alertFinalizarVendaIfood.setMessage("Item vendido pelo ifood, escolha uma das opções abaixo");

        alertFinalizarVendaIfood.setPositiveButton("VENDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            carregarInformacoesReceita(idReceitaRecuperada);


            }
        }).setNeutralButton("EDITAR VALOR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                carregarAlertEditarValor();

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertFinalizarVendaIfood.create();
        alertFinalizarVendaIfood.show();

    }

    private void carregarAlertEditarValor() {

        AlertDialog.Builder alerEditValor = new AlertDialog.Builder(FinalizarVendaBoloExpostoVitrineActivity.this);
        alerEditValor.setTitle("Editar valor de venda");
        alerEditValor.setMessage("Informe o valor de venda pelo Ifood");

        EditText editValor = new EditText(this);
        alerEditValor.setView(editValor);
        alerEditValor.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String valorDigitado = editValor.getText().toString();

                if(!valorDigitado.isEmpty()){
                    double valorConvert = Double.parseDouble(valorDigitado);
                    atualizarValorVendaBolo(valorConvert);

                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerEditValor.create();
        alerEditValor.show();
    }

    private void atualizarValorVendaBolo(double valorConvert) {

        double valorRecebido = valorConvert;

        referenceBolo.document(idBoloRecuperado).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                BolosAdicionadosVitrine boloAtualizaVitrine = documentSnapshot.toObject(BolosAdicionadosVitrine.class);
                nomeBoloRecuperado = boloAtualizaVitrine.getNomeBolo();
                enderecoFotoBolo = boloAtualizaVitrine.getEnderecoFotoSalva();
                custoBolo = boloAtualizaVitrine.getCustoBoloAdicionado();
                vendaBolo = boloAtualizaVitrine.getValorVenda();


                if(nomeBoloRecuperado != null && enderecoFotoBolo != null && custoBolo > 0 && vendaBolo >0){
                    BoloVendidoModel boloVendidoModel = new BoloVendidoModel();
                    boloVendidoModel.salvarBolobanco(FinalizarVendaBoloExpostoVitrineActivity.this, idMontanteRecuperado, idBoloRecuperado, nomeBoloRecuperado, valorRecebido, custoBolo);


                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }

    private void carregarInformacoesReceita(String idReceita) {

        String recu = idReceita;
        referenceReceita.document(recu).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaModel = documentSnapshot.toObject(ReceitaModel.class);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    public void carregarListaIngredientesReceita() {

        Query query = referenciaListaIngredienteReceita.orderBy("nameItem", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();

        adapter = new ListaIngredientesReceitaFinalizarCompraAdapter(options);

        recyclerListaIngredientesReceita.setHasFixedSize(true);
        recyclerListaIngredientesReceita.setLayoutManager(new LinearLayoutManager(FinalizarVendaBoloExpostoVitrineActivity.this));
        recyclerListaIngredientesReceita.setAdapter(adapter);
        recyclerListaIngredientesReceita.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        adapter.startListening();
    }

    private void carregarInformacoesBolo(String idBoloRecuperado) {

        String idd = idBoloRecuperado;

        referenceBolo.document(idd).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                BolosAdicionadosVitrine bolosAdicionadosVitrine = documentSnapshot.toObject(BolosAdicionadosVitrine.class);
                nomeBoloRecuperado = bolosAdicionadosVitrine.getNomeBolo();
                enderecoFotoBolo = bolosAdicionadosVitrine.getEnderecoFotoSalva();
                custoBolo = bolosAdicionadosVitrine.getCustoBoloAdicionado();
                vendaBolo = bolosAdicionadosVitrine.getValorVenda();

                textoNomeToolbarBolo.setText(nomeBoloRecuperado);
                textoCustoCadastradoBolo.setText(String.valueOf(custoBolo));
                textoValorVendaCadastradoBolo.setText(String.valueOf(vendaBolo));
                Picasso.get().load(enderecoFotoBolo).into(imagemBoloSalvoFinalizarVenda);
                recuperarNomeBolo(nomeBoloRecuperado);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void recuperarNomeBolo(String nomeBoloRecuperado) {

        String idReceita = idReceitaRecuperada;
        String nomeRecuperado = nomeBoloRecuperado;

        referenciaListaIngredienteReceita = referenceReceita.document(idReceita).collection(nomeRecuperado);
        carregarListaIngredientesReceita();

    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
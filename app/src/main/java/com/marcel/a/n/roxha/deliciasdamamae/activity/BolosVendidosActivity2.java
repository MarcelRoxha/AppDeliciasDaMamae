package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosVendidosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;

public class BolosVendidosActivity2 extends AppCompatActivity {


    //Componentes de tela..
    RecyclerView recyclerView_lista_bolos_vendidos;

    //Banco de dados
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference reference = firebaseFirestore.collection("BOLOS_VENDIDOS");

    //Classes

    BolosVendidosAdapter bolosVendidosAdapter;



    //Variáveis

    private String idMontanteAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_bolos_vendidos2);

        recyclerView_lista_bolos_vendidos = findViewById(R.id.recyclerview_lista_bolos_vendidos_id);

        idMontanteAtual = getIntent().getStringExtra("idMontanteRecuperado");


    }

    public void carregarlistabolosvendidos() {

        Query query = reference.orderBy("nomeBolo", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<BoloVendidoModel> options = new FirestoreRecyclerOptions.Builder<BoloVendidoModel>()
                .setQuery(query, BoloVendidoModel.class)
                .build();

        bolosVendidosAdapter = new BolosVendidosAdapter(options);

        recyclerView_lista_bolos_vendidos.setHasFixedSize(true);
        recyclerView_lista_bolos_vendidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_lista_bolos_vendidos.setAdapter(bolosVendidosAdapter);
        recyclerView_lista_bolos_vendidos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        bolosVendidosAdapter.setOnItemClickListerner(new BolosVendidosAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String idBoloVendido = documentSnapshot.getId();

                carregarAlertaSettingsBoloVendido(idBoloVendido);
            }
        });

    }

    private void carregarAlertaSettingsBoloVendido(String id) {


        String idBolo = id;


        AlertDialog.Builder alert = new AlertDialog.Builder(BolosVendidosActivity2.this);
        alert.setTitle("OPÇÕES");
        alert.setMessage("Escolha uma das opções abaixo.");

        alert.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(BolosVendidosActivity2.this, EditarBoloVendidoBancoActivity.class);
                intent.putExtra("idBoloVendido", idBolo);
                intent.putExtra("idMontanteAtual", idMontanteAtual);
                startActivity(intent);


            }
        }).setNeutralButton("DELETAR DO BANCO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(BolosVendidosActivity2.this, DeletarBoloVendidoBancoActivity.class);
                intent.putExtra("idBoloVendido", idBolo);
                intent.putExtra("idMontanteAtual", idMontanteAtual);
                startActivity(intent);

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarlistabolosvendidos();
        bolosVendidosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bolosVendidosAdapter.stopListening();
    }


}
package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.List;

public class ReceitasActivity extends AppCompatActivity {

    private Button botao_tela_nova_receita;
    private Button botao_tela_receitas;

    private String idReceita;
    private String receitaKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_receitas);




        botao_tela_nova_receita = findViewById(R.id.bt_tela_nova_receita_id);
        botao_tela_receitas     = findViewById(R.id.bt_tela_receitas_feitas_id);




        botao_tela_nova_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verificarReceitaDuplicada ;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ReceitasActivity.this);
                dialog.setTitle("ATENÇÃO");
                dialog.setMessage("Para adicionar uma nova receita, é necessário informar o nome da receita antes de adicionar os ingrediente, favor insira o nome da receita e clique em CONTINUAR");
                EditText editNomeReceita = new EditText(getApplicationContext());
                dialog.setView(editNomeReceita);
                dialog.setCancelable(false);

                dialog.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                         receitaKey = editNomeReceita.getText().toString();


                        if(!receitaKey.isEmpty()){

                            verificarDuplicidadeNomeReceita(receitaKey);

                        }else {
                            Toast.makeText(ReceitasActivity.this, "Desculpe, não foi identificado o nome da receita e para continuar é preciso inserir essa informação, favor tente novamente inserir os dados", Toast.LENGTH_LONG).show();
                        }




                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(ReceitasActivity.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.create();
                dialog.show();


            }
        });

        botao_tela_receitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReceitasActivity.this, ReceitasProntasActivity.class);
                startActivity(intent);

            }
        });


    }

    public void verificarDuplicidadeNomeReceita(String nomeDigit){



        FirebaseFirestore.getInstance().collection("Receitas").whereEqualTo("nomeReceita", nomeDigit)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


                    List<String> listaReceita = new ArrayList<>();

                    for (DocumentSnapshot snapshot : snapshotList) {
                        idReceita = snapshot.getId();
                        listaReceita.add(snapshot.getId());
                   }

                if(listaReceita.size() > 0){

                    Intent intent = new Intent(ReceitasActivity.this, FinalizarReceitaActivity.class);
                    intent.putExtra("idReceita", idReceita);
                    intent.putExtra("nameReceita", receitaKey);
                    startActivity(intent);


                }else {

                    ReceitaModel receitaModel = new ReceitaModel();
                    receitaModel.setNomeReceita(nomeDigit);
                    receitaModel.salvarReceita();

                    Intent intent = new Intent(ReceitasActivity.this, NovaReceitaActivity.class);
                    intent.putExtra("nameReceita", receitaModel.getNomeReceita());
                    intent.putExtra("idReceita", idReceita);
                    startActivity(intent);





                }

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
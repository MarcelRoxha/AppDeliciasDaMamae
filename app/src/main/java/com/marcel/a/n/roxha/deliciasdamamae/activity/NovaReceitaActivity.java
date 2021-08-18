package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.IngredienteModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovaReceitaActivity extends AppCompatActivity {


    private RecyclerView recyclerView_list_itens_estoque;
    private RecyclerView recyclerView;
    private TextInputEditText edit_nome_receita;
    private TextView textValorIngredientes;
    private Button botao_proximo;

    private ReceitaModel receitaModel = new ReceitaModel();
    private IngredienteModel ingrediente = new IngredienteModel();
    private IngredienteAdapter adapterItem;
    private IngredienteAdicionadoAdapter adapterItem_0;

    private List<Double> listValoresItensAdd = new ArrayList<>();

    private String receitaKey;
    private String idReceita;
    private String valorAtualReceita;


    private final FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();


    private CollectionReference reference = db.collection("Receitas");

    private int contItem = 0;
    private String valorAdicionado;
    private  double valorConvert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_nova_receita);

        receitaKey = getIntent().getStringExtra("nameReceita");
        idReceita = getIntent().getStringExtra("idReceita");


        recyclerView_list_itens_estoque = findViewById(R.id.recycler_lista_itens_receita_estoque_id);
        recyclerView = findViewById(R.id.recycler_lista_item_add_id);
        botao_proximo = findViewById(R.id.bt_proxima_etapa_finalizar_receita_id);
        edit_nome_receita = findViewById(R.id.edit_nome_receita_id);
        edit_nome_receita.setText(receitaKey);

        textValorIngredientes = findViewById(R.id.text_valor_ingredientes_add_id);

        FirebaseFirestore.getInstance().collection("Receitas").whereEqualTo("nomeReceita", receitaKey)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {

                    idReceita = snapshot.getId();
                    receitaModel.setIdReceita(idReceita);

                    Toast.makeText(NovaReceitaActivity.this, "Adicione os ingredientes da Receita " + receitaKey + ", depois clique em salvar", Toast.LENGTH_SHORT).show();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        botao_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(NovaReceitaActivity.this, FinalizarReceitaActivity.class);
                intent.putExtra("idReceita", idReceita);
                intent.putExtra("nameReceita", receitaKey);
                startActivity(intent);


            }
        });


    }


    public void carregarListaItemEstoqueDate(){

        Query query = FirebaseFirestore.getInstance().collection("Item_Estoque").orderBy("nameItem", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();

        adapterItem = new IngredienteAdapter(options);

        recyclerView_list_itens_estoque.setHasFixedSize(true);
        recyclerView_list_itens_estoque.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_list_itens_estoque.setAdapter(adapterItem);
        recyclerView_list_itens_estoque.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        adapterItem.setOnItemClickListerner(new IngredienteAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                /* IngredienteModel ingredienteAdicionado = new IngredienteModel(); */

                ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                assert itemEstoqueModel != null;




                String nomeAdicionado = itemEstoqueModel.getNameItem();
                valorAdicionado = itemEstoqueModel.valorItemPorReceita();
                String quantAdicionado = itemEstoqueModel.getQuantUsadaReceita();



                FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();

                CollectionReference reference = db.collection("Receitas").document(receitaModel.getIdReceita()).collection(receitaKey);

                Map<String, Object> itemAdicionado = new HashMap<>();

                itemAdicionado.put("idReceita", idReceita);
                itemAdicionado.put("nameItem", nomeAdicionado);
                itemAdicionado.put("valorItemPorReceita", valorAdicionado);
                itemAdicionado.put("quantUsadaReceita",quantAdicionado);

                reference.add(itemAdicionado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        valorConvert = Double.parseDouble(valorAdicionado);
                        Toast.makeText(NovaReceitaActivity.this,  nomeAdicionado+ " Adicionado", Toast.LENGTH_SHORT).show();
                        listValoresItensAdd.add(valorConvert);
                        contItem++ ;

                        if (contItem == 1){
                            carregarListaItensAdicionados();
                            adapterItem_0.startListening();
                        }
                        setValorReceitaSalvo();
                        textValorIngredientes.setText("Valores adicionados R$: " + setValorReceitaSalvo());



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            }
        });



    }

    public void carregarListaItensAdicionados(){

        Query query = FirebaseFirestore.getInstance().collection("Receitas").document(idReceita).collection(receitaKey)
        .orderBy("nameItem", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();


         adapterItem_0 = new IngredienteAdicionadoAdapter(options);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItem_0);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        adapterItem_0.setOnItemClickListerner(new IngredienteAdicionadoAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                adapterItem_0.deletarItemIndividual(position);
                listValoresItensAdd.remove(valorConvert);
                setValorReceitaSalvo();
                textValorIngredientes.setText("Valores adicionados R$: " + setValorReceitaSalvo());
                adapterItem_0.deletarItemIndividual(position);
            }
        });




    }

    public String setValorReceitaSalvo (){

        double valorAdd = 0;
        double resultado = 0;
        for (Double list : listValoresItensAdd){
            resultado += valorAdd + list;
        }

        String valor = String.format("%.2f", resultado);
        return valor;

    }

    public String getValorAtualReceita(){
        return valorAtualReceita;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_nova_receita_descartar_alteracoes_receita, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if ((item.getItemId() == R.id.bt_cancelarr_receita)){


            AlertDialog.Builder dialog = new AlertDialog.Builder(NovaReceitaActivity.this);
            dialog.setTitle("CANCELAR");
            dialog.setMessage("Deseja realmente descartar essa receita?");
            dialog.setCancelable(false);

            dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FirebaseFirestore.getInstance().collection("Receitas").whereEqualTo("nomeReceita", receitaKey).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot snapshot : snapshotList){

                                snapshot.getReference().delete();
                                Toast.makeText(NovaReceitaActivity.this, "Alterações na receita " + receitaKey + " foram descartadas", Toast.LENGTH_SHORT).show();

                            }

                            Intent intent = new Intent(NovaReceitaActivity.this, ReceitasActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    FirebaseFirestore.getInstance().collection("Receitas").document(idReceita).collection(receitaKey).whereEqualTo("idReceita", idReceita).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot snapshot: listIngredientes){
                                snapshot.getReference().delete();
                            }

                            Toast.makeText(NovaReceitaActivity.this, "Alterações na receita " + receitaKey + " foram descartadas", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });





                        }

            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Toast.makeText(NovaReceitaActivity.this, "Ação cancelada continue confeccionando: " + receitaKey, Toast.LENGTH_SHORT).show();

                }
            });

            dialog.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);


}


    @Override
    protected void onStart() {
        carregarListaItemEstoqueDate();
        adapterItem.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adapterItem.stopListening();

        if(listValoresItensAdd.size() > 0){
            adapterItem_0.stopListening();
        }

        super.onStop();
    }
}
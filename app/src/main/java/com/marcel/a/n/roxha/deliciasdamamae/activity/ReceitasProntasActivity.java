package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredientesReceitaProntaAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasProntasAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.List;

public class ReceitasProntasActivity extends AppCompatActivity {

    /*Componentes de tela*/
    private RecyclerView recyclerView_lista_ingredientes_receita;
    private TextInputEditText nome_edit_receita;
    private TextInputEditText porcent_servico_edit_receita;
    private TextInputEditText quant_rendi_edit_receita;

    private TextView valor_ingredientes_edit_receita;
    private TextView valor_total_receita_edit_receita;

    private Button botao_salvar_alteracoes;
    private Button botao_deletar_receita;
    private Button botao_cancelar_voltar;

    /*Firebase*/
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceReceita = firebaseFirestore.collection("Receitas_completas");
    CollectionReference referenceReceitaIngredientes = firebaseFirestore.collection("Receitas_completas");

    /*Classes*/
    private IngredienteAdicionadoAdapter adapterIngrediente;


    /*Variaveis para receber o valor salvo no banco*/

    String idRecuperadoReceitaEdit = null;
    String nomeReceita;
    String porcentServicoReceita;
    String quanRendimentoReceita;
    String valorTotalIngredientes;
    String valorTotalRecita;

    String idIngrediente;
    String valorIngreienteBanco;
    double valorIngredienteBancoConverdo;
    List<Double> listaValoresIngredientes = new ArrayList<>();
    double resultado;
    double valor;
    String valorIngredientesExibi;


    /*Variavei para atualizar a receita*/
    String nomeReceitaAtualiza;
    String porcentServicoReceitaAtualiza;
    String quantRendimentoReceitaAtualiza;
    String valorTotalIngredientesAtualiza;
    String valorTotalReceitaAtualiza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_receitas_prontas);

        //Indentificando os componentes da tela
        recyclerView_lista_ingredientes_receita = findViewById(R.id.recyclerView_lista_ingredientes_receita_edit_id);
        nome_edit_receita = findViewById(R.id.nome_receita_edit_id);
        porcent_servico_edit_receita = findViewById(R.id.porcent_receita_edit_id);
        quant_rendi_edit_receita = findViewById(R.id.rendimento_fornada_receita_edit_id);

        valor_ingredientes_edit_receita = findViewById(R.id.valor_total_ingredientes_receita_edit_id);
        valor_total_receita_edit_receita = findViewById(R.id.valor_total_receita_edit_id);

        botao_salvar_alteracoes = findViewById(R.id.botao_salvar_alteracoes_receita_edit_id);
        botao_deletar_receita = findViewById(R.id.botao_deletar_receita_banco_id);
        botao_cancelar_voltar = findViewById(R.id.botao_cancelar_voltar_id);

        idRecuperadoReceitaEdit = getIntent().getStringExtra("idReceitaCadastrada");
        nomeReceita = getIntent().getStringExtra("nomeReceitaEdit");



        if(idRecuperadoReceitaEdit != null){
            carregarInformacoesReceitaPronta(idRecuperadoReceitaEdit);
            carregarListaIngredientesReceita();
            adapterIngrediente.startListening();

        }






    }

    private void carregarInformacoesReceitaPronta(String idRecuperadoReceitaEdit) {
            String verificaId = idRecuperadoReceitaEdit;

            if (verificaId != null){
                referenceReceita.document(verificaId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ReceitaModel receitaModel = documentSnapshot.toObject(ReceitaModel.class);

                        nomeReceita = receitaModel.getNomeReceita();
                        porcentServicoReceita = receitaModel.getPorcentagemServico();
                        quanRendimentoReceita = receitaModel.getQuantRendimentoReceita();
                        valorTotalRecita = receitaModel.getValorTotalReceita();


                        recuperaIngredientes(nomeReceita, porcentServicoReceita, quanRendimentoReceita, valorTotalRecita);
                       // carregarListaIngredientesReceita();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }



    }

    private void recuperaIngredientes(String nomeReceita, String porcentServicoReceita, String quanRendimentoReceita, String valorTotalRecita) {

        String nome = nomeReceita;
        String porcent = porcentServicoReceita;
        String quantRendi = quanRendimentoReceita;
        String valorReceita = valorTotalRecita;

        referenceReceitaIngredientes = referenceReceita.document(idRecuperadoReceitaEdit).collection(nomeReceita);
        referenceReceitaIngredientes.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot list : snapshotList){
                    idIngrediente = list.getId();

                    calcularValoresIngredientes(idIngrediente, nome, porcent, quantRendi, valorReceita);

                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



        //exibeValoresUser();




    }


    private void calcularValoresIngredientes(String idIngrediente, String name, String porcentti, String quantRendiimento, String valorReceitaTotal ) {

        String nomeExibi = name;
        String porcentExibi = porcentti;
        String quantRendiExibi = quantRendiimento;
        String valorReceitaExibi = valorReceitaTotal;

        referenceReceita.document(idRecuperadoReceitaEdit).collection(nomeReceita).document(idIngrediente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                assert itemEstoqueModel != null;

                valorIngreienteBanco = itemEstoqueModel.getValorItemPorReceita();
                valorIngredienteBancoConverdo = Double.parseDouble(valorIngreienteBanco);
                resultado += valorIngredienteBancoConverdo;
                listaValoresIngredientes.add(valorIngredienteBancoConverdo);
                //Toast.makeText(ReceitasProntasActivity.this, "Resultado dentro : " + resultado, Toast.LENGTH_SHORT).show();
                recuperaTeste(resultado);


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    private void recuperaTeste(double resultado) {
        nomeReceitaAtualiza = nomeReceita;
        porcentServicoReceitaAtualiza = porcentServicoReceita;
        quantRendimentoReceitaAtualiza = quanRendimentoReceita;
        valorTotalIngredientesAtualiza = valorTotalIngredientes;
        valorTotalReceitaAtualiza = valorTotalRecita;

        String textoTotalIngredients = String.format("%.2f", resultado);
        nome_edit_receita.setText(nomeReceitaAtualiza);
        porcent_servico_edit_receita.setText(porcentServicoReceitaAtualiza);
        quant_rendi_edit_receita.setText(quantRendimentoReceitaAtualiza);

        valor_total_receita_edit_receita.setText(valorTotalReceitaAtualiza);
        valor_ingredientes_edit_receita.setText(textoTotalIngredients);




    }

    public void carregarListaIngredientesReceita(){

        Query queryReceitaCompleta = referenceReceitaIngredientes.document(idRecuperadoReceitaEdit).collection(nomeReceita).orderBy("nameItem", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(queryReceitaCompleta, ItemEstoqueModel.class)
                .build();


        adapterIngrediente = new IngredienteAdicionadoAdapter(options);

        recyclerView_lista_ingredientes_receita.setHasFixedSize(true);
        recyclerView_lista_ingredientes_receita.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_lista_ingredientes_receita.setAdapter(adapterIngrediente);
        recyclerView_lista_ingredientes_receita.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));

        adapterIngrediente.setOnItemClickListerner(new IngredienteAdicionadoAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Toast.makeText(ReceitasProntasActivity.this, "Olha o clique", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
      /*  carregarListaIngredientesReceita();
        adapterIngrediente.startListening();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterIngrediente.stopListening();
    }
}
package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredientesReceitaProntaAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasProntasAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.IngredienteReceitaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ReceitaCompletaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceitasProntasActivity extends AppCompatActivity {

    /*Componentes de tela*/
    private RecyclerView recyclerView_lista_ingredientes_receita;
    private RecyclerView recyclerView_lista_ingredientes_estoque;

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
    CollectionReference referenceIngredientesReceita = firebaseFirestore.collection("Item_Estoque");

    /*Classes*/
    private IngredienteAdicionadoAdapter adapterIngrediente;
    private IngredienteAdapter adapterItem;


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
    private List<Double> listValoresItensAdd = new ArrayList<>();
    double resultado;
    double valor;
    String valorIngredientesExibi;


    /*Variavei para atualizar a receita*/
    String nomeReceitaAtualiza;
    String porcentServicoReceitaAtualiza;
    String quantRendimentoReceitaAtualiza;
    String valorTotalIngredientesAtualiza;
    String valorTotalReceitaAtualiza;
    String textoIngredientes;
    String textoValorReceita;
    double addIngredienteReceita;
    double deleteIngredienteReceita;


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
        recyclerView_lista_ingredientes_estoque = findViewById(R.id.recyclerView_Lista_ingredientes_estoque_id);

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
            carregarListaIngredientesEstoque();
           adapterIngrediente.startListening();
           adapterItem.startListening();

        }

        botao_cancelar_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceitasProntasActivity.this, ProducaoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        botao_salvar_alteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nomeReceitaAtualiza = nome_edit_receita.getText().toString();
                porcentServicoReceitaAtualiza = porcent_servico_edit_receita.getText().toString();
                quantRendimentoReceitaAtualiza = quant_rendi_edit_receita.getText().toString();
                valorTotalIngredientesAtualiza = valor_ingredientes_edit_receita.getText().toString();
                valorTotalReceitaAtualiza = valor_total_receita_edit_receita.getText().toString();

                if(        !nomeReceitaAtualiza.isEmpty()
                        && !porcentServicoReceitaAtualiza.isEmpty()
                        && !quantRendimentoReceitaAtualiza.isEmpty()
                        && !valorTotalIngredientesAtualiza.isEmpty()
                        && !valorTotalReceitaAtualiza.isEmpty()){



                    String valorTotalIngredientesPrepare = valorTotalIngredientesAtualiza.replace(",", ".");
                    String valorTotalReceitaPrepare = valorTotalReceitaAtualiza.replace(",", ".");

                    double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesPrepare);

                    int porcentConvert = Integer.parseInt(porcentServicoReceitaAtualiza);


                    double resultadoPorcentagem = (valorTotalIngredientesConvertido * porcentConvert) / 100;
                    double resultadoTotalReceita = resultadoPorcentagem + valorTotalIngredientesConvertido ;


                    convertStringValoresIngredientes(valorTotalIngredientesConvertido);
                    convertStringValorTotalReceita(resultadoTotalReceita);


                    ReceitaModel receitaAtualiza = new ReceitaModel();
                    receitaAtualiza.setIdReceita(idRecuperadoReceitaEdit);
                    receitaAtualiza.setNomeReceita(nomeReceitaAtualiza);
                    receitaAtualiza.setQuantRendimentoReceita(quantRendimentoReceitaAtualiza);
                    receitaAtualiza.setPorcentagemServico(porcentServicoReceitaAtualiza);
                    receitaAtualiza.setValorTotalReceita(getValorTotaReceita());
                    receitaAtualiza.setValoresIngredientes(getValorTotalIngredientes());

                    ReceitaCompletaDAO receitaCompletaDAO = new ReceitaCompletaDAO(ReceitasProntasActivity.this);
                    receitaCompletaDAO.atualizarReceita(idRecuperadoReceitaEdit, receitaAtualiza);


                    Intent intent = new Intent(ReceitasProntasActivity.this, ProducaoActivity.class);
                    startActivity(intent);
                    finish();


                }else {
                    Toast.makeText(ReceitasProntasActivity.this, "Favor verifique as informações inseridas", Toast.LENGTH_SHORT).show();
                }






            }
        });






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
                        valorTotalIngredientes = receitaModel.getValoresIngredientes();

                        nome_edit_receita.setText(nomeReceita);
                        porcent_servico_edit_receita.setText(porcentServicoReceita);
                        quant_rendi_edit_receita.setText(quanRendimentoReceita);
                        valor_total_receita_edit_receita.setText(valorTotalRecita);
                        valor_ingredientes_edit_receita.setText(valorTotalIngredientes);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }



    }

    /*Ingredientes em estoque  ao clicar será adicionado esse valor a receita*/
    private void carregarListaIngredientesEstoque() {

        Query query = FirebaseFirestore.getInstance().collection("Item_Estoque").orderBy("nameItem", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();

        adapterItem = new IngredienteAdapter(options);

        recyclerView_lista_ingredientes_estoque.setHasFixedSize(true);
        recyclerView_lista_ingredientes_estoque.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_lista_ingredientes_estoque.setAdapter(adapterItem);
        recyclerView_lista_ingredientes_estoque.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        adapterItem.setOnItemClickListerner(new IngredienteAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                /*Caso tenha ação de clique será adicionado esse item que está em estoque a receita.*/

                ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                assert itemEstoqueModel != null;
                String id = documentSnapshot.getId();
                String nomeItemAdd = itemEstoqueModel.getNameItem();
                String valorItemAdd = itemEstoqueModel.getValorItemPorReceita();
                String quantItemAdd = itemEstoqueModel.getQuantUsadaReceita();




                AlertDialog.Builder alert = new AlertDialog.Builder(ReceitasProntasActivity.this);

                alert.setMessage("CONFIRME PARA ADICIONAR INGREDIENTE");
                alert.setTitle("ADICIONAR INGREDIENTE");
                alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ReceitaCompletaDAO receitaCompletaDAO = new ReceitaCompletaDAO(ReceitasProntasActivity.this);

                      receitaCompletaDAO.adicionarIngredienteEdit(idRecuperadoReceitaEdit, nomeReceita, nomeItemAdd, quantItemAdd, valorItemAdd);


                            String valorIngredientePrepare = valorItemAdd.replace(",", ".");
                            String valorTotalIngredientesPrepare = valorTotalIngredientes.replace(",", ".");
                            String valorTotalReceitaPrepare = valorTotalRecita.replace(",", ".");


                            double valorTotalReceitaConvertido = Double.parseDouble(valorTotalReceitaPrepare);
                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesPrepare);
                            double valorItemAdicionadoConvertido = Double.parseDouble(valorIngredientePrepare);
                            int porcentConvert = Integer.parseInt(porcentServicoReceita);


                            double resultadoTotalIngredientes = valorTotalIngredientesConvertido + valorItemAdicionadoConvertido;
                            double resultadoPorcentagem = (resultadoTotalIngredientes * porcentConvert) / 100;
                            double resultadoTotalReceita = resultadoPorcentagem + resultadoTotalIngredientes ;


                            convertStringValoresIngredientes(resultadoTotalIngredientes);
                            convertStringValorTotalReceita(resultadoTotalReceita);


                            valor_total_receita_edit_receita.setText(getValorTotaReceita());
                            valor_ingredientes_edit_receita.setText(getValorTotalIngredientes());

                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.create();
                alert.show();
            }


        });



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
                /*Caso tenha ação de clique será adicionado esse item que está em estoque a receita.*/

                ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                assert itemEstoqueModel != null;
                String id = documentSnapshot.getId();
                String nomeItemAdd = itemEstoqueModel.getNameItem();
                String valorItemAdd = itemEstoqueModel.getValorItemPorReceita();
                String quantItemAdd = itemEstoqueModel.getQuantUsadaReceita();




                AlertDialog.Builder alert = new AlertDialog.Builder(ReceitasProntasActivity.this);

                alert.setMessage("CONFIRME PARA ADICIONAR INGREDIENTE");
                alert.setTitle("REMOVER INGREDIENTE");
                alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ReceitaCompletaDAO receitaCompletaDAO = new ReceitaCompletaDAO(ReceitasProntasActivity.this);

                        receitaCompletaDAO.removerIngredienteEdit(idRecuperadoReceitaEdit, id, nomeReceita, nomeItemAdd, quantItemAdd, valorItemAdd);


                        String valorIngredientePrepare = valorItemAdd.replace(",", ".");
                        String valorTotalIngredientesPrepare = valorTotalIngredientes.replace(",", ".");
                        String valorTotalReceitaPrepare = valorTotalRecita.replace(",", ".");


                        double valorTotalReceitaConvertido = Double.parseDouble(valorTotalReceitaPrepare);
                        double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesPrepare);
                        double valorItemAdicionadoConvertido = Double.parseDouble(valorIngredientePrepare);
                        int porcentConvert = Integer.parseInt(porcentServicoReceita);


                        double resultadoTotalIngredientes = valorTotalIngredientesConvertido - valorItemAdicionadoConvertido;
                        double resultadoPorcentagem = (resultadoTotalIngredientes * porcentConvert) / 100;
                        double resultadoTotalReceita = resultadoPorcentagem + resultadoTotalIngredientes ;


                        convertStringValoresIngredientes(resultadoTotalIngredientes);
                        convertStringValorTotalReceita(resultadoTotalReceita);


                        valor_total_receita_edit_receita.setText(getValorTotaReceita());
                        valor_ingredientes_edit_receita.setText(getValorTotalIngredientes());

                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.create();
                alert.show();
            }


        });



    }

    private void convertStringValorTotalReceita(double resultadoTotalReceita) {

        double totalReceita = resultadoTotalReceita;

        String valorReceitaTotal = String.format("%.2f", totalReceita);

        this.textoValorReceita = valorReceitaTotal;

        valorTotalRecita = textoValorReceita;

    }

    private void convertStringValoresIngredientes(double resultadoTotalIngredientes) {


        double TotalIngredientes = resultadoTotalIngredientes;

        String totalIngredientes = String.format("%.2f", TotalIngredientes);


        this.textoIngredientes = totalIngredientes;

        valorTotalIngredientes = textoIngredientes;
    }

    private String getValorTotaReceita (){

        return textoValorReceita;
    }

    private String getValorTotalIngredientes(){

        return textoIngredientes;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(idRecuperadoReceitaEdit != null){
            carregarInformacoesReceitaPronta(idRecuperadoReceitaEdit);
            carregarListaIngredientesReceita();
            carregarListaIngredientesEstoque();
            adapterItem.startListening();
            adapterIngrediente.startListening();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterIngrediente.stopListening();
    }
}
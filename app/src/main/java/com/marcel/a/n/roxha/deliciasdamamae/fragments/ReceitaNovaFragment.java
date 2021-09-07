package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.FinalizarReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.NovaReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ReceitaCompletaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.IngredienteModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceitaNovaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceitaNovaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceitaNovaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceitaNovaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceitaNovaFragment newInstance(String param1, String param2) {
        ReceitaNovaFragment fragment = new ReceitaNovaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //Componentes de tela
    private RecyclerView recyclerViewListItensEstoque;
    private RecyclerView recyclerViewListItensAdicionados;
    private TextInputEditText edit_nome_receita;
    private TextView textValorIngredientes, textoIngredientesEstoque, textoIngredientesAdicionados;
    private TextView texto_tipo_producao;
    private Button botao_jaAdicioneiIngredientes, botao_salvar_nome_receita;
    private RadioButton radioButton_massa_selecionada;
    private RadioButton radioButton_cobertura_selecionada;
    private RadioButton radioButton_receita_completa_selecionada;
    private RadioGroup radioGroup_tipo_producao;

    //Classes
    private ReceitaModel receitaModel = new ReceitaModel();
    private IngredienteModel ingrediente = new IngredienteModel();
    private IngredienteAdapter adapterItem;
    private IngredienteAdicionadoAdapter adapterItem_0;
    private ReceitaCompletaDAO receitaCompletaDAO;

    //Variaveis Temporarias
    private List<Double> listValoresItensAdd = new ArrayList<>();
    private String nomeReceitaDigitado;
    private String idReceita;
    private final FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();


    private CollectionReference reference = db.collection("Receitas");

    private int contItem = 0;
    private String valorAdicionado;
    private  double valorConvert;
    private  int contReceitaCompleta = 0;
    private  int contMassa = 0;
    private  int contConbertura = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_receita_nova, container, false);

        //Identificando os componentes na tela

        //-----------------------------------RecyclerView
        recyclerViewListItensEstoque = view.findViewById(R.id.recycler_fragment_itens_estoque_id);
        recyclerViewListItensAdicionados = view.findViewById(R.id.recycler_fragment_receita_nova_itens_adicionados_id);

        //-----------------------------------Inputs e Textos
        texto_tipo_producao = view.findViewById(R.id.texto_tipo_producao_id);
        textValorIngredientes = view.findViewById(R.id.valor_id);
        textoIngredientesEstoque = view.findViewById(R.id.textView24);
        textoIngredientesAdicionados = view.findViewById(R.id.textView25);
        edit_nome_receita = view.findViewById(R.id.edit_fragment_nome_receita_id);

        //-----------------------------------Botões
        botao_salvar_nome_receita = view.findViewById(R.id.botao_salvar_nome_receita_fragment_id);
        botao_jaAdicioneiIngredientes = view.findViewById(R.id.btProximo_fragment_receita_nova_id);



        //Escondendo os componentes da tela para serem exibidos na ordem certa
        textValorIngredientes.setVisibility(View.GONE);
        botao_jaAdicioneiIngredientes.setVisibility(View.GONE);
        textoIngredientesEstoque.setVisibility(View.GONE);
        textoIngredientesAdicionados.setVisibility(View.GONE);
        recyclerViewListItensEstoque.setVisibility(View.GONE);
        recyclerViewListItensAdicionados.setVisibility(View.GONE);
        texto_tipo_producao.setVisibility(View.GONE);


        //Definindo a ação do botão ao ser clicado
        botao_salvar_nome_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificando o que foi digitado no edit nome da receita
                nomeReceitaDigitado = edit_nome_receita.getText().toString();
                if (!nomeReceitaDigitado.isEmpty()) {

                    /* Caso o nome da receita não esteja vazio, será verificado
                     * se não existe outra receita com o mesmo nome,
                     * pois não é permitido receitas duplicadas*/

                    //Methodo que verifica a duplicidade
                        verificarDuplicidadeNomeReceita(nomeReceitaDigitado);
                        texto_tipo_producao.setText(nomeReceitaDigitado);
                        texto_tipo_producao.setVisibility(View.VISIBLE);

                        contReceitaCompleta = 1;
 }else {
                    Toast.makeText(getActivity(), "Favor insira o nome da Receita", Toast.LENGTH_SHORT).show();

                }

            }
        });


        botao_jaAdicioneiIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contItem > 0){



                Intent intent = new Intent(getActivity(), FinalizarReceitaActivity.class);
                intent.putExtra("idReceita", idReceita);
                intent.putExtra("nameReceita", nomeReceitaDigitado);

                if(contReceitaCompleta == 1){
                    intent.putExtra("tipoProducao", "1");

                }
                startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Favor, insira os ingredientes necessarios", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;



    }

   public void carregarListaItemEstoqueDate(){

        Query query = FirebaseFirestore.getInstance().collection("Item_Estoque").orderBy("nameItem", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();

        adapterItem = new IngredienteAdapter(options);

       recyclerViewListItensEstoque.setHasFixedSize(true);
       recyclerViewListItensEstoque.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerViewListItensEstoque.setAdapter(adapterItem);
       recyclerViewListItensEstoque.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        adapterItem.setOnItemClickListerner(new IngredienteAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {



                    ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                    assert itemEstoqueModel != null;

                    String nomeItemAdd = itemEstoqueModel.getNameItem();
                    String valorItemAdd = itemEstoqueModel.getValorItemPorReceita();
                    String quantItemAdd = itemEstoqueModel.getQuantUsadaReceita();



                        adicionarIngredienteReceitaCompleta(nomeReceitaDigitado, nomeItemAdd, valorItemAdd, quantItemAdd);






                }


        });



    }

    private  void adicionarIngredienteReceitaCompleta(String nomeReceita, String nomeItemAdicionado, String valorItemAdicionado, String quantItemAdicionado){

        FirebaseFirestore.getInstance().collection("Receitas_completas").whereEqualTo("nomeReceita", nomeReceita).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


                List<String> listaReceita = new ArrayList<>();

                for (DocumentSnapshot snapshot : snapshotList){

                    idReceita = snapshot.getId();

                }

                CollectionReference reference = db.collection("Receitas_completas").document(idReceita).collection(nomeReceitaDigitado);

                Map<String, Object> ingredietenAdicionadoReceita = new HashMap<>();
                ingredietenAdicionadoReceita.put("nameItem", nomeItemAdicionado);
                ingredietenAdicionadoReceita.put("valorItemPorReceita", valorItemAdicionado);
                ingredietenAdicionadoReceita.put("quantUsadaReceita", quantItemAdicionado);

                reference.add(ingredietenAdicionadoReceita).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        valorConvert = Double.parseDouble(valorItemAdicionado);
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    public void carregarListaItensAdicionados(){


        if(contReceitaCompleta == 1){

            Query queryReceitaCompleta = FirebaseFirestore.getInstance().collection("Receitas_completas").document(idReceita).collection(nomeReceitaDigitado)
                    .orderBy("nameItem", Query.Direction.ASCENDING);

            FirestoreRecyclerOptions<ItemEstoqueModel> optionsReceitaCompleta = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                    .setQuery(queryReceitaCompleta, ItemEstoqueModel.class)
                    .build();


            adapterItem_0 = new IngredienteAdicionadoAdapter(optionsReceitaCompleta);

        }else if (contMassa == 1){

            Query queryMassa = FirebaseFirestore.getInstance().collection("Massas").document(idReceita).collection(nomeReceitaDigitado)
                    .orderBy("nameItem", Query.Direction.ASCENDING);

            FirestoreRecyclerOptions<ItemEstoqueModel> optionsMassa = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                    .setQuery(queryMassa, ItemEstoqueModel.class)
                    .build();


            adapterItem_0 = new IngredienteAdicionadoAdapter(optionsMassa);

        }else  if (contConbertura == 1){

            Query queryCobertura = FirebaseFirestore.getInstance().collection("Coberturas").document(idReceita).collection(nomeReceitaDigitado)
                    .orderBy("nameItem", Query.Direction.ASCENDING);

            FirestoreRecyclerOptions<ItemEstoqueModel> optionsCobertura = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                    .setQuery(queryCobertura, ItemEstoqueModel.class)
                    .build();


            adapterItem_0 = new IngredienteAdicionadoAdapter(optionsCobertura);

        }



        recyclerViewListItensAdicionados.setHasFixedSize(true);
        recyclerViewListItensAdicionados.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListItensAdicionados.setAdapter(adapterItem_0);
        recyclerViewListItensAdicionados.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        adapterItem_0.setOnItemClickListerner(new IngredienteAdicionadoAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                adapterItem_0.deletarItemIndividual(position);
                listValoresItensAdd.remove(valorConvert);
                adapterItem_0.deletarItemIndividual(position);
            }
        });




    }



    public void verificarDuplicidadeNomeReceita(String nomeDigit) {


       FirebaseFirestore.getInstance().collection("Receitas_completas").whereEqualTo("nomeReceita", nomeDigit)
               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

               String verificarDupIdReceita = "";
               List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


               List<String> listaReceita = new ArrayList<>();

               for (DocumentSnapshot snapshot : snapshotList) {
                   verificarDupIdReceita = snapshot.getId();
                   listaReceita.add(snapshot.getId());
               }

               if (listaReceita.size() > 0) {
                   Toast.makeText(getActivity(), "Receita já existe, favor insira outro nome", Toast.LENGTH_SHORT).show();
                   edit_nome_receita.setText("");
               }else {



                   receitaModel = new ReceitaModel();
                   receitaModel.setIdReceita(verificarDupIdReceita);
                   receitaModel.setNomeReceita(nomeDigit);

                   receitaCompletaDAO = new ReceitaCompletaDAO(getActivity());
                   receitaCompletaDAO.salvarReceita(receitaModel);

                  // receitaModel.salvarReceita();

                   textoIngredientesEstoque.setVisibility(View.VISIBLE);
                   textoIngredientesAdicionados.setVisibility(View.VISIBLE);
                   recyclerViewListItensEstoque.setVisibility(View.VISIBLE);
                   recyclerViewListItensAdicionados.setVisibility(View.VISIBLE);
                   botao_salvar_nome_receita.setVisibility(View.GONE);
                   textValorIngredientes.setVisibility(View.VISIBLE);
                   botao_jaAdicioneiIngredientes.setVisibility(View.VISIBLE);

                   carregarListaItemEstoqueDate();
                   adapterItem.startListening();

               }

           }


       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

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



}
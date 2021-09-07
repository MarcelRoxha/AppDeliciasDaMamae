package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.ReceitasProntasActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasProntasAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ReceitaCompletaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceitasProntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceitasProntasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceitasProntasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceitasProntasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceitasProntasFragment newInstance(String param1, String param2) {
        ReceitasProntasFragment fragment = new ReceitasProntasFragment();
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

    /*Componentes de tela*/

    private RecyclerView recyclerView_Receitas_completas_cadastradas;
    private RecyclerView recyclerView_Massas_cadastradas;
    private RecyclerView recyclerView_Cobeturas_cadastradas;


    /*Firebase*/

    CollectionReference reference = ConfiguracaoFirebase.getFirestor().collection("Receitas_completas");


    /*Classes*/
    private ReceitasProntasAdapter adapterReceitaCompleta;
    public ReceitaModel receitaModel;
    public ReceitaCompletaDAO receitaCompletaDAO;


    /*Variaveis temporárias*/

    String nomeReceitaEdit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_receitas_prontas, container, false);
        //Identificando o componente da tela
        recyclerView_Receitas_completas_cadastradas = view.findViewById(R.id.recycler_receitas_prontas_fragment_id);


        return view;
    }

    public void carregarListaReceitasCompletasCadastradas(){

        Query query = FirebaseFirestore.getInstance().collection("Receitas_completas").orderBy("nomeReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReceitaModel> options = new FirestoreRecyclerOptions.Builder<ReceitaModel>()
                .setQuery(query, ReceitaModel.class)
                .build();

         adapterReceitaCompleta = new ReceitasProntasAdapter(options);

        recyclerView_Receitas_completas_cadastradas.setHasFixedSize(true);
        recyclerView_Receitas_completas_cadastradas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_Receitas_completas_cadastradas.setAdapter(adapterReceitaCompleta);
        //recyclerView_Receitas_completas_cadastradas.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapterReceitaCompleta.setOnItemClickListerner(new ReceitasProntasAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String idRecuperado = documentSnapshot.getId();



                if(idRecuperado != null){

                        carregarAlertaEditarAndDelet(idRecuperado);

                    }



                }


        });



    }


    private void recuperarInformacoes() {

        Toast.makeText(getActivity(), "Recupera Informacoes", Toast.LENGTH_SHORT).show();
    }

    private void carregarAlertaEditarAndDelet(String idReceita) {

        String id = idReceita;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("RECEITAS PRONTAS");
        alert.setMessage("Escolha uma das opções");
        alert.setIcon(R.drawable.ic_logo);
        alert.setCancelable(false);
        alert.setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



            }
        }).setNeutralButton("EDITAR/DELETAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                reference.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ReceitaModel receitaModel = documentSnapshot.toObject(ReceitaModel.class);
                        nomeReceitaEdit = receitaModel.getNomeReceita();
                        Intent intent = new Intent(getActivity(), ReceitasProntasActivity.class);
                        intent.putExtra("idReceitaCadastrada", id);
                        intent.putExtra("nomeReceitaEdit", nomeReceitaEdit);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            }
        });
alert.create();
alert.show();



    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaReceitasCompletasCadastradas();

        adapterReceitaCompleta.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        adapterReceitaCompleta.stopListening();
    }
}
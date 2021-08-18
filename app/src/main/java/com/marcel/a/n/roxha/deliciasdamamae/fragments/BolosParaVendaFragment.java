package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.EditarBoloCadastradoVendaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosConfecionadosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasBolosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BolosParaVendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BolosParaVendaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BolosParaVendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BolosParaVendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BolosParaVendaFragment newInstance(String param1, String param2) {
        BolosParaVendaFragment fragment = new BolosParaVendaFragment();
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

    private RecyclerView recyclerViewListBolosConfeccionados;
    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference reference = db.collection("Bolos_Cadastrados_venda");

    private BolosConfecionadosAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_bolos_para_venda, container, false);

        recyclerViewListBolosConfeccionados = view.findViewById(R.id.recycler_lista_bolos_confecionados_id);

        return view;
    }


    public void carregarListaBolosConfecionados(){

        Query query = reference.orderBy("nomeBolo", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<BolosModel> options = new FirestoreRecyclerOptions.Builder<BolosModel>()
                .setQuery(query, BolosModel.class)
                .build();

        adapter = new BolosConfecionadosAdapter(options);
        recyclerViewListBolosConfeccionados.setHasFixedSize(true);
        recyclerViewListBolosConfeccionados.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListBolosConfeccionados.setAdapter(adapter);
        recyclerViewListBolosConfeccionados.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListerner(new ItemEstoqueAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                BolosModel boloClicado = documentSnapshot.toObject(BolosModel.class);

                if(boloClicado != null){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(boloClicado.getNomeBolo());
                    alert.setMessage("Deseja realmente editar esse item?");
                    alert.setCancelable(false);

                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            String id = documentSnapshot.getId();
                            String nomeBoloClicado = boloClicado.getNomeBolo();
                            String custoBoloClicado = boloClicado.getCustoBolo();
                            String precoBoloClicado = boloClicado.getValorVenda();
                            String enderecoFotoBoloClicado = boloClicado.getEnderecoFoto();

                            Intent intent = new Intent(getActivity(), EditarBoloCadastradoVendaActivity.class);
                            intent.putExtra("boloKey", id);
                           /* intent.putExtra("nomeboloKey", nomeBoloClicado);
                            intent.putExtra("preoboloKey", precoBoloClicado);
                            intent.putExtra("custoboloKey", custoBoloClicado);
                            intent.putExtra("custoboloKey", custoBoloClicado);
                            intent.putExtra("fotoboloKey", enderecoFotoBoloClicado);*/
                            startActivity(intent);



                        }
                    }).setNeutralButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {



                        }
                    });
                    alert.create();
                    alert.show();

                }



            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaBolosConfecionados();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosConfecionadosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosConfecionadosExpostosVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BolosVitrineLojaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BolosVitrineLojaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BolosVitrineLojaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BolosVitrineLojaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BolosVitrineLojaFragment newInstance(String param1, String param2) {
        BolosVitrineLojaFragment fragment = new BolosVitrineLojaFragment();
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
    private CollectionReference reference = db.collection("Bolos_Confecionados");

    private BolosConfecionadosExpostosVitrineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bolos_vitrine_loja, container, false);

        recyclerViewListBolosConfeccionados = view.findViewById(R.id.recycler_bolos_add_vitrine_loja_id);

                return view;

    }

    public void carregarListaBolosConfecionadosExpoVitrine(){

        Query query = reference.orderBy("nomeBolo", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<BolosModel> options = new FirestoreRecyclerOptions.Builder<BolosModel>()
                .setQuery(query, BolosModel.class)
                .build();



        adapter = new BolosConfecionadosExpostosVitrineAdapter(options);

        recyclerViewListBolosConfeccionados.setHasFixedSize(true);
        recyclerViewListBolosConfeccionados.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListBolosConfeccionados.setAdapter(adapter);
        recyclerViewListBolosConfeccionados.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

    }


    @Override
    public void onStart() {
        super.onStart();
        carregarListaBolosConfecionadosExpoVitrine();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

import static java.lang.System.load;

public class BolosConfecionadosExpostosVitrineAdapter extends FirestoreRecyclerAdapter<BolosModel, BolosConfecionadosExpostosVitrineAdapter.BolosConfeccionadosExpoVitrineViewHolder> {

    private Context context;

    public BolosConfecionadosExpostosVitrineAdapter(@NonNull FirestoreRecyclerOptions<BolosModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosConfeccionadosExpoVitrineViewHolder holder, int position, @NonNull BolosModel model) {

        holder.nomeBoloExpoVitrineAdapter.setText(model.getNomeBolo());
        holder.precoBoloExpoVitrineAdapter.setText(model.getValorVenda());
        holder.quantVendaBoloExpoVitrineAdapter.setText(model.getQuantBoloVenda());


    }

    @NonNull
    @Override
    public BolosConfeccionadosExpoVitrineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_expostos_venda_adapter, parent, false);


        return new BolosConfeccionadosExpoVitrineViewHolder(view);
    }

    public class BolosConfeccionadosExpoVitrineViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeBoloExpoVitrineAdapter;
        private TextView precoBoloExpoVitrineAdapter;
        private TextView quantVendaBoloExpoVitrineAdapter;
        private ImageView imagemSalva;


        public BolosConfeccionadosExpoVitrineViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeBoloExpoVitrineAdapter = itemView.findViewById(R.id.text_nome_bolo_exposto_vitrine_id);
            precoBoloExpoVitrineAdapter = itemView.findViewById(R.id.texto_valor_venda_bolo_cadastrado_id);
            quantVendaBoloExpoVitrineAdapter = itemView.findViewById(R.id.texto_quant_exposto_bolo_vitrine_id);
            imagemSalva = itemView.findViewById(R.id.imagemSalvaBolo_id);

        }
    }
}

package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;

public class BolosVendidosAdapter extends FirestoreRecyclerAdapter<BoloVendidoModel, BolosVendidosAdapter.BolosVendidoViewHolder> {

    private OnItemClickLisener listener;


    public BolosVendidosAdapter(@NonNull FirestoreRecyclerOptions<BoloVendidoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosVendidoViewHolder holder, int position, @NonNull BoloVendidoModel model) {

        holder.textoNome.setText(model.getNomeBolo());
        holder.textoValorVenda.setText(String.valueOf(model.getVelorVenda()));
        holder.textoCusto.setText(String.valueOf(model.getCustoBolo()));


    }

    @NonNull
    @Override
    public BolosVendidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_vendidos_adapter, parent, false);

        return new BolosVendidoViewHolder(view);
    }

    public class BolosVendidoViewHolder extends RecyclerView.ViewHolder {

        TextView textoNome;
        TextView textoValorVenda;
        TextView textoCusto;


        public BolosVendidoViewHolder(@NonNull View itemView) {
            super(itemView);

            textoNome = itemView.findViewById(R.id.nome_bolo_vendido_id);
            textoValorVenda = itemView.findViewById(R.id.valor_venda_bolo_vendido_id);
            textoCusto = itemView.findViewById(R.id.valor_custo_bolo_vendido_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListerner(OnItemClickLisener listerner){

        this.listener =  listerner;

    }
}

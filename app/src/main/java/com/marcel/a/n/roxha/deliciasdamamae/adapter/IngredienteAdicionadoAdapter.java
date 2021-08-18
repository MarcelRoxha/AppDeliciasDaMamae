package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

public class IngredienteAdicionadoAdapter extends FirestoreRecyclerAdapter<ItemEstoqueModel, IngredienteAdicionadoAdapter.IngredienteAdicionadoViewHolder> {

    private OnItemClickLisener listener;

    public IngredienteAdicionadoAdapter(@NonNull FirestoreRecyclerOptions<ItemEstoqueModel> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IngredienteAdicionadoViewHolder holder, int position, @NonNull ItemEstoqueModel model) {

        holder.nomeIngrediente.setText(model.getNameItem());
        holder.quantIngrediente.setText(model.getQuantUsadaReceita());
        holder.custoIngrediente.setText(model.getValorItemPorReceita());

    }

    @NonNull
    @Override
    public IngredienteAdicionadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingrediente_adicionado_adapter, parent, false);


        return new IngredienteAdicionadoViewHolder(view);
    }

    public void deletarItemIndividual(int position){


        final Task<Void> delete = getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class IngredienteAdicionadoViewHolder extends RecyclerView.ViewHolder {

        TextView nomeIngrediente;
        TextView custoIngrediente;
        TextView quantIngrediente;


        public IngredienteAdicionadoViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngrediente = itemView.findViewById(R.id.text_nome_ingrediente_adicionado_adapter_id);
            quantIngrediente = itemView.findViewById(R.id.text_quant_add_item_receita_id);
            custoIngrediente = itemView.findViewById(R.id.text_custo_item_add_receita_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
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

        this.listener = listerner;

    }
}

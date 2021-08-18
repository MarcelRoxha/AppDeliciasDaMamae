package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

public class BolosConfecionadosAdapter extends FirestoreRecyclerAdapter<BolosModel, BolosConfecionadosAdapter.BolosConfecionadoViewHolder> {

    private ItemEstoqueAdapter.OnItemClickLisener listener;
    private ItemEstoqueAdapter.OnLongClickListener listenerLong;

    public BolosConfecionadosAdapter(@NonNull FirestoreRecyclerOptions<BolosModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosConfecionadoViewHolder holder, int position, @NonNull BolosModel model) {

        holder.nomeBolo.setText(model.getNomeBolo());
        holder.custo.setText(model.getCustoBolo());
        holder.valorVenda.setText(model.getValorVenda());

    }

    @NonNull
    @Override
    public BolosConfecionadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_confecionados_adapter, parent, false);

        return new BolosConfecionadoViewHolder(view);
    }

    public class BolosConfecionadoViewHolder extends RecyclerView.ViewHolder {

        TextView nomeBolo;
        TextView custo;
        TextView valorVenda;


        public BolosConfecionadoViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeBolo = itemView.findViewById(R.id.textoNomeBoloAdapter);
            custo = itemView.findViewById(R.id.textoCustoBoloAdapter);
            valorVenda = itemView.findViewById(R.id.textoValorVendaBoloAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);


                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listenerLong != null){
                        listenerLong.onItemClick(getSnapshots().getSnapshot(position), position);

                        return true;
                    }

                    return false;
                }
            });
        }
    }

    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListerner(ItemEstoqueAdapter.OnItemClickLisener listerner){

        this.listener = listerner;

    }

    public interface OnLongClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemLongClickListerner(ItemEstoqueAdapter.OnLongClickListener listenerLong){

        this.listenerLong = listenerLong;

    }
}

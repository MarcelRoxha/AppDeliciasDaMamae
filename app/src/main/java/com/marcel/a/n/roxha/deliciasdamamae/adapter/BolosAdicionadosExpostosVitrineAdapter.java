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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;

public class BolosAdicionadosExpostosVitrineAdapter extends FirestoreRecyclerAdapter<BolosAdicionadosVitrine, BolosAdicionadosExpostosVitrineAdapter.BolosAdicionadosExpostoViewHolder> {


    private Context context;
    private OnItemClickLisener listener;
    private OnLongClickListener listenerLong;


    public BolosAdicionadosExpostosVitrineAdapter(@NonNull FirestoreRecyclerOptions<BolosAdicionadosVitrine> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosAdicionadosExpostoViewHolder holder, int position, @NonNull BolosAdicionadosVitrine model) {


        holder.nome_salvo.setText(model.getNomeBolo());
        holder.preco_salvo.setText(String.valueOf(model.getValorVenda()));

        Glide.with(context).load(model.getEnderecoFotoSalva()).into(holder.foto_salva);





    }

    @NonNull
    @Override
    public BolosAdicionadosExpostoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_adicionados_expostos_vitrine, parent, false);


        return new BolosAdicionadosExpostoViewHolder(view);
    }

    public class BolosAdicionadosExpostoViewHolder extends RecyclerView.ViewHolder {


        ImageView foto_salva;
        TextView nome_salvo;
        TextView preco_salvo;

        public BolosAdicionadosExpostoViewHolder(@NonNull View itemView) {
            super(itemView);


            foto_salva = itemView.findViewById(R.id.foto_salva_bolo_adicionado_exposto_vitrine_id);
            nome_salvo = itemView.findViewById(R.id.texto_nome_bolo_adicionado_exposto_vitrine_id);
            preco_salvo = itemView.findViewById(R.id.texto_preco_bolo_adicionado_exposto_vitrine_id);



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

    public void deletarItemIndividual(int position){


        final Task<Void> delete = getSnapshots().getSnapshot(position).getReference().delete();
    }

    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListerner(OnItemClickLisener listerner){

        this.listener = listerner;

    }

    public interface OnLongClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemLongClickListerner(OnLongClickListener listenerLong){

        this.listenerLong = listenerLong;

    }
}

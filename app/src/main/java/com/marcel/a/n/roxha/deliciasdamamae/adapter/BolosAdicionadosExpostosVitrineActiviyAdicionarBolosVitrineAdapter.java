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
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;

import java.io.Serializable;

public class BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter extends FirestoreRecyclerAdapter<BolosAdicionadosVitrine, BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter.BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder > {

    private OnItemClickLisener listenerBolosAdicionados;

    public BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapter(@NonNull FirestoreRecyclerOptions<BolosAdicionadosVitrine> options) {
        super(options);
    }

    public void deletarBolosAdicionadoIndividual(int position){

        final Task<Void> delete = getSnapshots().getSnapshot(position).getReference().delete();

    }

    @Override
    protected void onBindViewHolder(@NonNull BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder holder, int position, @NonNull BolosAdicionadosVitrine model) {


        holder.nome_salvo_adicionado.setText(model.getNomeBolo());

        String precoTexto = String.valueOf(model.getValorVenda());
        holder.preco_salvo_adicionado.setText(precoTexto);
    }

    @NonNull
    @Override
    public BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_adicionados_expostos_vitrine_activity_adicionar_bolo_vitrine, parent, false);


        return new BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder(view);
    }

    public class BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView nome_salvo_adicionado;
        TextView preco_salvo_adicionado;

        public BolosAdicionadosExpostosVitrineActiviyAdicionarBolosVitrineAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_salvo_adicionado = itemView.findViewById(R.id.nome_bolo_adicionado_exposto_vitrine_adicionar_bolo_vitrine_id);
            preco_salvo_adicionado = itemView.findViewById(R.id.preco_bolo_adicionado_exposto_vitrine_adicionar_bolo_vitrine_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION && listenerBolosAdicionados != null) {
                        listenerBolosAdicionados.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
        }
    }

    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListerner(OnItemClickLisener listerner){

        this.listenerBolosAdicionados = listerner;

    }
}

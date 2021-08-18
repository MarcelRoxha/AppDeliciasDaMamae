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
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class ReceitasBolosAdapter extends FirestoreRecyclerAdapter<ReceitaModel, ReceitasBolosAdapter.ReceitasBolosViewHolder> {

    private ReceitasBolosAdapter.OnItemClickLisener listener;

    public ReceitasBolosAdapter(@NonNull FirestoreRecyclerOptions<ReceitaModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceitasBolosViewHolder holder, int position, @NonNull ReceitaModel model) {

        holder.nomeReceita.setText(model.getNomeReceita());
        holder.custoReceita.setText(model.getValorTotalReceita());


    }

    @NonNull
    @Override
    public ReceitasBolosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_receitas_bolos_adapter, parent, false);


        return new ReceitasBolosViewHolder(view);
    }

    public class ReceitasBolosViewHolder extends RecyclerView.ViewHolder {

        TextView nomeReceita;
        TextView custoReceita;

        public ReceitasBolosViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeReceita = itemView.findViewById(R.id.textViewNomeReceitaBoloAdapter_id);
            custoReceita = itemView.findViewById(R.id.textViewCustoReceitaBoloAdapter_id);

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
    public void setOnItemClickListerner(ReceitasBolosAdapter.OnItemClickLisener listerner){

        this.listener = listerner;

    }
}

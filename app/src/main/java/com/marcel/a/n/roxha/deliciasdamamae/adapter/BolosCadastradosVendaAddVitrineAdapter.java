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
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

public class BolosCadastradosVendaAddVitrineAdapter extends FirestoreRecyclerAdapter<BolosModel,BolosCadastradosVendaAddVitrineAdapter.BolosCadastradosVendaAddVitrineViewHolder > {

    private Context context;
    private IngredienteAdapter.OnItemClickLisener listenerBolos;


    public BolosCadastradosVendaAddVitrineAdapter(@NonNull FirestoreRecyclerOptions<BolosModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosCadastradosVendaAddVitrineViewHolder holder, int position, @NonNull BolosModel model) {

        holder.textoNome.setText(model.getNomeBolo());
        holder.textoPreco.setText(model.getValorVenda());
        holder.textoCusto.setText(model.getCustoBolo());

           Glide.with(context).load(model.getEnderecoFoto()).into(holder.imageViewBoloCadastradoVendaAddVitrine);



    }

    @NonNull
    @Override
    public BolosCadastradosVendaAddVitrineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bolos_cadastrados_vendas_add_vitrine_adapter, parent, false);

        return new BolosCadastradosVendaAddVitrineViewHolder(view);
    }

    public class BolosCadastradosVendaAddVitrineViewHolder extends RecyclerView.ViewHolder {


        ImageView imageViewBoloCadastradoVendaAddVitrine;
        TextView textoNome;
        TextView textoPreco;
        TextView textoCusto;

        public BolosCadastradosVendaAddVitrineViewHolder(@NonNull View itemView) {
            super(itemView);


            imageViewBoloCadastradoVendaAddVitrine = itemView.findViewById(R.id.foto_bolo_cadastrado_venda_id);
            textoNome = itemView.findViewById(R.id.nome_bolo_cadastrado_venda_add_vitrine_id);
            textoPreco = itemView.findViewById(R.id.valor_bolo_cadastrado_venda_add_vitrine_id);
            textoCusto = itemView.findViewById(R.id.custo_bolo_cadastrado_venda_add_vitrine_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION && listenerBolos != null){
                        listenerBolos.onItemClick(getSnapshots().getSnapshot(position), position);

                    }

                }
            });
        }
    }


    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListerner(IngredienteAdapter.OnItemClickLisener listerner){
        this.listenerBolos =  listerner;


    }
}

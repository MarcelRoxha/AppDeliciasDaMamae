package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

import org.w3c.dom.Text;

public class IngredientesReceitaProntaAdapter extends FirestoreRecyclerAdapter<ItemEstoqueModel, IngredientesReceitaProntaAdapter.IngredientesReceitaPontaViewHolder> {

    public IngredientesReceitaProntaAdapter(@NonNull FirestoreRecyclerOptions<ItemEstoqueModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IngredientesReceitaPontaViewHolder holder, int position, @NonNull ItemEstoqueModel model) {

        holder.nomeIngrediente.setText(model.getNameItem());
        holder.quantUsadaIngrediente.setText(model.getQuantUsadaReceita());
        holder.valorIngredienteReceita.setText(model.getValorItemPorReceita());

    }

    @NonNull
    @Override
    public IngredientesReceitaPontaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_ingrediente_receita_pronta_adapter, parent, false);
        
        return new IngredientesReceitaPontaViewHolder(view);
    }

    public class IngredientesReceitaPontaViewHolder extends RecyclerView.ViewHolder {

        TextView nomeIngrediente;
        TextView quantUsadaIngrediente;
        TextView valorIngredienteReceita;

        public IngredientesReceitaPontaViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngrediente = itemView.findViewById(R.id.nome_ingrediente_receita_pronta_id);
            quantUsadaIngrediente = itemView.findViewById(R.id.quant_usada_ingrediente_receita_pronta_id);
            valorIngredienteReceita = itemView.findViewById(R.id.valor_custo_ingrediente_receita_pronta_id);
        }

    }
}

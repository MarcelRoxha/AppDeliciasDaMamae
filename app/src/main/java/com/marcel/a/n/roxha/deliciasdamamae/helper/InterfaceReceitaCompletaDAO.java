package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public interface InterfaceReceitaCompletaDAO {

    public boolean salvarReceita (ReceitaModel receitaModel);
    public boolean atualizarReceita (String idReceitaModel, ReceitaModel receitaModel);
    public boolean deletarReceitaBanco(String idReceitaModel, ReceitaModel receitaModel);
    public boolean adicionarIngredienteEdit(String idReceita, String nomeReceita, String nomeIngrediente, String quantUsadaIngrediente, String custoIngrediente);
    public boolean removerIngredienteEdit(String idReceita, String idIngrediente, String nomeReceita, String nomeIngrediente, String quantUsadaIngrediente, String custoIngrediente);


    public boolean removerIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ReceitaModel receitaModel, ItemEstoqueModel itemEstoqueModel);

}

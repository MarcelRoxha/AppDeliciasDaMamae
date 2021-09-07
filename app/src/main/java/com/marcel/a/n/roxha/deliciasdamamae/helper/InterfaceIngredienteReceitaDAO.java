package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

public interface InterfaceIngredienteReceitaDAO {

    public boolean adicionarIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ItemEstoqueModel itemEstoqueModel);
    public boolean removerIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ItemEstoqueModel itemEstoqueModel);

}

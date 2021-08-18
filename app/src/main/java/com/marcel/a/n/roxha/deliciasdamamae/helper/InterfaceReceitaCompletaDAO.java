package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public interface InterfaceReceitaCompletaDAO {

    public boolean salvarReceita (ReceitaModel receitaModel);
    public boolean atualizarReceita (String idReceitaModel, ReceitaModel receitaModel);
    public boolean deletarReceitaBanco(String idReceitaModel, ReceitaModel receitaModel);
}

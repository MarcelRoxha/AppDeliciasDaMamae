package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.IngredienteModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceitaCompletaDAO implements InterfaceReceitaCompletaDAO {

    //Instanciar Banco de dados;

    //-------->Firestore
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceReceitasCompletas = firebaseFirestore.collection("Receitas_completas");
    CollectionReference referenceIngredienteEstoque = firebaseFirestore.collection("Item_Estoque");
    //-------->Realtime
    DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getReference().child("Receitas_completas");

    //Atributo Contexto
   private Context context;

   //Classes

    public  ReceitaModel receitaModelInformacoes;
    public  ReceitaModel receitaModelRecuperada;

    //Atributo Para estrutura de decisão
    boolean resultadoAdd;
    boolean resultadoUpdate;
    boolean resultadoDelet;
    boolean resultadoAddIngrediente;
    boolean resultadoRemoveIngrediente;

    //Variaveis Temporarias:

    double valorConvertidoIngrediente;
    double resultadoValorTotalReceita;

    /*Variveis Receita adicionarIngredientes*/
    String idReceita;
    String nomeReceita;
    String porcentagemServicoReceita;
    String quantRendiReceita;
    String valorTotalReceita;
    String valorTotalIngredientes;

    /*Variveis Ingredientes adicionarIngredientes*/

    String idIngredienteReceita;
    String nomeIngrediente;
    String custoIngrediente;
    String quanUsadaReceita;




    private List<Double> listValoresItensAdd = new ArrayList<>();

    private String nomeReceitaDigitado;
    /*private String idReceita;*/

    private int contItem = 0;
    private String valorAdicionado;
    private  double valorConvert;
    private  int contReceitaCompleta = 0;
    private  int contMassa = 0;
    private  int contConbertura = 0;
    private String textoIngredientes;
    private String textoValorReceita;


    //Construtor


    public ReceitaCompletaDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean salvarReceita(ReceitaModel receitaModel) {

        try {

            Map<String, Object> receitaSalva = new HashMap<>();

            receitaSalva.put("nomeReceita",receitaModel. getNomeReceita());
            receitaSalva.put("quantRendimentoReceita", receitaModel.getQuantRendimentoReceita());
            receitaSalva.put("valorTotalReceita", receitaModel.getValorTotalReceita());

            referenceReceitasCompletas.add(receitaSalva).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Map<String, Object> receitaAtualizaId = new HashMap<>();

                    receitaAtualizaId.put("idReceita",documentReference.getId());
                    receitaAtualizaId.put("nomeReceita",receitaModel. getNomeReceita());
                    receitaAtualizaId.put("quantRendimentoReceita", receitaModel.getQuantRendimentoReceita());
                    receitaAtualizaId.put("valorTotalReceita", receitaModel.getValorTotalReceita());
                    receitaAtualizaId.put("porcentagemServico", receitaModel.getPorcentagemServico());
                    receitaAtualizaId.put("valoresIngredientes", receitaModel.getValoresIngredientes());

                    referenceReceitasCompletas.document(documentReference.getId()).update(receitaAtualizaId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(context, "Sucesso ao salvar Receita", Toast.LENGTH_SHORT).show();
                            resultadoAdd = true;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, "Erro ao salvar Receita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    resultadoAdd = false;
                }
            });


        }catch (Exception e){
            Log.i("Error Exception ", e.getMessage());
            resultadoAdd = false;
        }
        return resultadoAdd;
    }

    @Override
    public boolean atualizarReceita(String id, ReceitaModel receitaModel) {

        String idRecuperado = id;

        if(id != null){

            try {

                Map<String, Object> receitaAtualizada = new HashMap<>();

                receitaAtualizada.put("idReceita", receitaModel.getIdReceita());
                receitaAtualizada.put("nomeReceita", receitaModel.getNomeReceita());
                receitaAtualizada.put("quantRendimentoReceita",receitaModel.getQuantRendimentoReceita() );
                receitaAtualizada.put("valorTotalReceita", receitaModel.getValorTotalReceita());
                receitaAtualizada.put("valoresIngredientes", receitaModel.getValoresIngredientes());
                receitaAtualizada.put("porcentagemServico", receitaModel.getPorcentagemServico());

                referenceReceitasCompletas.document(id).update(receitaAtualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Sucesso ao atualizar receita", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Não foi possível atualizar a receita", Toast.LENGTH_SHORT).show();
                        Log.i("Erro de Exception firebase : " , e.getMessage());
                    }
                });


            }catch (Exception e){
                Log.i("Erro de Exception : " , e.getMessage());

            }
        }else {
            Log.i("Erro id não recuperado : " , id);
        }
        return false;
    }


    @Override
    public boolean deletarReceitaBanco(String idReceitaModel, ReceitaModel receitaModel) {
        return false;
    }

    @Override
    public boolean adicionarIngredienteEdit(String idReceita,String nomeReceitaAtualiza, String nomeIngrediente, String quantUsadaIngrediente, String custoIngrediente) {

        String nomeReceitaRecebido = nomeReceitaAtualiza;
        String idReceitaRecebido = idReceita;
        String nomeIngredienteRecebido = nomeIngrediente;
        String quantUsadoIngredienteRecebido = quantUsadaIngrediente;
        String custoIngredienteRecebido = custoIngrediente;



        if(idReceitaRecebido != null && nomeIngredienteRecebido != null && quantUsadoIngredienteRecebido!= null
        && custoIngredienteRecebido != null){

            Map<String, Object> ingredienteAdicionado = new HashMap<>();

            ingredienteAdicionado.put("nameItem", nomeIngredienteRecebido);
            ingredienteAdicionado.put("valorItemPorReceita", custoIngredienteRecebido);
            ingredienteAdicionado.put("quantUsadaReceita", quantUsadoIngredienteRecebido);

            referenceReceitasCompletas.document(idReceitaRecebido).collection(nomeReceitaRecebido).add(ingredienteAdicionado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {


                    referenceReceitasCompletas.document(idReceitaRecebido).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ReceitaModel receitaModel = documentSnapshot.toObject(ReceitaModel.class);
                            assert receitaModel != null;
                            nomeReceita = receitaModel.getNomeReceita();
                            valorTotalIngredientes = receitaModel.getValoresIngredientes();
                            valorTotalReceita = receitaModel.getValorTotalReceita();
                            porcentagemServicoReceita = receitaModel.getPorcentagemServico();
                            quantRendiReceita = receitaModel.getQuantRendimentoReceita();


                            String valorIngredientePrepare = custoIngredienteRecebido.replace(",", ".");
                            String valorTotalIngredientesPrepare = valorTotalIngredientes.replace(",", ".");
                            String valorTotalReceitaPrepare = valorTotalReceita.replace(",", ".");


                            double valorTotalReceitaConvertido = Double.parseDouble(valorTotalReceitaPrepare);
                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesPrepare);
                            double valorItemAdicionadoConvertido = Double.parseDouble(valorIngredientePrepare);
                            int porcentConvert = Integer.parseInt(porcentagemServicoReceita);


                            double resultadoTotalIngredientes = valorTotalIngredientesConvertido + valorItemAdicionadoConvertido;
                            double resultadoPorcentagem = (resultadoTotalIngredientes * porcentConvert) / 100;
                            double resultadoTotalReceita = resultadoPorcentagem + resultadoTotalIngredientes ;



                            atualizarReceitaAdicionarIngrediente(idReceita, nomeReceita, porcentagemServicoReceita, quantRendiReceita,
                                    resultadoTotalIngredientes,resultadoTotalReceita);
                            resultadoAddIngrediente = true;









                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.i("Error Exception Firebase: " , e.getMessage());
                            resultadoAddIngrediente = false;
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }else{

        }


        return resultadoAddIngrediente;
    }

    @Override
    public boolean removerIngredienteEdit(String idReceita, String idIngrediente, String nomeReceitaDelete, String nomeIngrediente, String quantUsadaIngrediente, String custoIngrediente) {



        String idIngredienteDelete = idIngrediente;
        String nomeReceitaIngredienteDelete = nomeReceitaDelete;
        String idReceitaRecebido = idReceita;
        String nomeIngredienteRecebido = nomeIngrediente;
        String quantUsadoIngredienteRecebido = quantUsadaIngrediente;
        String custoIngredienteRecebido = custoIngrediente;



        if(idReceitaRecebido != null && nomeIngredienteRecebido != null && quantUsadoIngredienteRecebido!= null
                && custoIngredienteRecebido != null){


            referenceReceitasCompletas.document(idReceitaRecebido).collection(nomeReceitaIngredienteDelete).document(idIngredienteDelete).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    referenceReceitasCompletas.document(idReceitaRecebido).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ReceitaModel receitaModel = documentSnapshot.toObject(ReceitaModel.class);
                            assert receitaModel != null;
                            nomeReceita = receitaModel.getNomeReceita();
                            valorTotalIngredientes = receitaModel.getValoresIngredientes();
                            valorTotalReceita = receitaModel.getValorTotalReceita();
                            porcentagemServicoReceita = receitaModel.getPorcentagemServico();
                            quantRendiReceita = receitaModel.getQuantRendimentoReceita();


                            String valorIngredientePrepare = custoIngredienteRecebido.replace(",", ".");
                            String valorTotalIngredientesPrepare = valorTotalIngredientes.replace(",", ".");
                            String valorTotalReceitaPrepare = valorTotalReceita.replace(",", ".");


                            double valorTotalReceitaConvertido = Double.parseDouble(valorTotalReceitaPrepare);
                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesPrepare);
                            double valorItemAdicionadoConvertido = Double.parseDouble(valorIngredientePrepare);
                            int porcentConvert = Integer.parseInt(porcentagemServicoReceita);


                            double resultadoTotalIngredientes = valorTotalIngredientesConvertido - valorItemAdicionadoConvertido;
                            double resultadoPorcentagem = (resultadoTotalIngredientes * porcentConvert) / 100;
                            double resultadoTotalReceita = resultadoPorcentagem + resultadoTotalIngredientes ;


                            atualizarReceitaRemoverIngrediente(idReceita, nomeReceita, porcentagemServicoReceita, quantRendiReceita,
                                    resultadoTotalIngredientes,resultadoTotalReceita);










                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.i("Error Exception Firebase: " , e.getMessage());

                        }
                    });




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.i("Error Excption Firebase " , e.getMessage());

                }
            });





        }else{

        }



        return resultadoRemoveIngrediente;
    }


    private void atualizarReceitaRemoverIngrediente(String idReceita, String nomeReceita,
                                                      String porcentagemServicoReceita,
                                                      String quantRendiReceita,
                                                      double resultadoTotalIngredientes,
                                                      double resultadoTotalReceita) {

        String idReceitaRecebido = idReceita;
        String nomeReceitaRecebido = nomeReceita;
        String porcentagemServicoReceitaRecebido = porcentagemServicoReceita;
        String quantRendiReceitaRecebido = quantRendiReceita;


        double totalReceita = resultadoTotalReceita;
        double TotalIngredientes = resultadoTotalIngredientes;

        String valorReceitaTotal = String.format("%.2f", totalReceita);
        String totalIngredientes = String.format("%.2f", TotalIngredientes);



        Map<String, Object> receitaEditAddIngrediente = new HashMap<>();

        receitaEditAddIngrediente.put("idReceita",idReceitaRecebido);
        receitaEditAddIngrediente.put("nomeReceita",nomeReceitaRecebido);
        receitaEditAddIngrediente.put("quantRendimentoReceita", quantRendiReceitaRecebido);
        receitaEditAddIngrediente.put("porcentagemServico", porcentagemServicoReceitaRecebido);
        receitaEditAddIngrediente.put("valorTotalReceita", valorReceitaTotal);
        receitaEditAddIngrediente.put("valoresIngredientes",totalIngredientes);

        referenceReceitasCompletas.document(idReceita).update(receitaEditAddIngrediente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(context, "Ingrediente removido com sucesso", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("Erros Exception Firebase: ", e.getMessage());

            }
        });



    }



    private void atualizarReceitaAdicionarIngrediente(String idReceita, String nomeReceita,
                                                      String porcentagemServicoReceita,
                                                      String quantRendiReceita,
                                                      double resultadoTotalIngredientes,
                                                      double resultadoTotalReceita) {

        String idReceitaRecebido = idReceita;
        String nomeReceitaRecebido = nomeReceita;
        String porcentagemServicoReceitaRecebido = porcentagemServicoReceita;
        String quantRendiReceitaRecebido = quantRendiReceita;


        double totalReceita = resultadoTotalReceita;
        double TotalIngredientes = resultadoTotalIngredientes;

        String valorReceitaTotal = String.format("%.2f", totalReceita);
        String totalIngredientes = String.format("%.2f", TotalIngredientes);



        Map<String, Object> receitaEditAddIngrediente = new HashMap<>();

        receitaEditAddIngrediente.put("idReceita",idReceitaRecebido);
        receitaEditAddIngrediente.put("nomeReceita",nomeReceitaRecebido);
        receitaEditAddIngrediente.put("quantRendimentoReceita", quantRendiReceitaRecebido);
        receitaEditAddIngrediente.put("porcentagemServico", porcentagemServicoReceitaRecebido);
        receitaEditAddIngrediente.put("valorTotalReceita", valorReceitaTotal);
        receitaEditAddIngrediente.put("valoresIngredientes",totalIngredientes);

        referenceReceitasCompletas.document(idReceita).update(receitaEditAddIngrediente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(context, "Ingrediente adicionado com sucesso", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("Erros Exception Firebase: ", e.getMessage());

            }
        });



    }




    @Override
    public boolean removerIngredienteReceita(String idReceitaCompleta, String nomReceita, String idIngrediente, ReceitaModel receitaModel, ItemEstoqueModel itemEstoqueModel) {

        String nomeItemAdd = itemEstoqueModel.getNameItem();
        String valorItemAdd = itemEstoqueModel.getValorItemPorReceita();
        String quantItemAdd = itemEstoqueModel.getQuantUsadaReceita();
        String idReceitaRecuperado = idReceitaCompleta;
        String nomReceitaRecuperado = nomReceita;
        String idIngredienteRecuperado = idIngrediente;

        Map<String, Object> ingredietenAdicionadoReceita = new HashMap<>();
        ingredietenAdicionadoReceita.put("nameItem", nomeItemAdd);
        ingredietenAdicionadoReceita.put("valorItemPorReceita", valorItemAdd);
        ingredietenAdicionadoReceita.put("quantUsadaReceita", quantItemAdd);



        return false;
    }



}

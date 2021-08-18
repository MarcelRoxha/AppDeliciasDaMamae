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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ItemEstoqueDAO implements InterfaceItemEstoqueDAO{

    //Instanciar Banco de dados;

    //-------->Firestore
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceItemEstoque = firebaseFirestore.collection("Item_Estoque");
    //-------->Realtime
    DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getReference().child("Item_Estoque");

    //Atributo Contexto
    Context context;

    //Atributo Para estrutura de decisão
    boolean resultadoAdd;
    boolean resultadoUpdate;
    boolean resultadoDelet;

    //Identificador do item para atualização

    public String idRecuperado;

    public ItemEstoqueDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean salvarItemEstoque(ItemEstoqueModel itemEstoqueModel) {

        try{
            Map<String, Object> item = new HashMap<>();

            item.put("nameItem",itemEstoqueModel.getNameItem());
            item.put("versionEstoque", itemEstoqueModel.getVersionEstoque());
            item.put("valorItem", itemEstoqueModel.getValorItem());
            item.put("quantItem", itemEstoqueModel.getQuantItem());
            item.put("unidMedida", itemEstoqueModel.getUnidMedida());
            item.put("valorFracionado", itemEstoqueModel.calcularValorFracionado());
            item.put("valorItemPorReceita", itemEstoqueModel.valorItemPorReceita());
            item.put("quantPacote", itemEstoqueModel.getQuantPacote());
            item.put("quantUsadaReceita", itemEstoqueModel.getQuantUsadaReceita());

            referenceItemEstoque.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());
                    Toast.makeText(context, "Sucesso ao salvar  item estoque", Toast.LENGTH_SHORT).show();
                    resultadoAdd = true;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Erro ao salvar  item estoque", Toast.LENGTH_SHORT).show();
                    resultadoAdd = false;
                }
            });


        }catch (Exception e){
        Log.i("Error Exception: ",e.getMessage());
        return false;
        }

        return resultadoAdd;


    }

    @Override
    public boolean salvarItemEstoqueRealtimeDataBase(ItemEstoqueModel itemEstoqueModel) {


        return false;
    }

    @Override
    public boolean atualizarItemEstoque(String id, ItemEstoqueModel itemEstoqueModel) {

        this.idRecuperado = id;

        if(idRecuperado != null){

            try{

                Map<String, Object> itemAtualiza = new HashMap<>();

                itemAtualiza.put("nameItem",itemEstoqueModel.getNameItem());
                itemAtualiza.put("versionEstoque", itemEstoqueModel.getVersionEstoque());
                itemAtualiza.put("valorItem", itemEstoqueModel.getValorItem());
                itemAtualiza.put("quantItem", itemEstoqueModel.getQuantItem());
                itemAtualiza.put("unidMedida", itemEstoqueModel.getUnidMedida());
                itemAtualiza.put("valorFracionado", itemEstoqueModel.calcularValorFracionado());
                itemAtualiza.put("valorItemPorReceita", itemEstoqueModel.valorItemPorReceita());
                itemAtualiza.put("quantPacote", itemEstoqueModel.getQuantPacote());
                itemAtualiza.put("quantUsadaReceita", itemEstoqueModel.getQuantUsadaReceita());


                referenceItemEstoque.document(idRecuperado).update(itemAtualiza).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(context, "Suceso ao atualizar item", Toast.LENGTH_SHORT).show();
                        resultadoUpdate = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erro ao atualizar item", Toast.LENGTH_SHORT).show();
                        resultadoUpdate = false;
                    }
                });




            }catch (Exception e){

                Log.i("Error: " , e.getMessage());
                resultadoUpdate = false;
            }



        }


        return resultadoUpdate;
    }

    @Override
    public boolean deletarItemEstoque(String id, ItemEstoqueModel itemEstoqueModel) {


        return false;
    }
    //Methodo para inicializar uma lista completa de itens estoque com valores padrão
    public void listaDefault(){

        //Instanciando a farinha de trigo com valores padrão------------Inicio
            ItemEstoqueModel farinhaTrigo = new ItemEstoqueModel();
            farinhaTrigo.setNameItem("Farinha de trigo");
            farinhaTrigo.setQuantItem("1");
            farinhaTrigo.setQuantPacote("5");
            farinhaTrigo.setQuantUsadaReceita("375");
            farinhaTrigo.setUnidMedida("1000");
            farinhaTrigo.setUnidReceita("1");
            farinhaTrigo.setValorItem("9.98");
        //Instanciando a farinha de trigo com valores padrão------------Fim

        //Adicionando ao Realtime DataBase
            firebaseDatabase.push().setValue(farinhaTrigo);

            Map<String, String> FarinhaTrigo = new HashMap<>();

            FarinhaTrigo.put("nameItem", farinhaTrigo.getNameItem());
            FarinhaTrigo.put("versionEstoque", farinhaTrigo.getVersionEstoque());
            FarinhaTrigo.put("valorItem", farinhaTrigo.getValorItem());
            FarinhaTrigo.put("quantItem", farinhaTrigo.getQuantItem());
            FarinhaTrigo.put("unidMedida", farinhaTrigo.getUnidMedida());
            FarinhaTrigo.put("unidReceita", farinhaTrigo.getUnidReceita());
            FarinhaTrigo.put("valorFracionado", farinhaTrigo.calcularValorFracionado());
            FarinhaTrigo.put("valorItemPorReceita", farinhaTrigo.valorItemPorReceita());
            FarinhaTrigo.put("quantPacote", farinhaTrigo.getQuantPacote());
            FarinhaTrigo.put("quantUsadaReceita", farinhaTrigo.getQuantUsadaReceita());

            //Adicionando ao Firestore

            referenceItemEstoque.add(FarinhaTrigo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando o Fuba com valores padrão-----inicio
            ItemEstoqueModel fuba = new ItemEstoqueModel();
            fuba.setNameItem("Fubá");
            fuba.setQuantItem("3.39");
            fuba.setQuantPacote("1");
            fuba.setQuantUsadaReceita("200");
            fuba.setUnidMedida("1000");
            fuba.setUnidReceita("1");
            fuba.setValorItem("2.50");
//Instanciando o Fuba com valores padrão-----fim

        //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(fuba);

            Map<String, String> Fuba = new HashMap<>();

            Fuba.put("nameItem", fuba.getNameItem());
            Fuba.put("versionEstoque", fuba.getVersionEstoque());
            Fuba.put("valorItem", fuba.getValorItem());
            Fuba.put("quantItem", fuba.getQuantItem());
            Fuba.put("unidMedida", fuba.getUnidMedida());
            Fuba.put("unidReceita", fuba.getUnidReceita());
            Fuba.put("valorFracionado", fuba.calcularValorFracionado());
            Fuba.put("valorItemPorReceita", fuba.valorItemPorReceita());
            Fuba.put("quantPacote", fuba.getQuantPacote());
            Fuba.put("quantUsadaReceita", fuba.getQuantUsadaReceita());

            //Adicionando ao Firestore
            referenceItemEstoque.add(Fuba).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            //Instanciando o Ovo com valores padrão---inicio
            ItemEstoqueModel ovo = new ItemEstoqueModel();
            ovo.setNameItem("Ovo");
            ovo.setValorItem("12.00");
            ovo.setQuantItem("1");
            ovo.setUnidMedida("1");
            ovo.setUnidReceita("1");
            ovo.setQuantPacote("30");
            ovo.setQuantUsadaReceita("5");
            //Instanciando o Ovo com valores padrão---fim

            //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(ovo);

            Map<String, String> Ovo = new HashMap<>();

            Ovo.put("nameItem", ovo.getNameItem());
            Ovo.put("versionEstoque", ovo.getVersionEstoque());
            Ovo.put("valorItem", ovo.getValorItem());
            Ovo.put("quantItem", ovo.getQuantItem());
            Ovo.put("unidMedida", ovo.getUnidMedida());
            Ovo.put("unidReceita", ovo.getUnidReceita());
            Ovo.put("valorFracionado", ovo.calcularValorFracionado());
            Ovo.put("valorItemPorReceita", ovo.valorItemPorReceita());
            Ovo.put("quantPacote", ovo.getQuantPacote());
            Ovo.put("quantUsadaReceita", ovo.getQuantUsadaReceita());

            //Adicionando ao Firestore
            referenceItemEstoque.add(Ovo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando Fermento com valores padrão---inicio
            ItemEstoqueModel fermento = new ItemEstoqueModel();

            fermento.setNameItem("Fermento");
            fermento.setValorItem("1.79");
            fermento.setQuantItem("1");
            fermento.setUnidMedida("1");
            fermento.setUnidReceita("1");
            fermento.setQuantPacote("100");
            fermento.setQuantUsadaReceita("30");
            //Instanciando Fermento com valores padrão---fim

            //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(fermento);

            Map<String, String> Fermento = new HashMap<>();

            Fermento.put("nameItem", fermento.getNameItem());
            Fermento.put("versionEstoque", fermento.getVersionEstoque());
            Fermento.put("valorItem", fermento.getValorItem());
            Fermento.put("quantItem", fermento.getQuantItem());
            Fermento.put("unidMedida", fermento.getUnidMedida());
            Fermento.put("unidReceita", fermento.getUnidReceita());
            Fermento.put("valorFracionado", fermento.calcularValorFracionado());
            Fermento.put("valorItemPorReceita", fermento.valorItemPorReceita());
            Fermento.put("quantPacote", fermento.getQuantPacote());
            Fermento.put("quantUsadaReceita", fermento.getQuantUsadaReceita());


            //Adicionando ao Firestore
            referenceItemEstoque.add(Fermento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            //Instanciando a Essencia com valores padrão---inicio
            ItemEstoqueModel essencia = new ItemEstoqueModel();

            essencia.setNameItem("Essência");
            essencia.setValorItem("3.00");
            essencia.setQuantItem("1");
            essencia.setUnidMedida("1");
            essencia.setUnidReceita("1");
            essencia.setQuantPacote("30");
            essencia.setQuantUsadaReceita("15");
            //Instanciando a Essencia com valores padrão---fim

            //Adicionando ao RealtimeDatabase
             firebaseDatabase.push().setValue(essencia);

            Map<String, String> Essencia = new HashMap<>();

            Essencia.put("nameItem", essencia.getNameItem());
            Essencia.put("versionEstoque", essencia.getVersionEstoque());
            Essencia.put("valorItem", essencia.getValorItem());
            Essencia.put("quantItem", essencia.getQuantItem());
            Essencia.put("unidMedida", essencia.getUnidMedida());
            Essencia.put("unidReceita", essencia.getUnidReceita());
            Essencia.put("valorFracionado", essencia.calcularValorFracionado());
            Essencia.put("valorItemPorReceita", essencia.valorItemPorReceita());
            Essencia.put("quantPacote", essencia.getQuantPacote());
            Essencia.put("quantUsadaReceita", essencia.getQuantUsadaReceita());

            //Adicionando Firestore
            referenceItemEstoque.add(Essencia).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando Açucar com valores padrão---inicio
            ItemEstoqueModel acucar = new ItemEstoqueModel();

            acucar.setNameItem("Açucar");
            acucar.setValorItem("1.50");
            acucar.setQuantItem("1");
            acucar.setUnidMedida("1000");
            acucar.setUnidReceita("1");
            acucar.setQuantPacote("1");
            acucar.setQuantUsadaReceita("400");
        //Instanciando Açucar com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(acucar);

            Map<String, String> Acucar = new HashMap<>();

            Acucar.put("nameItem", acucar.getNameItem());
            Acucar.put("versionEstoque", acucar.getVersionEstoque());
            Acucar.put("valorItem", acucar.getValorItem());
            Acucar.put("quantItem", acucar.getQuantItem());
            Acucar.put("unidMedida", acucar.getUnidMedida());
            Acucar.put("unidReceita", acucar.getUnidReceita());
            Acucar.put("valorFracionado", acucar.calcularValorFracionado());
            Acucar.put("valorItemPorReceita", acucar.valorItemPorReceita());
            Acucar.put("quantPacote", acucar.getQuantPacote());
            Acucar.put("quantUsadaReceita", acucar.getQuantUsadaReceita());

            //Adicionando Firestore
            referenceItemEstoque.add(Acucar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando Leite com valores padrão---inicio
            ItemEstoqueModel leite = new ItemEstoqueModel();

            leite.setNameItem("Leite");
            leite.setValorItem("2.14");
            leite.setQuantItem("1");
            leite.setUnidMedida("1000");
            leite.setUnidReceita("1");
            leite.setQuantPacote("1");
            leite.setQuantUsadaReceita("250");
            //Instanciando Leite com valores padrão---fim

                //Adicionando ao RealtimeDatabase
                firebaseDatabase.push().setValue(leite);


            Map<String, String> Leite = new HashMap<>();

            Leite.put("nameItem", leite.getNameItem());
            Leite.put("versionEstoque", leite.getVersionEstoque());
            Leite.put("valorItem", leite.getValorItem());
            Leite.put("quantItem", leite.getQuantItem());
            Leite.put("unidMedida", leite.getUnidMedida());
            Leite.put("unidReceita", leite.getUnidReceita());
            Leite.put("valorFracionado", leite.calcularValorFracionado());
            Leite.put("valorItemPorReceita", leite.valorItemPorReceita());
            Leite.put("quantPacote", leite.getQuantPacote());
            Leite.put("quantUsadaReceita", leite.getQuantUsadaReceita());

            //Adicionando ao Firestore
            referenceItemEstoque.add(Leite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando Oleo com valores padrão---inicio
            ItemEstoqueModel oleo = new ItemEstoqueModel();

            oleo.setNameItem("Óleo");
            oleo.setValorItem("2.89");
            oleo.setQuantItem("1");
            oleo.setUnidMedida("1000");
            oleo.setUnidReceita("1");
            oleo.setQuantPacote("1");
            oleo.setQuantUsadaReceita("250");
        //Instanciando Oleo com valores padrão---fim

        //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(oleo);

            Map<String, String> Oleo = new HashMap<>();

            Oleo.put("nameItem", oleo.getNameItem());
            Oleo.put("versionEstoque", oleo.getVersionEstoque());
            Oleo.put("valorItem", oleo.getValorItem());
            Oleo.put("quantItem", oleo.getQuantItem());
            Oleo.put("unidMedida", oleo.getUnidMedida());
            Oleo.put("unidReceita", oleo.getUnidReceita());
            Oleo.put("valorFracionado", oleo.calcularValorFracionado());
            Oleo.put("valorItemPorReceita", oleo.valorItemPorReceita());
            Oleo.put("quantPacote", oleo.getQuantPacote());
            Oleo.put("quantUsadaReceita", oleo.getQuantUsadaReceita());
        //Adicionando ao Firestore
            referenceItemEstoque.add(Oleo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando antimofo com valores padrão---inicio
            ItemEstoqueModel antimofo = new ItemEstoqueModel();

            antimofo.setNameItem("Anti Mofo");
            antimofo.setValorItem("2.00");
            antimofo.setQuantItem("1");
            antimofo.setUnidMedida("1");
            antimofo.setUnidReceita("1");
            antimofo.setQuantPacote("50");
            antimofo.setQuantUsadaReceita("15");
            //Instanciando antimofo com valores padrão---fim

            //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(antimofo);

        Map<String, String> AntiMofo = new HashMap<>();

            AntiMofo.put("nameItem", antimofo.getNameItem());
            AntiMofo.put("versionEstoque", antimofo.getVersionEstoque());
            AntiMofo.put("valorItem", antimofo.getValorItem());
            AntiMofo.put("quantItem", antimofo.getQuantItem());
            AntiMofo.put("unidMedida", antimofo.getUnidMedida());
            AntiMofo.put("unidReceita", antimofo.getUnidReceita());
            AntiMofo.put("valorFracionado", antimofo.calcularValorFracionado());
            AntiMofo.put("valorItemPorReceita", antimofo.valorItemPorReceita());
            AntiMofo.put("quantPacote", antimofo.getQuantPacote());
            AntiMofo.put("quantUsadaReceita", antimofo.getQuantUsadaReceita());

            //Adicionando ao Firestore
           referenceItemEstoque.add(AntiMofo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        //Instanciando queijoRalado com valores padrão---inicio
            ItemEstoqueModel queijoRalado = new ItemEstoqueModel();

            queijoRalado.setNameItem("Queijo Ralado");
            queijoRalado.setValorItem("2.00");
            queijoRalado.setQuantItem("1");
            queijoRalado.setUnidMedida("1");
            queijoRalado.setUnidReceita("1");
            queijoRalado.setQuantPacote("50");
            queijoRalado.setQuantUsadaReceita("50");
        //Instanciando queijoRalado com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(queijoRalado);


            Map<String, String> QueijoRalado = new HashMap<>();

            QueijoRalado.put("nameItem", queijoRalado.getNameItem());
            QueijoRalado.put("versionEstoque", queijoRalado.getVersionEstoque());
            QueijoRalado.put("valorItem", queijoRalado.getValorItem());
            QueijoRalado.put("quantItem", queijoRalado.getQuantItem());
            QueijoRalado.put("unidMedida", queijoRalado.getUnidMedida());
            QueijoRalado.put("unidReceita", queijoRalado.getUnidReceita());
            QueijoRalado.put("valorFracionado", queijoRalado.calcularValorFracionado());
            QueijoRalado.put("valorItemPorReceita", queijoRalado.valorItemPorReceita());
            QueijoRalado.put("quantPacote", queijoRalado.getQuantPacote());
            QueijoRalado.put("quantUsadaReceita", queijoRalado.getQuantUsadaReceita());

        //Adicionando ao Firestore
           referenceItemEstoque.add(QueijoRalado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando sazonMassa com valores padrão---inicio
            ItemEstoqueModel sazonMassa = new ItemEstoqueModel();

            sazonMassa.setNameItem("Sazon Massa");
            sazonMassa.setValorItem("2.00");
            sazonMassa.setQuantItem("1");
            sazonMassa.setUnidMedida("1");
            sazonMassa.setUnidReceita("1");
            sazonMassa.setQuantPacote("60");
            sazonMassa.setQuantUsadaReceita("5");
            //Instanciando sazonMassa com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(sazonMassa);

            Map<String, String> SazonMassa = new HashMap<>();

            SazonMassa.put("nameItem", sazonMassa.getNameItem());
            SazonMassa.put("versionEstoque", sazonMassa.getVersionEstoque());
            SazonMassa.put("valorItem", sazonMassa.getValorItem());
            SazonMassa.put("quantItem", sazonMassa.getQuantItem());
            SazonMassa.put("unidMedida", sazonMassa.getUnidMedida());
            SazonMassa.put("unidReceita", sazonMassa.getUnidReceita());
            SazonMassa.put("valorFracionado", sazonMassa.calcularValorFracionado());
            SazonMassa.put("valorItemPorReceita", sazonMassa.valorItemPorReceita());
            SazonMassa.put("quantPacote", sazonMassa.getQuantPacote());
            SazonMassa.put("quantUsadaReceita", sazonMassa.getQuantUsadaReceita());

        //Adicionando ao Firestore
           referenceItemEstoque.add(SazonMassa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            //Instanciando acucarMascavo com valores padrão---inicio
            ItemEstoqueModel acucarMascavo = new ItemEstoqueModel();

            acucarMascavo.setNameItem("Açucar Mascavo");
            acucarMascavo.setValorItem("9.00");
            acucarMascavo.setQuantItem("1");
            acucarMascavo.setUnidMedida("1000");
            acucarMascavo.setUnidReceita("1");
            acucarMascavo.setQuantPacote("1");
            acucarMascavo.setQuantUsadaReceita("75");
            //Instanciando acucarMascavo com valores padrão---fim

            //Adicionando ao RealtimeDatabase
            firebaseDatabase.push().setValue(sazonMassa);

            Map<String, String> AcucarMascavo = new HashMap<>();

            AcucarMascavo.put("nameItem", acucarMascavo.getNameItem());
            AcucarMascavo.put("versionEstoque", acucarMascavo.getVersionEstoque());
            AcucarMascavo.put("valorItem", acucarMascavo.getValorItem());
            AcucarMascavo.put("quantItem", acucarMascavo.getQuantItem());
            AcucarMascavo.put("unidMedida", acucarMascavo.getUnidMedida());
            AcucarMascavo.put("unidReceita", acucarMascavo.getUnidReceita());
            AcucarMascavo.put("valorFracionado", acucarMascavo.calcularValorFracionado());
            AcucarMascavo.put("valorItemPorReceita", acucarMascavo.valorItemPorReceita());
            AcucarMascavo.put("quantPacote", acucarMascavo.getQuantPacote());
            AcucarMascavo.put("quantUsadaReceita", acucarMascavo.getQuantUsadaReceita());

            //Adicionando ao Firestore
           referenceItemEstoque.add(AcucarMascavo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        //Instanciando manteiga com valores padrão---inicio
            ItemEstoqueModel manteiga = new ItemEstoqueModel();

            manteiga.setNameItem("Manteiga");
            manteiga.setValorItem("2.30");
            manteiga.setQuantItem("1");
            manteiga.setUnidMedida("1");
            manteiga.setUnidReceita("1");
            manteiga.setQuantPacote("500");
            manteiga.setQuantUsadaReceita("30");
        //Instanciando manteiga com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(manteiga);

        Map<String, String> Manteiga = new HashMap<>();

            Manteiga.put("nameItem", manteiga.getNameItem());
            Manteiga.put("versionEstoque", manteiga.getVersionEstoque());
            Manteiga.put("valorItem", manteiga.getValorItem());
            Manteiga.put("quantItem", manteiga.getQuantItem());
            Manteiga.put("unidMedida", manteiga.getUnidMedida());
            Manteiga.put("unidReceita", manteiga.getUnidReceita());
            Manteiga.put("valorFracionado", manteiga.calcularValorFracionado());
            Manteiga.put("valorItemPorReceita", manteiga.valorItemPorReceita());
            Manteiga.put("quantPacote", manteiga.getQuantPacote());
            Manteiga.put("quantUsadaReceita", manteiga.getQuantUsadaReceita());

            //Adicionando ao Firestore
            referenceItemEstoque.add(Manteiga).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando bicarbonatoSodio com valores padrão---inicio
            ItemEstoqueModel bicarbonatoSodio = new ItemEstoqueModel();

            bicarbonatoSodio.setNameItem("Bicarbonado de Sódio");
            bicarbonatoSodio.setValorItem("1.00");
            bicarbonatoSodio.setQuantItem("1");
            bicarbonatoSodio.setUnidMedida("1");
            bicarbonatoSodio.setUnidReceita("1");
            bicarbonatoSodio.setQuantPacote("30");
            bicarbonatoSodio.setQuantUsadaReceita("10");
        //Instanciando bicarbonatoSodio com valores padrão---fim


        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(bicarbonatoSodio);


            Map<String, String> BicarbonadoSodio = new HashMap<>();

            BicarbonadoSodio.put("nameItem", bicarbonatoSodio.getNameItem());
            BicarbonadoSodio.put("versionEstoque", bicarbonatoSodio.getVersionEstoque());
            BicarbonadoSodio.put("valorItem", bicarbonatoSodio.getValorItem());
            BicarbonadoSodio.put("quantItem", bicarbonatoSodio.getQuantItem());
            BicarbonadoSodio.put("unidMedida", bicarbonatoSodio.getUnidMedida());
            BicarbonadoSodio.put("unidReceita", bicarbonatoSodio.getUnidReceita());
            BicarbonadoSodio.put("valorFracionado", bicarbonatoSodio.calcularValorFracionado());
            BicarbonadoSodio.put("valorItemPorReceita", bicarbonatoSodio.valorItemPorReceita());
            BicarbonadoSodio.put("quantPacote", bicarbonatoSodio.getQuantPacote());
            BicarbonadoSodio.put("quantUsadaReceita", bicarbonatoSodio.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(BicarbonadoSodio).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando canelaPo com valores padrão---inicio
            ItemEstoqueModel canelaPo = new ItemEstoqueModel();

            canelaPo.setNameItem("Canela em Pó");
            canelaPo.setValorItem("4.00");
            canelaPo.setQuantItem("1");
            canelaPo.setUnidMedida("1");
            canelaPo.setUnidReceita("1");
            canelaPo.setQuantPacote("30");
            canelaPo.setQuantUsadaReceita("10");
        //Instanciando canelaPo com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(canelaPo);

            Map<String, String> CanelaPo = new HashMap<>();

            CanelaPo.put("nameItem", canelaPo.getNameItem());
            CanelaPo.put("versionEstoque", canelaPo.getVersionEstoque());
            CanelaPo.put("valorItem", canelaPo.getValorItem());
            CanelaPo.put("quantItem", canelaPo.getQuantItem());
            CanelaPo.put("unidMedida", canelaPo.getUnidMedida());
            CanelaPo.put("unidReceita", canelaPo.getUnidReceita());
            CanelaPo.put("valorFracionado", canelaPo.calcularValorFracionado());
            CanelaPo.put("valorItemPorReceita", canelaPo.valorItemPorReceita());
            CanelaPo.put("quantPacote", canelaPo.getQuantPacote());
            CanelaPo.put("quantUsadaReceita", canelaPo.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(CanelaPo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando cravoPo com valores padrão---inicio
            ItemEstoqueModel cravoPo = new ItemEstoqueModel();

            cravoPo.setNameItem("Cravo em Pó");
            cravoPo.setValorItem("4.00");
            cravoPo.setQuantItem("1");
            cravoPo.setUnidMedida("1");
            cravoPo.setUnidReceita("1");
            cravoPo.setQuantPacote("30");
            cravoPo.setQuantUsadaReceita("2.5");
        //Instanciando cravoPo com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(cravoPo);


            Map<String, String> CravoPo = new HashMap<>();

            CravoPo.put("nameItem", cravoPo.getNameItem());
            CravoPo.put("versionEstoque", cravoPo.getVersionEstoque());
            CravoPo.put("valorItem", cravoPo.getValorItem());
            CravoPo.put("quantItem", cravoPo.getQuantItem());
            CravoPo.put("unidMedida", cravoPo.getUnidMedida());
            CravoPo.put("unidReceita", cravoPo.getUnidReceita());
            CravoPo.put("valorFracionado", cravoPo.calcularValorFracionado());
            CravoPo.put("valorItemPorReceita", cravoPo.valorItemPorReceita());
            CravoPo.put("quantPacote", cravoPo.getQuantPacote());
            CravoPo.put("quantUsadaReceita", cravoPo.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(CravoPo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando nozMoscada com valores padrão---inicio
            ItemEstoqueModel nozMoscada = new ItemEstoqueModel();

            nozMoscada.setNameItem("Noz-Moscada");
            nozMoscada.setValorItem("3.00");
            nozMoscada.setQuantItem("1");
            nozMoscada.setUnidMedida("1");
            nozMoscada.setUnidReceita("1");
            nozMoscada.setQuantPacote("30");
            nozMoscada.setQuantUsadaReceita("2.5");
            //Instanciando nozMoscada com valores padrão---fim


        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(nozMoscada);


        Map<String, String> NosMoscada = new HashMap<>();

            NosMoscada.put("nameItem", nozMoscada.getNameItem());
            NosMoscada.put("versionEstoque", nozMoscada.getVersionEstoque());
            NosMoscada.put("valorItem", nozMoscada.getValorItem());
            NosMoscada.put("quantItem", nozMoscada.getQuantItem());
            NosMoscada.put("unidMedida", nozMoscada.getUnidMedida());
            NosMoscada.put("unidReceita", nozMoscada.getUnidReceita());
            NosMoscada.put("valorFracionado", nozMoscada.calcularValorFracionado());
            NosMoscada.put("valorItemPorReceita", nozMoscada.valorItemPorReceita());
            NosMoscada.put("quantPacote", nozMoscada.getQuantPacote());
            NosMoscada.put("quantUsadaReceita", nozMoscada.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(NosMoscada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //Instanciando melFavinho com valores padrão---inicio
            ItemEstoqueModel melFavinho = new ItemEstoqueModel();

            melFavinho.setNameItem("Mel Favinho");
            melFavinho.setValorItem("6.39");
            melFavinho.setQuantItem("1");
            melFavinho.setUnidMedida("1000");
            melFavinho.setUnidReceita("1");
            melFavinho.setQuantPacote("1.1");
            melFavinho.setQuantUsadaReceita("150");
        //Instanciando melFavinho com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(melFavinho);


        Map<String, String> MelFavinho = new HashMap<>();

            MelFavinho.put("nameItem", melFavinho.getNameItem());
            MelFavinho.put("versionEstoque", melFavinho.getVersionEstoque());
            MelFavinho.put("valorItem", melFavinho.getValorItem());
            MelFavinho.put("quantItem", melFavinho.getQuantItem());
            MelFavinho.put("unidMedida", melFavinho.getUnidMedida());
            MelFavinho.put("unidReceita", melFavinho.getUnidReceita());
            MelFavinho.put("valorFracionado", melFavinho.calcularValorFracionado());
            MelFavinho.put("valorItemPorReceita", melFavinho.valorItemPorReceita());
            MelFavinho.put("quantPacote", melFavinho.getQuantPacote());
            MelFavinho.put("quantUsadaReceita", melFavinho.getQuantUsadaReceita());


            //Adicionando ao Firestore
           referenceItemEstoque.add(MelFavinho).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



        //Instanciando leiteCondensado com valores padrão---inicio
            ItemEstoqueModel leiteCondensado = new ItemEstoqueModel();

            leiteCondensado.setNameItem("Leite condensado");
            leiteCondensado.setValorItem("2.99");
            leiteCondensado.setQuantItem("1");
            leiteCondensado.setUnidMedida("1");
            leiteCondensado.setUnidReceita("1");
            leiteCondensado.setQuantPacote("395");
            leiteCondensado.setQuantUsadaReceita("395");
        //Instanciando leiteCondensado com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(leiteCondensado);

            Map<String, String> LeiteCondensado = new HashMap<>();

            LeiteCondensado.put("nameItem", leiteCondensado.getNameItem());
            LeiteCondensado.put("versionEstoque", leiteCondensado.getVersionEstoque());
            LeiteCondensado.put("valorItem", leiteCondensado.getValorItem());
            LeiteCondensado.put("quantItem", leiteCondensado.getQuantItem());
            LeiteCondensado.put("unidMedida", leiteCondensado.getUnidMedida());
            LeiteCondensado.put("unidReceita", leiteCondensado.getUnidReceita());
            LeiteCondensado.put("valorFracionado", leiteCondensado.calcularValorFracionado());
            LeiteCondensado.put("valorItemPorReceita", leiteCondensado.valorItemPorReceita());
            LeiteCondensado.put("quantPacote", leiteCondensado.getQuantPacote());
            LeiteCondensado.put("quantUsadaReceita", leiteCondensado.getQuantUsadaReceita());

            //Adicionando ao Firestore
          referenceItemEstoque.add(LeiteCondensado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando cremedeLeite com valores padrão---inicio
            ItemEstoqueModel cremedeLeite = new ItemEstoqueModel();

            cremedeLeite.setNameItem("Creme de Leite");
            cremedeLeite.setValorItem("1.69");
            cremedeLeite.setQuantItem("1");
            cremedeLeite.setUnidMedida("1");
            cremedeLeite.setUnidReceita("1");
            cremedeLeite.setQuantPacote("200");
            cremedeLeite.setQuantUsadaReceita("200");
        //Instanciando cremedeLeite com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(cremedeLeite);

            Map<String, String> CremeDeLeite = new HashMap<>();

            CremeDeLeite.put("nameItem", cremedeLeite.getNameItem());
            CremeDeLeite.put("versionEstoque", cremedeLeite.getVersionEstoque());
            CremeDeLeite.put("valorItem", cremedeLeite.getValorItem());
            CremeDeLeite.put("quantItem", cremedeLeite.getQuantItem());
            CremeDeLeite.put("unidMedida", cremedeLeite.getUnidMedida());
            CremeDeLeite.put("unidReceita", cremedeLeite.getUnidReceita());
            CremeDeLeite.put("valorFracionado", cremedeLeite.calcularValorFracionado());
            CremeDeLeite.put("valorItemPorReceita", cremedeLeite.valorItemPorReceita());
            CremeDeLeite.put("quantPacote", cremedeLeite.getQuantPacote());
            CremeDeLeite.put("quantUsadaReceita", cremedeLeite.getQuantUsadaReceita());

//Adicionando ao Firestore
            referenceItemEstoque.add(CremeDeLeite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando leitePo com valores padrão---inicio
            ItemEstoqueModel leitePo = new ItemEstoqueModel();

            leitePo.setNameItem("Leite em Pó");
            leitePo.setValorItem("8.15");
            leitePo.setQuantItem("1");
            leitePo.setUnidMedida("1");
            leitePo.setUnidReceita("1");
            leitePo.setQuantPacote("400");
            leitePo.setQuantUsadaReceita("120");
        //Instanciando leitePo com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(leitePo);

        Map<String, String> LeitePo = new HashMap<>();

            LeitePo.put("nameItem", leitePo.getNameItem());
            LeitePo.put("versionEstoque", leitePo.getVersionEstoque());
            LeitePo.put("valorItem", leitePo.getValorItem());
            LeitePo.put("quantItem", leitePo.getQuantItem());
            LeitePo.put("unidMedida", leitePo.getUnidMedida());
            LeitePo.put("unidReceita", leitePo.getUnidReceita());
            LeitePo.put("valorFracionado", leitePo.calcularValorFracionado());
            LeitePo.put("valorItemPorReceita", leitePo.valorItemPorReceita());
            LeitePo.put("quantPacote", leitePo.getQuantPacote());
            LeitePo.put("quantUsadaReceita", leitePo.getQuantUsadaReceita());

        //Adicionando ao Firestore
           referenceItemEstoque.add(LeitePo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando leiteCoco com valores padrão---inicio
            ItemEstoqueModel leiteCoco = new ItemEstoqueModel();

            leiteCoco.setNameItem("Leite de coco ");
            leiteCoco.setValorItem("2.00");
            leiteCoco.setQuantItem("1");
            leiteCoco.setUnidMedida("1");
            leiteCoco.setUnidReceita("1");
            leiteCoco.setQuantPacote("200");
            leiteCoco.setQuantUsadaReceita("200");
        //Instanciando leiteCoco com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(leiteCoco);


            Map<String, String> LeiteCoco = new HashMap<>();

            LeiteCoco.put("nameItem", leiteCoco.getNameItem());
            LeiteCoco.put("versionEstoque", leiteCoco.getVersionEstoque());
            LeiteCoco.put("valorItem", leiteCoco.getValorItem());
            LeiteCoco.put("quantItem", leiteCoco.getQuantItem());
            LeiteCoco.put("unidMedida", leiteCoco.getUnidMedida());
            LeiteCoco.put("unidReceita", leiteCoco.getUnidReceita());
            LeiteCoco.put("valorFracionado", leiteCoco.calcularValorFracionado());
            LeiteCoco.put("valorItemPorReceita", leiteCoco.valorItemPorReceita());
            LeiteCoco.put("quantPacote", leiteCoco.getQuantPacote());
            LeiteCoco.put("quantUsadaReceita", leiteCoco.getQuantUsadaReceita());

//Adicionando ao Firestore
          referenceItemEstoque.add(LeiteCoco).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando cocoRalado com valores padrão---inicio
            ItemEstoqueModel cocoRalado = new ItemEstoqueModel();

            cocoRalado.setNameItem("Coco ralado");
            cocoRalado.setValorItem("1.95");
            cocoRalado.setQuantItem("1");
            cocoRalado.setUnidMedida("1");
            cocoRalado.setUnidReceita("1");
            cocoRalado.setQuantPacote("100");
            cocoRalado.setQuantUsadaReceita("100");
        //Instanciando cocoRalado com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(cocoRalado);

            Map<String, String> CocoRalado = new HashMap<>();

            CocoRalado.put("nameItem", cocoRalado.getNameItem());
            CocoRalado.put("versionEstoque", cocoRalado.getVersionEstoque());
            CocoRalado.put("valorItem", cocoRalado.getValorItem());
            CocoRalado.put("quantItem", cocoRalado.getQuantItem());
            CocoRalado.put("unidMedida", cocoRalado.getUnidMedida());
            CocoRalado.put("unidReceita", cocoRalado.getUnidReceita());
            CocoRalado.put("valorFracionado", cocoRalado.calcularValorFracionado());
            CocoRalado.put("valorItemPorReceita", cocoRalado.valorItemPorReceita());
            CocoRalado.put("quantPacote", cocoRalado.getQuantPacote());
            CocoRalado.put("quantUsadaReceita", cocoRalado.getQuantUsadaReceita());

            //Adicionando ao Firestore
            referenceItemEstoque.add(CocoRalado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
//Instanciando maizena com valores padrão---inicio
            ItemEstoqueModel maizena = new ItemEstoqueModel();

            maizena.setNameItem("Maizena");
            maizena.setValorItem("4.15");
            maizena.setQuantItem("1");
            maizena.setUnidMedida("1");
            maizena.setUnidReceita("1");
            maizena.setQuantPacote("500");
            maizena.setQuantUsadaReceita("30");
//Instanciando maizena com valores padrão---fim


        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(maizena);


        Map<String, String> Maizena = new HashMap<>();

            Maizena.put("nameItem", maizena.getNameItem());
            Maizena.put("versionEstoque", maizena.getVersionEstoque());
            Maizena.put("valorItem", maizena.getValorItem());
            Maizena.put("quantItem", maizena.getQuantItem());
            Maizena.put("unidMedida", maizena.getUnidMedida());
            Maizena.put("unidReceita", maizena.getUnidReceita());
            Maizena.put("valorFracionado", maizena.calcularValorFracionado());
            Maizena.put("valorItemPorReceita", maizena.valorItemPorReceita());
            Maizena.put("quantPacote", maizena.getQuantPacote());
            Maizena.put("quantUsadaReceita", maizena.getQuantUsadaReceita());


            //Adicionando ao Firestore
            referenceItemEstoque.add(Maizena).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando chocolate50porcento com valores padrão---inicio
            ItemEstoqueModel chocolate50porcento = new ItemEstoqueModel();

            chocolate50porcento.setNameItem("Chocolate 50%");
            chocolate50porcento.setValorItem("19.60");
            chocolate50porcento.setQuantItem("1");
            chocolate50porcento.setUnidMedida("1000");
            chocolate50porcento.setUnidReceita("1");
            chocolate50porcento.setQuantPacote("1");
            chocolate50porcento.setQuantUsadaReceita("45");
        //Instanciando chocolate50porcento com valores padrão---fim


        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(chocolate50porcento);


            Map<String, String> Chocolate50Porcento = new HashMap<>();

            Chocolate50Porcento.put("nameItem", chocolate50porcento.getNameItem());
            Chocolate50Porcento.put("versionEstoque", chocolate50porcento.getVersionEstoque());
            Chocolate50Porcento.put("valorItem", chocolate50porcento.getValorItem());
            Chocolate50Porcento.put("quantItem", chocolate50porcento.getQuantItem());
            Chocolate50Porcento.put("unidMedida", chocolate50porcento.getUnidMedida());
            Chocolate50Porcento.put("unidReceita", chocolate50porcento.getUnidReceita());
            Chocolate50Porcento.put("valorFracionado", chocolate50porcento.calcularValorFracionado());
            Chocolate50Porcento.put("valorItemPorReceita", chocolate50porcento.valorItemPorReceita());
            Chocolate50Porcento.put("quantPacote", chocolate50porcento.getQuantPacote());
            Chocolate50Porcento.put("quantUsadaReceita", chocolate50porcento.getQuantUsadaReceita());

        //Adicionando ao Firestore
          referenceItemEstoque.add(Chocolate50Porcento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando leiteRecheios com valores padrão---inicio
            ItemEstoqueModel leiteRecheios = new ItemEstoqueModel();

            leiteRecheios.setNameItem("Leite para recheios");
            leiteRecheios.setValorItem("2.14");
            leiteRecheios.setQuantItem("1");
            leiteRecheios.setUnidMedida("1000");
            leiteRecheios.setUnidReceita("1");
            leiteRecheios.setQuantPacote("1");
            leiteRecheios.setQuantUsadaReceita("480");
        //Instanciando leiteRecheios com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(leiteRecheios);


        Map<String, String> LeiteRecheios = new HashMap<>();

            LeiteRecheios.put("nameItem", leiteRecheios.getNameItem());
            LeiteRecheios.put("versionEstoque", leiteRecheios.getVersionEstoque());
            LeiteRecheios.put("valorItem", leiteRecheios.getValorItem());
            LeiteRecheios.put("quantItem", leiteRecheios.getQuantItem());
            LeiteRecheios.put("unidMedida", leiteRecheios.getUnidMedida());
            LeiteRecheios.put("unidReceita", leiteRecheios.getUnidReceita());
            LeiteRecheios.put("valorFracionado", leiteRecheios.calcularValorFracionado());
            LeiteRecheios.put("valorItemPorReceita", leiteRecheios.valorItemPorReceita());
            LeiteRecheios.put("quantPacote", leiteRecheios.getQuantPacote());
            LeiteRecheios.put("quantUsadaReceita", leiteRecheios.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(LeiteRecheios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando nozMoscada com valores padrão---inicio
            ItemEstoqueModel preparoSobremesaMorango = new ItemEstoqueModel();

            preparoSobremesaMorango.setNameItem("Preparo Sobremesa Morango");
            preparoSobremesaMorango.setValorItem("5.00");
            preparoSobremesaMorango.setQuantItem("1");
            preparoSobremesaMorango.setUnidMedida("1");
            preparoSobremesaMorango.setUnidReceita("1");
            preparoSobremesaMorango.setQuantPacote("100");
            preparoSobremesaMorango.setQuantUsadaReceita("45");
        //Instanciando nozMoscada com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(preparoSobremesaMorango);

            Map<String, String> PrepaSobremesaMorango = new HashMap<>();

            PrepaSobremesaMorango.put("nameItem", preparoSobremesaMorango.getNameItem());
            PrepaSobremesaMorango.put("versionEstoque", preparoSobremesaMorango.getVersionEstoque());
            PrepaSobremesaMorango.put("valorItem", preparoSobremesaMorango.getValorItem());
            PrepaSobremesaMorango.put("quantItem", preparoSobremesaMorango.getQuantItem());
            PrepaSobremesaMorango.put("unidMedida", preparoSobremesaMorango.getUnidMedida());
            PrepaSobremesaMorango.put("unidReceita", preparoSobremesaMorango.getUnidReceita());
            PrepaSobremesaMorango.put("valorFracionado", preparoSobremesaMorango.calcularValorFracionado());
            PrepaSobremesaMorango.put("valorItemPorReceita", preparoSobremesaMorango.valorItemPorReceita());
            PrepaSobremesaMorango.put("quantPacote", preparoSobremesaMorango.getQuantPacote());
            PrepaSobremesaMorango.put("quantUsadaReceita", preparoSobremesaMorango.getQuantUsadaReceita());

        //Adicionando ao Firestore
           referenceItemEstoque.add(PrepaSobremesaMorango).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando preparoSobremesaChocolateBranco com valores padrão---inicio

            ItemEstoqueModel preparoSobremesaChocolateBranco = new ItemEstoqueModel();

            preparoSobremesaChocolateBranco.setNameItem("Preparo Sobremesa Chocolate Branco");
            preparoSobremesaChocolateBranco.setValorItem("3.29");
            preparoSobremesaChocolateBranco.setQuantItem("1");
            preparoSobremesaChocolateBranco.setUnidMedida("1");
            preparoSobremesaChocolateBranco.setUnidReceita("1");
            preparoSobremesaChocolateBranco.setQuantPacote("100");
            preparoSobremesaChocolateBranco.setQuantUsadaReceita("45");
        //Instanciando preparoSobremesaChocolateBranco com valores padrão---fim


        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(preparoSobremesaChocolateBranco);


        Map<String, String> PrepaSobremesaChocolateBranco = new HashMap<>();


            PrepaSobremesaChocolateBranco.put("nameItem", preparoSobremesaChocolateBranco.getNameItem());
            PrepaSobremesaChocolateBranco.put("versionEstoque", preparoSobremesaChocolateBranco.getVersionEstoque());
            PrepaSobremesaChocolateBranco.put("valorItem", preparoSobremesaChocolateBranco.getValorItem());
            PrepaSobremesaChocolateBranco.put("quantItem", preparoSobremesaChocolateBranco.getQuantItem());
            PrepaSobremesaChocolateBranco.put("unidMedida", preparoSobremesaChocolateBranco.getUnidMedida());
            PrepaSobremesaChocolateBranco.put("unidReceita", preparoSobremesaChocolateBranco.getUnidReceita());
            PrepaSobremesaChocolateBranco.put("valorFracionado", preparoSobremesaChocolateBranco.calcularValorFracionado());
            PrepaSobremesaChocolateBranco.put("valorItemPorReceita", preparoSobremesaChocolateBranco.valorItemPorReceita());
            PrepaSobremesaChocolateBranco.put("quantPacote", preparoSobremesaChocolateBranco.getQuantPacote());
            PrepaSobremesaChocolateBranco.put("quantUsadaReceita", preparoSobremesaChocolateBranco.getQuantUsadaReceita());

//Adicionando ao Firestore
            referenceItemEstoque.add(PrepaSobremesaChocolateBranco).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando milho com valores padrão---inicio
            ItemEstoqueModel milho = new ItemEstoqueModel();

            milho.setNameItem("Milho");
            milho.setValorItem("1.79");
            milho.setQuantItem("1");
            milho.setUnidMedida("1");
            milho.setUnidReceita("1");
            milho.setQuantPacote("170");
            milho.setQuantUsadaReceita("170");
        //Instanciando milho com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(milho);

            Map<String, String> Milho = new HashMap<>();


            Milho.put("nameItem", milho.getNameItem());
            Milho.put("versionEstoque", milho.getVersionEstoque());
            Milho.put("valorItem", milho.getValorItem());
            Milho.put("quantItem", milho.getQuantItem());
            Milho.put("unidMedida", milho.getUnidMedida());
            Milho.put("unidReceita", milho.getUnidReceita());
            Milho.put("valorFracionado", milho.calcularValorFracionado());
            Milho.put("valorItemPorReceita", milho.valorItemPorReceita());
            Milho.put("quantPacote", milho.getQuantPacote());
            Milho.put("quantUsadaReceita", milho.getQuantUsadaReceita());


        //Adicionando ao Firestore
            referenceItemEstoque.add(Milho).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando goiabada com valores padrão---inicio
            ItemEstoqueModel goiabada = new ItemEstoqueModel();

            goiabada.setNameItem("Goiabada");
            goiabada.setValorItem("1.15");
            goiabada.setQuantItem("1");
            goiabada.setUnidMedida("1");
            goiabada.setUnidReceita("1");
            goiabada.setQuantPacote("300");
            goiabada.setQuantUsadaReceita("30");
//Instanciando goiabada com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(goiabada);

            Map<String, String> Goiabada = new HashMap<>();


            Goiabada.put("nameItem", goiabada.getNameItem());
            Goiabada.put("versionEstoque", goiabada.getVersionEstoque());
            Goiabada.put("valorItem", goiabada.getValorItem());
            Goiabada.put("quantItem", goiabada.getQuantItem());
            Goiabada.put("unidMedida", goiabada.getUnidMedida());
            Goiabada.put("unidReceita", goiabada.getUnidReceita());
            Goiabada.put("valorFracionado", goiabada.calcularValorFracionado());
            Goiabada.put("valorItemPorReceita", goiabada.valorItemPorReceita());
            Goiabada.put("quantPacote", goiabada.getQuantPacote());
            Goiabada.put("quantUsadaReceita", goiabada.getQuantUsadaReceita());

        //Adicionando ao Firestore
           referenceItemEstoque.add(Goiabada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando ervaDoce com valores padrão---inicio
            ItemEstoqueModel ervaDoce = new ItemEstoqueModel();

            ervaDoce.setNameItem("Erva doce");
            ervaDoce.setValorItem("1.00");
            ervaDoce.setQuantItem("1");
            ervaDoce.setUnidMedida("1");
            ervaDoce.setUnidReceita("1");
            ervaDoce.setQuantPacote("30");
            ervaDoce.setQuantUsadaReceita("10");
//Instanciando ervaDoce com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(ervaDoce);

            Map<String, String> ErvaDoce = new HashMap<>();

            ErvaDoce.put("nameItem", ervaDoce.getNameItem());
            ErvaDoce.put("versionEstoque", ervaDoce.getVersionEstoque());
            ErvaDoce.put("valorItem", ervaDoce.getValorItem());
            ErvaDoce.put("quantItem", ervaDoce.getQuantItem());
            ErvaDoce.put("unidMedida", ervaDoce.getUnidMedida());
            ErvaDoce.put("unidReceita", ervaDoce.getUnidReceita());
            ErvaDoce.put("valorFracionado", ervaDoce.calcularValorFracionado());
            ErvaDoce.put("valorItemPorReceita", ervaDoce.valorItemPorReceita());
            ErvaDoce.put("quantPacote", ervaDoce.getQuantPacote());
            ErvaDoce.put("quantUsadaReceita", ervaDoce.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(ErvaDoce).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando nutella com valores padrão---inicio
            ItemEstoqueModel nutella = new ItemEstoqueModel();

            nutella.setNameItem("Nutella");
            nutella.setValorItem("26.50");
            nutella.setQuantItem("1");
            nutella.setUnidMedida("1");
            nutella.setUnidReceita("1");
            nutella.setQuantPacote("850");
            nutella.setQuantUsadaReceita("200");
//Instanciando nutella com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(nutella);

            Map<String, String> Nutella = new HashMap<>();

            Nutella.put("nameItem", nutella.getNameItem());
            Nutella.put("versionEstoque", nutella.getVersionEstoque());
            Nutella.put("valorItem", nutella.getValorItem());
            Nutella.put("quantItem", nutella.getQuantItem());
            Nutella.put("unidMedida", nutella.getUnidMedida());
            Nutella.put("unidReceita", nutella.getUnidReceita());
            Nutella.put("valorFracionado", nutella.calcularValorFracionado());
            Nutella.put("valorItemPorReceita", nutella.valorItemPorReceita());
            Nutella.put("quantPacote", nutella.getQuantPacote());
            Nutella.put("quantUsadaReceita", nutella.getQuantUsadaReceita());
        //Adicionando ao Firestore
           referenceItemEstoque.add(Nutella).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

//Instanciando frangoDesfiado com valores padrão---inicio
            ItemEstoqueModel frangoDesfiado = new ItemEstoqueModel();

            frangoDesfiado.setNameItem("Frango desfiado");
            frangoDesfiado.setValorItem("9.00");
            frangoDesfiado.setQuantItem("1");
            frangoDesfiado.setUnidMedida("1000");
            frangoDesfiado.setUnidReceita("1");
            frangoDesfiado.setQuantPacote("1");
            frangoDesfiado.setQuantUsadaReceita("1000");
        //Instanciando frangoDesfiado com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(frangoDesfiado);

        Map<String, String> FrangoDesfiado = new HashMap<>();

            FrangoDesfiado.put("nameItem", frangoDesfiado.getNameItem());
            FrangoDesfiado.put("versionEstoque", frangoDesfiado.getVersionEstoque());
            FrangoDesfiado.put("valorItem", frangoDesfiado.getValorItem());
            FrangoDesfiado.put("quantItem", frangoDesfiado.getQuantItem());
            FrangoDesfiado.put("unidMedida", frangoDesfiado.getUnidMedida());
            FrangoDesfiado.put("unidReceita", frangoDesfiado.getUnidReceita());
            FrangoDesfiado.put("valorFracionado", frangoDesfiado.calcularValorFracionado());
            FrangoDesfiado.put("valorItemPorReceita", frangoDesfiado.valorItemPorReceita());
            FrangoDesfiado.put("quantPacote", frangoDesfiado.getQuantPacote());
            FrangoDesfiado.put("quantUsadaReceita", frangoDesfiado.getQuantUsadaReceita());

//Adicionando ao Firestore
            referenceItemEstoque.add(FrangoDesfiado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        //Instanciando cebola com valores padrão---inicio
            ItemEstoqueModel cebola = new ItemEstoqueModel();

            cebola.setNameItem("Cebola");
            cebola.setValorItem("0.50");
            cebola.setQuantItem("1");
            cebola.setUnidMedida("1");
            cebola.setUnidReceita("1");
            cebola.setQuantPacote("1");
            cebola.setQuantUsadaReceita("1");
//Instanciando cebola com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(cebola);


            Map<String, String> Cebola = new HashMap<>();

            Cebola.put("nameItem", cebola.getNameItem());
            Cebola.put("versionEstoque", cebola.getVersionEstoque());
            Cebola.put("valorItem", cebola.getValorItem());
            Cebola.put("quantItem", cebola.getQuantItem());
            Cebola.put("unidMedida", cebola.getUnidMedida());
            Cebola.put("unidReceita", cebola.getUnidReceita());
            Cebola.put("valorFracionado", cebola.calcularValorFracionado());
            Cebola.put("valorItemPorReceita", cebola.valorItemPorReceita());
            Cebola.put("quantPacote", cebola.getQuantPacote());
            Cebola.put("quantUsadaReceita", cebola.getQuantUsadaReceita());

//Adicionando ao Firestore
           referenceItemEstoque.add(Cebola).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando seleta com valores padrão---inicio
            ItemEstoqueModel seleta = new ItemEstoqueModel();

            seleta.setNameItem("Seleta");
            seleta.setValorItem("2.50");
            seleta.setQuantItem("1");
            seleta.setUnidMedida("1");
            seleta.setUnidReceita("1");
            seleta.setQuantPacote("1");
            seleta.setQuantUsadaReceita("1");
        //Instanciando seleta com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(seleta);


        Map<String, String> Seleta = new HashMap<>();

            Seleta.put("nameItem", seleta.getNameItem());
            Seleta.put("versionEstoque", seleta.getVersionEstoque());
            Seleta.put("valorItem", seleta.getValorItem());
            Seleta.put("quantItem", seleta.getQuantItem());
            Seleta.put("unidMedida", seleta.getUnidMedida());
            Seleta.put("unidReceita", seleta.getUnidReceita());
            Seleta.put("valorFracionado", seleta.calcularValorFracionado());
            Seleta.put("valorItemPorReceita", seleta.valorItemPorReceita());
            Seleta.put("quantPacote", seleta.getQuantPacote());
            Seleta.put("quantUsadaReceita", seleta.getQuantUsadaReceita());

        //Adicionando ao Firestore
            referenceItemEstoque.add(Seleta).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando massaTomate com valores padrão---inicio
            ItemEstoqueModel massaTomate = new ItemEstoqueModel();

            massaTomate.setNameItem("Massa de Tomate");
            massaTomate.setValorItem("1.05");
            massaTomate.setQuantItem("1");
            massaTomate.setUnidMedida("1");
            massaTomate.setUnidReceita("1");
            massaTomate.setQuantPacote("1");
            massaTomate.setQuantUsadaReceita("1");
        //Instanciando massaTomate com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(massaTomate);

            Map<String, String> MassaTomate = new HashMap<>();

            MassaTomate.put("nameItem", massaTomate.getNameItem());
            MassaTomate.put("versionEstoque", massaTomate.getVersionEstoque());
            MassaTomate.put("valorItem", massaTomate.getValorItem());
            MassaTomate.put("quantItem", massaTomate.getQuantItem());
            MassaTomate.put("unidMedida", massaTomate.getUnidMedida());
            MassaTomate.put("unidReceita", massaTomate.getUnidReceita());
            MassaTomate.put("valorFracionado", massaTomate.calcularValorFracionado());
            MassaTomate.put("valorItemPorReceita", massaTomate.valorItemPorReceita());
            MassaTomate.put("quantPacote", massaTomate.getQuantPacote());
            MassaTomate.put("quantUsadaReceita", massaTomate.getQuantUsadaReceita());

//Adicionando ao Firestore
            referenceItemEstoque.add(MassaTomate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        //Instanciando azeitonas com valores padrão---inicio
            ItemEstoqueModel azeitonas = new ItemEstoqueModel();

            azeitonas.setNameItem("Azeitonas");
            azeitonas.setValorItem("1.50");
            azeitonas.setQuantItem("1");
            azeitonas.setUnidMedida("1");
            azeitonas.setUnidReceita("1");
            azeitonas.setQuantPacote("50");
            azeitonas.setQuantUsadaReceita("50");
        //Instanciando azeitonas com valores padrão---fim

        //Adicionando ao RealtimeDatabase
        firebaseDatabase.push().setValue(azeitonas);


            Map<String, String> Azeitonas = new HashMap<>();


            Azeitonas.put("nameItem", azeitonas.getNameItem());
            Azeitonas.put("versionEstoque", azeitonas.getVersionEstoque());
            Azeitonas.put("valorItem", azeitonas.getValorItem());
            Azeitonas.put("quantItem", azeitonas.getQuantItem());
            Azeitonas.put("unidMedida", azeitonas.getUnidMedida());
            Azeitonas.put("unidReceita", azeitonas.getUnidReceita());
            Azeitonas.put("valorFracionado", azeitonas.calcularValorFracionado());
            Azeitonas.put("valorItemPorReceita", azeitonas.valorItemPorReceita());
            Azeitonas.put("quantPacote", azeitonas.getQuantPacote());
            Azeitonas.put("quantUsadaReceita", azeitonas.getQuantUsadaReceita());


            //Adicionando ao Firestore
            referenceItemEstoque.add(Azeitonas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.i("Firestore", documentReference.getId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }
}

package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

import java.util.HashMap;
import java.util.Map;

public class AddItemEstoqueActivity extends AppCompatActivity {


    //Componentes de tela:

    //-----------------------------------------------INPUTS-------------------USER
    private TextInputEditText edit_nome_item_estoque;
    private TextInputEditText edit_valor_item_estoque;
    private TextInputEditText edit_quantTotal_item_estoque;
    private TextInputEditText edit_quantPacote_item_estoque;
    private TextInputEditText edit_quantUsadaReceita_item_estoque;

    //-----------------------------------------------RADIOBUTTON-------------------USER
    private RadioButton radioButton_gramas_ml_unid, radioButton_litro_kilo;


    //Classes:
    private ItemEstoqueModel itemEstoqueModel, itemEstoqueModelAtualizar;
    private ItemEstoqueDAO itemEstoqueDAO;

    //Firebase
    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference ref = db.collection("Item_Estoque");


    //Variaveis temporarias:
    private String itemKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);

        setContentView(R.layout.activity_add_item_estoque);


        //----->identificando os componentes da tela de acordo com o id inserido<-----
        edit_nome_item_estoque = findViewById(R.id.edit_nome_item_estoque_id);
        edit_valor_item_estoque = findViewById(R.id.edit_valor_item_estoque_id);
        edit_quantTotal_item_estoque = findViewById(R.id.edit_quant_item_estoque_id);
        edit_quantPacote_item_estoque = findViewById(R.id.edit_quant_pacote_item_estoque_id);
        edit_quantUsadaReceita_item_estoque = findViewById(R.id.edit_quant_usada_receita_id);
        radioButton_gramas_ml_unid = findViewById(R.id.radioButton_grama_ml_und_id);
        radioButton_litro_kilo = findViewById(R.id.radioButton_Litro_Kilo_id);

        //Recuperando a chave do item estoque caso seja atualização
         itemKey = getIntent().getStringExtra("itemKey");

         /*Verificando se o item recuperado é diferente de nulo, pois se for direfente de nulo significa
             que é uma atualização e os inputs e RadionButtons serão preenchidos com os dados*/
        if (itemKey != null) {

            //Quando a condição for verdadeira, será recuperado o item do estoque no banco de dados
            FirebaseFirestore.getInstance().collection("Item_Estoque").document(itemKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                    String undMedidaRecebida = itemEstoqueModel.getUnidMedida();


                    edit_nome_item_estoque.setText(itemEstoqueModel.getNameItem());
                    edit_valor_item_estoque.setText(itemEstoqueModel.getValorItem());
                    edit_quantTotal_item_estoque.setText(itemEstoqueModel.getQuantItem());
                    edit_quantPacote_item_estoque.setText(itemEstoqueModel.getQuantPacote());
                    edit_quantUsadaReceita_item_estoque.setText(itemEstoqueModel.getQuantUsadaReceita());

                    if (undMedidaRecebida.contains("1000")) {
                        radioButton_litro_kilo.toggle();
                    } else  {
                        radioButton_gramas_ml_unid.toggle();
                    }


                }
            });
        }



    }

    //Verificando qual icone será exibido no menu suspenso de acordo com a condição de atualização ou cadastro
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //Verificando a chave para alternar entre os icones
        if(itemKey != null) {
            //Se a condição for verdaderia, será uma atualização, então o icone utilizdo é o de atualização
            getMenuInflater().inflate(R.menu.menu_atualizar_item_estoque, menu);

        }else {
            //Se a condição for falsa, será um cadastro, então o icone utilizado é o de salvar
            getMenuInflater().inflate(R.menu.menu_salvar_item_estoque, menu);
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.salvar_item_estoque:

                String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();

                itemEstoqueModel = new ItemEstoqueModel();

                itemEstoqueModel.setNameItem(nomeItemDigitado);
                itemEstoqueModel.setValorItem(valorItemDigitado);
                itemEstoqueModel.setQuantItem(quantItemDigitado);
                itemEstoqueModel.setQuantPacote(quantPacoteItemDigitado);
                itemEstoqueModel.setQuantUsadaReceita(quantUsadaReceitaItemDigitado);

                if (radioButton_gramas_ml_unid.isChecked()) {
                   itemEstoqueModel.setUnidMedida("1");
                } else if (radioButton_litro_kilo.isChecked()) {
                  itemEstoqueModel.setUnidMedida("1000");
                }
                itemEstoqueDAO = new ItemEstoqueDAO(AddItemEstoqueActivity.this);
                itemEstoqueDAO.salvarItemEstoque(itemEstoqueModel);



            case R.id.bt_atualizar:

                String nomeItemDigitadoAtualiza = edit_nome_item_estoque.getText().toString();
                String valorItemDigitadoAtualiza = edit_valor_item_estoque.getText().toString();
                String quantItemDigitadoAtualiza = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitadoAtualiza = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitadoAtualiza = edit_quantUsadaReceita_item_estoque.getText().toString();

                itemEstoqueModel = new ItemEstoqueModel();

                itemEstoqueModel.setNameItem(nomeItemDigitadoAtualiza);
                itemEstoqueModel.setValorItem(valorItemDigitadoAtualiza);
                itemEstoqueModel.setQuantItem(quantItemDigitadoAtualiza);
                itemEstoqueModel.setQuantPacote(quantPacoteItemDigitadoAtualiza);
                itemEstoqueModel.setQuantUsadaReceita(quantUsadaReceitaItemDigitadoAtualiza);

                if (radioButton_gramas_ml_unid.isChecked()) {
                    itemEstoqueModel.setUnidMedida("1");
                } else if (radioButton_litro_kilo.isChecked()) {
                    itemEstoqueModel.setUnidMedida("1000");
                }
                itemEstoqueDAO = new ItemEstoqueDAO(AddItemEstoqueActivity.this);
                itemEstoqueDAO.atualizarItemEstoque(itemKey, itemEstoqueModel);


               // atualizarItem();


            default:
                carregarTelaEstoque();
                return super.onOptionsItemSelected(item);

        }

    }

    public void carregarTelaEstoque() {
        Intent intent = new Intent(AddItemEstoqueActivity.this, EstoqueActivity.class);
        startActivity(intent);

    }
}
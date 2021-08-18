package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.util.HashMap;
import java.util.Map;

public class PrincipalActivity extends AppCompatActivity {

    private Button botao_tela_estoque;
    private Button botao_tela_producao;
    private Button botao_tela_bolos;
    private Button botao_tela_loja;
    private FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    private DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_principal);

        botao_tela_estoque = findViewById(R.id.bt_tela_estoque_id);

        botao_tela_estoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregarTelaEstoque();
            }
        });

        botao_tela_producao = findViewById(R.id.bt_tela_producao_Id);

        botao_tela_producao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, ProducaoActivity.class);
                startActivity(intent);

            }
        });

        botao_tela_bolos = findViewById(R.id.bt_tela_bolos_Id);

        botao_tela_bolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(PrincipalActivity.this, BolosActivity.class);
                startActivity(intent);

            }
        });

        botao_tela_loja = findViewById(R.id.bt_tela_loja_Id);

        botao_tela_loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, LojaActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_sair_app, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.botao_sair_app :

                FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
                auth.signOut();
                Toast.makeText(this, "Usuário deslogado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void carregarTelaEstoque(){
    Intent intent = new Intent(PrincipalActivity.this, EstoqueActivity.class);
    startActivity(intent);
    }
}
package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.UserModel;

public class MainActivity extends AppCompatActivity {

    private Button botao_logar;
    private TextInputEditText email_login, senha_login;
    private UserModel user = new UserModel();

    private FirebaseAuth auth = ConfiguracaoFirebase.getAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_main);

        verificarUsuarioLogado();

        botao_logar = findViewById(R.id.bt_logar_id);
        email_login = findViewById(R.id.edit_email_logar_id);
        senha_login = findViewById(R.id.edti_senha_logar_id);


        botao_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailUserInserido = email_login.getText().toString();
                String senhaUserInserido = senha_login.getText().toString();

                if(!emailUserInserido.isEmpty() && !senhaUserInserido.isEmpty()){


                    user.setEmailUser(emailUserInserido);
                    user.setSenhaUser(senhaUserInserido);
                    validarLogin();
                }else{

                    Toast.makeText(MainActivity.this, "Favor insira os dados de login", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void verificarUsuarioLogado(){

        auth = ConfiguracaoFirebase.getAuth();
        if (auth.getCurrentUser() != null){

            abrirTelaPrincipal();


        }
    }

    private void validarLogin(){

        auth = ConfiguracaoFirebase.getAuth();

        auth.signInWithEmailAndPassword(
                user.getEmailUser()
                ,user.getSenhaUser()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Sucesso ao fazer login", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

        public void abrirTelaPrincipal(){

            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(intent);
            finish();
        }
}
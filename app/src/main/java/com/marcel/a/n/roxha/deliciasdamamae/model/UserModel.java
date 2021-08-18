package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.util.HashMap;
import java.util.Map;

public class UserModel {

    String id;
    String nameUser;
    String emailUser;
    String senhaUser;

    public UserModel() {
    }

    public UserModel(String id, String nameUser, String emailUser, String senhaUser) {
        this.id = id;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.senhaUser = senhaUser;
    }
    public void salvarUser(){

        DatabaseReference reference = ConfiguracaoFirebase.getReference();
        reference.child("Users").push()
                .setValue(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String , String> user = new HashMap<>();

        user.put("nameUser", getNameUser());
        user.put("emailUser", getEmailUser());

        db.collection("Usuario").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getSenhaUser() {
        return senhaUser;
    }

    public void setSenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }
}

package com.marcel.a.n.roxha.deliciasdamamae.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ConfiguracaoFirebase {

private static DatabaseReference reference;
private static FirebaseFirestore firestore;
private static FirebaseAuth auth;
private static FirebaseStorage storage;

    public static FirebaseFirestore getFirestor(){

        if (firestore == null){
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }

    public static DatabaseReference getReference(){

        if (reference == null){

            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static FirebaseAuth getAuth(){

        if (auth == null){

            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

    public static FirebaseStorage getStorage(){

        if (storage == null){

            storage = FirebaseStorage.getInstance();
        }

        return storage;
    }

}




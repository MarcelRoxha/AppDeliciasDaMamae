package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.BolosActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasBolosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfeccionarBoloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfeccionarBoloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfeccionarBoloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfeccionarBoloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfeccionarBoloFragment newInstance(String param1, String param2) {
        ConfeccionarBoloFragment fragment = new ConfeccionarBoloFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private FirebaseStorage storageReference = ConfiguracaoFirebase.getStorage();
    private StorageReference storageRef;
    private TextView textoNomeBolo;
    private TextView textoQuantRendeFornada;
    private TextView textoCustoTotalBolo;
    private TextView textoValorSugeridoVenda;
    private TextView textoNome;
    private TextView textoNomeAdd;
    private TextView textCusto;
    private TextView textCustoAdd;
    private TextView textoRendi;
    private TextView textoRendiAdd;
    private TextView textValorSuge;
    private TextView textValorSugeAdd;
    private TextView textInfo;
    private ImageView imagemBoloCadastrado;
    private Uri imageUri;


    private String currentPatch;


    private ImageView imagemCapturada;

    private static final int REQUEST_CODE_PERMISSIONS = 1;

    private TextInputEditText editValorVendaBolo;
    private ReceitasBolosAdapter adapter;

    private RecyclerView recyclerViewListaReceitasProntas;

    private String valorSugerido;
    private String nomeReceita;
    private String custoReceita;
    private String quantRendimentoReceita;
    private String currentImagePath = null;
    private String valorVendaDigitado;
    private String enderecoSalvoFoto;
    private double porcentConvert;
    private double custoConvert;
    private double resultado;
    private int verificaGaleriaCamera;

    private static final int REQUEST_CODE_CAMERA = 101;
    private static final int REQUEST_CODE_GALERIA = 100;

    private ReceitaModel receitaRecuperada = new ReceitaModel();

    private Button botaoSalvarBoloVendas;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confeccionar_bolo, container, false);

        recyclerViewListaReceitasProntas = view.findViewById(R.id.recyclerViewReceitasProntasParaBolo_id);
        textoCustoTotalBolo = view.findViewById(R.id.textViewValorCustoBolo_id);
        textoValorSugeridoVenda = view.findViewById(R.id.textViewValorSugeridoVenda_id);
        textoNomeBolo = view.findViewById(R.id.textViewNomBolo_id);
        textoQuantRendeFornada = view.findViewById(R.id.textViewQuantPorFornada_id);
        imagemBoloCadastrado = view.findViewById(R.id.imagem_bolo_cadastrado_id);

        botaoSalvarBoloVendas = view.findViewById(R.id.botao_salvar_receita_venda_id);
        editValorVendaBolo = view.findViewById(R.id.edit_preco_venda_bolo_id);

        textoNome = view.findViewById(R.id.textView18);
        textoNomeAdd = view.findViewById(R.id.textoNome);
        textCusto = view.findViewById(R.id.textView21);
        textCustoAdd = view.findViewById(R.id.textoCustoReceita);
        textoRendi = view.findViewById(R.id.textView22);
        textoRendiAdd = view.findViewById(R.id.textoRendimentoReceitaBolo);
        textValorSuge = view.findViewById(R.id.textView23);
        textValorSugeAdd = view.findViewById(R.id.textoValorSugerido);
        textInfo = view.findViewById(R.id.textView26);


        imagemBoloCadastrado.setVisibility(View.GONE);
        textoCustoTotalBolo.setVisibility(View.GONE);
        ;
        textoValorSugeridoVenda.setVisibility(View.GONE);

        textoQuantRendeFornada.setVisibility(View.GONE);
        ;
        textInfo.setVisibility(View.GONE);
        ;

        botaoSalvarBoloVendas.setVisibility(View.GONE);
        editValorVendaBolo.setVisibility(View.GONE);
        textoNomeBolo.setVisibility(View.GONE);
        textoNome.setVisibility(View.GONE);
        textoNomeAdd.setVisibility(View.GONE);
        textCusto.setVisibility(View.GONE);
        textCustoAdd.setVisibility(View.GONE);
        textoRendi.setVisibility(View.GONE);
        textoRendiAdd.setVisibility(View.GONE);
        textValorSuge.setVisibility(View.GONE);
        textValorSugeAdd.setVisibility(View.GONE);


        storageRef = storageReference.getReference();


        botaoSalvarBoloVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String valorDigitado = editValorVendaBolo.getText().toString();

                if (valorDigitado.isEmpty()) {
                    Toast.makeText(getActivity(), "Favor insira o valor que será vendido o bolo", Toast.LENGTH_SHORT).show();
                } else {

                    String nome;
                    String custo;
                    String quant;


                    nome = receitaRecuperada.getNomeReceita();
                    custo = receitaRecuperada.getValorTotalReceita();
                    quant = receitaRecuperada.getQuantRendimentoReceita();


                    BolosModel boloSalvar = new BolosModel();

                    boloSalvar.setNomeBolo(nome);
                    boloSalvar.setCustoBolo(custo);
                    boloSalvar.setQuantBoloVenda(quant);
                    boloSalvar.setValorVenda(valorDigitado);
                    boloSalvar.setEnderecoFoto(enderecoSalvoFoto);
                    boloSalvar.setVerificaCameraGaleria(verificaGaleriaCamera);


                    FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
                    CollectionReference reference = db.collection("Bolos_Cadastrados_venda");

                    Map<String, Object> boloConfec = new HashMap<>();

                    boloConfec.put("nomeBolo", boloSalvar.getNomeBolo());
                    boloConfec.put("custoBolo", boloSalvar.getCustoBolo());
                    boloConfec.put("quantBoloVenda", "1");
                    boloConfec.put("valorVenda", boloSalvar.getValorVenda());
                    boloConfec.put("enderecoFoto", boloSalvar.getEnderecoFoto());
                    boloConfec.put("verificaCameraGaleria", boloSalvar.getVerificaCameraGaleria());




                    reference.add(boloConfec).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(getActivity(), "Sucesso ao salvar bolo para venda", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), BolosActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }

            }
        });


        return view;
    }

    private void carregarListaReceitasProntas() {

        FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
        CollectionReference reference = db.collection("Receitas_completas");

        Query query = reference.orderBy("nomeReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReceitaModel> options = new FirestoreRecyclerOptions.Builder<ReceitaModel>()
                .setQuery(query, ReceitaModel.class)
                .build();

        adapter = new ReceitasBolosAdapter(options);
        recyclerViewListaReceitasProntas.setHasFixedSize(true);
        recyclerViewListaReceitasProntas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListaReceitasProntas.setAdapter(adapter);
        recyclerViewListaReceitasProntas.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter.setOnItemClickListerner(new ReceitasBolosAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {


                ReceitaModel receitaSelecionada = documentSnapshot.toObject(ReceitaModel.class);

                assert receitaSelecionada != null;


                nomeReceita = receitaSelecionada.getNomeReceita();
                custoReceita = receitaSelecionada.getValorTotalReceita();
                quantRendimentoReceita = receitaSelecionada.getQuantRendimentoReceita();

                receitaRecuperada.setNomeReceita(nomeReceita);
                receitaRecuperada.setValorTotalReceita(custoReceita);
                receitaRecuperada.setQuantRendimentoReceita(quantRendimentoReceita);

                carregarAlertFoto();

            }

        });

    }

    public void carregarAlertFoto() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("FOTO NECESSÁRIA");
        dialog.setMessage("Para cadastrar um produto para venda, é necessário inserir uma foto desse produto, indicamos fotos selecionadas na GALERIA por conta da qualidade da foto.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("CÂMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){


                }else {

                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSIONS);
                    verificaGaleriaCamera = 1;
                    dispatchCaptureImageIntent();
               }



            }
        }).setNeutralButton("GALERIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_GALERIA);
                }
                verificaGaleriaCamera = 2;
                selecionarFoto();

            }
        });
        dialog.create();
        dialog.show();
    }

    public void selecionarFoto() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Foto"), REQUEST_CODE_GALERIA);
    }

    public void carregarAlertPorcent() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("INFORME % DE LUCRO DA RECEITA");
        dialog.setMessage("informe o minimo de % de lucro que deseja obter na venda desse bolo");
        dialog.setCancelable(false);
        EditText texPorcentDig = new EditText(getActivity());
        dialog.setView(texPorcentDig);

        dialog.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String porct = texPorcentDig.getText().toString();


                if (!porct.isEmpty()) {


                    calcularValorSugerido(custoReceita, porct);

                    textoNomeBolo.setVisibility(View.GONE);
                    textoCustoTotalBolo.setVisibility(View.GONE);
                    textoQuantRendeFornada.setVisibility(View.GONE);
                    recyclerViewListaReceitasProntas.setVisibility(View.GONE);


                    imagemBoloCadastrado.setVisibility(View.VISIBLE);
                    textoNome.setVisibility(View.VISIBLE);
                    textoNomeAdd.setVisibility(View.VISIBLE);
                    textCusto.setVisibility(View.VISIBLE);
                    textCustoAdd.setVisibility(View.VISIBLE);
                    textoRendi.setVisibility(View.VISIBLE);
                    textoRendiAdd.setVisibility(View.VISIBLE);
                    textValorSuge.setVisibility(View.VISIBLE);
                    textValorSugeAdd.setVisibility(View.VISIBLE);


                    textoNomeAdd.setText(nomeReceita);
                    textCustoAdd.setText(custoReceita);
                    textoRendiAdd.setText(quantRendimentoReceita);
                    textValorSugeAdd.setText(valorSugerido);


                    textInfo.setVisibility(View.VISIBLE);
                    editValorVendaBolo.setVisibility(View.VISIBLE);
                    botaoSalvarBoloVendas.setVisibility(View.VISIBLE);


                } else {

                    Toast.makeText(getActivity(), "É necessário informar a % de lucro", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.create();
        dialog.show();

    }

    public void calcularValorSugerido(String custo, String porcDi) {

        String custoFormatado = custo.replace(",", ".");
        String porncetFormatado = porcDi.replace(",", ".");


        custoConvert = Double.parseDouble(custoFormatado);
        porcentConvert = Double.parseDouble(porncetFormatado);

        resultado = (custoConvert * porcentConvert) / 100 + custoConvert;

        String totalVend = String.valueOf(resultado);
/*
        textoNomeBolo.setText(receitaRecuperada.getNomeReceita());
        textoCustoTotalBolo.setText("Custo total do bolo foi: "+receitaRecuperada.getValorTotalReceita());
        textoQuantRendeFornada.setText("Essa receita rende: "+ receitaRecuperada.getQuantRendimentoReceita() + " Bolos");*/

        valorSugerido = String.format("%.2f", resultado);


    }


    @Override
    public void onStart() {
        super.onStart();
        carregarListaReceitasProntas();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == REQUEST_CODE_GALERIA && resultCode == RESULT_OK) {


                imageUri = data.getData();
                imagemBoloCadastrado.setImageURI(data.getData());
                enviarFotoGaleria();


        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            rotateImage(BitmapFactory.decodeFile(currentPatch));
            enviarFotoCamera();

        }



            super.onActivityResult(requestCode, resultCode, data);

}


    private void enviarFotoCamera() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Aguarde enquanto a foto é carregada");
        pd.show();

        //imagemBoloCadastrado.setRotation(90f);
//imagemBoloCadastrado.getRotation();


        Bitmap bitmap = ((BitmapDrawable) imagemBoloCadastrado.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

       /* String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "val", null);
        Uri uri = Uri.parse(path);
        imagemBoloCadastrado.setImageURI(uri);*/
        byte[] image = byteArrayOutputStream.toByteArray();


        StorageReference reference = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(nomeReceita + ".jpeg");
        UploadTask uploadTask = reference.putBytes(image);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getActivity(), "Sucesso ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
                        enderecoSalvoFoto = uri.toString();

                        pd.dismiss();
                        carregarAlertPorcent();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Erro ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void enviarFotoGaleria() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Aguarde enquanto a foto é carregada");
        pd.show();


        if (imageUri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(nomeReceita + ".jpeg");

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            enderecoSalvoFoto = uri.toString();
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Imagem enviada da galeria com sucesso", Toast.LENGTH_SHORT).show();
                            carregarAlertPorcent();
                        }
                    });
                }
            });
        }


    }

    private void dispatchCaptureImageIntent() {

        Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (inte.resolveActivity(getActivity().getPackageManager()) != null) {
            File ImageFile = null;
            try {
                ImageFile = createImageFile();

            } catch (Exception e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            if (ImageFile != null) {

                Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.marcel.a.n.roxha.deliciasdamamae.fileprovider", ImageFile);

                inte.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(inte, REQUEST_CODE_CAMERA);
            }
        }

    }

    private File createImageFile() {

        String fileName = "IMAGE_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());


        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPatch = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == REQUEST_CODE_PERMISSIONS && grantResults.length > 0){
                if(grantResults [0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    //dispatchCaptureImageIntent();
                }else {
                    Toast.makeText(getActivity(), "É necessário permitir os acessos a câmera e aos arquivos", Toast.LENGTH_SHORT).show();
                }
            }
        }


     private void rotateImage (Bitmap bitmap){

         ExifInterface exifInterface = null;
         try{
             exifInterface = new ExifInterface(currentPatch);
         }catch (Exception e){
         }
         int orientatio = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
         Matrix matrix = new Matrix();
         switch (orientatio){
             case ExifInterface.ORIENTATION_ROTATE_90:
                 matrix.setRotate(90);
                 break;
                 case ExifInterface.ORIENTATION_ROTATE_180:
                     matrix.setRotate(180);
                     break;
             default:
         }
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imagemBoloCadastrado.setImageBitmap(rotatedBitmap);
     }

}
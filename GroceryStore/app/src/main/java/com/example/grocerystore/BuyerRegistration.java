package com.example.grocerystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerRegistration extends AppCompatActivity {
    private TextView PageQuestion1,Result;
    private TextInputEditText Rfullname,Rnumber,Rcnicnumber,LoginEmail,Password;
    private Button signingIN3;
    private CircleImageView profileImage;
    private Toolbar toolbar;
    private Uri resultUri;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;
    private Spinner genderSpinner;
    private String picUrl;
    String emailPattern = "[a-zA-Z0-9]+@[gmail]+\\.+[com]";
    private FirebaseFirestore db;
    StorageReference storageReference;
    private ResolveInfo[] matches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_registration);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery App");
        PageQuestion1 = findViewById(R.id.LoginQuestion);
        PageQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerRegistration.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        Rfullname=findViewById(R.id.Rfullname);
        LoginEmail=findViewById(R.id.LoginEmail);
        Password=findViewById(R.id.Password);
        signingIN3=findViewById(R.id.signingIN2);
        profileImage=findViewById(R.id.profileImage);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        signingIN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = LoginEmail.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String full_name = Rfullname.getText().toString().trim();



                if (TextUtils.isEmpty(email)) {
                    LoginEmail.setError("Email  Is Required!");
                    return;
                }
                if(!LoginEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@(gmail)+\\.(com)+")){
                    LoginEmail.setError("Invalid Email Address  Is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password Is Required!");
                    return;
                }
                if (TextUtils.isEmpty(full_name)) {
                    Rfullname.setError("Full Name Is Required!");
                    return;
                }


                if (resultUri == null) {
                    Toast.makeText(BuyerRegistration.this,
                            "Profile Is Required", Toast.LENGTH_SHORT).show();
                }


                else{
                    loader.setMessage("Registration Is In Progress");
                    loader.setCanceledOnTouchOutside(false);

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        loader.show();
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {

                                                            String currentUserid = mAuth.getCurrentUser().getUid();

                                                            if(resultUri!=null){
                                                                final StorageReference filePath=
                                                                        FirebaseStorage.getInstance()
                                                        .getReference().child("Profile Pictures/"+ UUID.randomUUID());

                                                                Bitmap bitmap=null;
                                                                try {
                                                                    bitmap= MediaStore.Images.Media.
                                                                            getBitmap(getApplication().getContentResolver(),resultUri);
                                                                }catch (IOException e){
                                                                    e.printStackTrace();

                                                                }

                                                                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                                                                bitmap.compress(Bitmap.
                                                                        CompressFormat.JPEG,20,byteArrayOutputStream);
                                                                byte[] data = byteArrayOutputStream.toByteArray();
                                                                UploadTask uploadTask= filePath.putBytes(data);
                                                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        finish();
                                                                        return;
                                                                    }

                                                                });

           uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                        if(taskSnapshot.getMetadata()!=null){
                                                                            Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                @Override
                                                                                public void onSuccess(Uri uri) {
                                                                                    picUrl = uri.toString();
                                                                                    db = FirebaseFirestore.getInstance();

                                                                                    HashMap userInfo = new HashMap();
                                                                                    userInfo.put("name", full_name);
                                                                                    userInfo.put("email", email);
                                                                                    userInfo.put("ImageUrl", picUrl);
                                                                                    userInfo.put("timeStamp", Timestamp.now());
                                                                                    userInfo.put("type", "Buyer");


           db.collection("users").document(FirebaseAuth.getInstance().getUid()).set(userInfo);
                                                                                    loader.dismiss();
            Toast.makeText(BuyerRegistration.this,
                    "Registered Successfully .Please Verify Your Email", Toast.LENGTH_LONG).show();



                                                                                    Intent intent =new Intent(BuyerRegistration.this
                                                                                            ,LoginActivity.class);
                                                                                    startActivity(intent);
                                                                                    finish();
                                                                                    loader.dismiss();

                                                                                }});
                                                                        }}});


                                                            }



                                                        }



                                                    }

                                                });



                                    }
                                    else if(!task.isSuccessful()){

                                        Toast.makeText(BuyerRegistration.this, "Already user exist",
                                                Toast.LENGTH_LONG).show();
                                        loader.dismiss();
                                    }
                                }});
                }}});



    }
    private void uploadImage()
    {


    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode,
                                      @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            resultUri = data.getData();
            profileImage.setImageURI(resultUri);
        }

    }}

package com.example.grocerystore;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class AdminRegistration extends AppCompatActivity {
    private TextView LoginQuestion;
    private CircleImageView profileImage1;
    private Toolbar toolbar;
    private TextInputEditText Rfullname,Rnumber,Rcnicnumber,LoginEmail,Password;
    private Spinner availabilitySpinner,genderSpinner,specializationSpinner;
    private Button signingIN3;

    private Uri resultUri;
    private String picUrl;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressDialog loader;
    //    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery App");
        LoginQuestion = findViewById(R.id.LoginQuestion);
//        LoginQuestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminRegistration.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });


        Rfullname = findViewById(R.id.Rfullname);
        Rnumber = findViewById(R.id.Rfullname);
        LoginEmail = findViewById(R.id.LoginEmail);
        Password = findViewById(R.id.Password);
        signingIN3 = findViewById(R.id.signingIN2);
        profileImage1 = findViewById(R.id.profileImage);
        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        profileImage1.setOnClickListener(new View.OnClickListener() {
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
                final String number = Rnumber.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    LoginEmail.setError("Email Is Required!");
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

                if (TextUtils.isEmpty(number)) {
                    Rnumber.setError("Number Is Required!");
                    return;
                }
                if (resultUri == null) {
                    Toast.makeText(AdminRegistration.this,
                            "Profile Is Required", Toast.LENGTH_SHORT).show();
                }

                else{
                    loader.setMessage("Registration Is In Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {

                                                            String currentUserid = mAuth.getCurrentUser().getUid();

                                                            if(resultUri!=null){
                                                                final StorageReference filePath=
                                                                        FirebaseStorage.getInstance().getReference()
                                                                                .child("Profile Pictures/"+ UUID.randomUUID());

                                                                Bitmap bitmap=null;
                                                                try {
                                                                    bitmap= MediaStore.Images.Media.getBitmap(getApplication()
                                                                            .getContentResolver(),resultUri);
                                                                }catch (IOException e){
                                                                    e.printStackTrace();

                                                                }

                                                                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                                                                bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
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

                                                                                    userInfo.put("phone_number", number);

                                                                                    userInfo.put("ImageUrl", picUrl);

                                                                                    userInfo.put("timeStamp", Timestamp.now());
                                                                                    userInfo.put("type", "Admin");




          db.collection("users").document(FirebaseAuth.getInstance().getUid()).set(
                                                                                            userInfo
                                                                                    );
                                                                                    loader.dismiss();
        Toast.makeText(AdminRegistration.this, "Registered Successfully .Please Verify Your Email",
                                                                                            Toast.LENGTH_LONG).show();
       Intent intent =new Intent(AdminRegistration.this
                                                  ,AdminRegistration.class);
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
                                        Toast.makeText(AdminRegistration.this, "Already user exist",
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
            profileImage1.setImageURI(resultUri);
        }
    }}
package com.example.grocerystore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private TextView LoginQuestion;
    private TextInputEditText LoginEmail, Password;
    private Toolbar toolbar;
    private TextView ForgotPassword;
    private Button signingIN2;
    private String Password1,LoginEmail1;
    CheckBox checkBox;
    boolean passwordVisible;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;   //////verfying email using authorization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery App");
        LoginQuestion = findViewById(R.id.DocRegQuestion);
        LoginEmail = findViewById(R.id.LoginEmail);
        Password = findViewById(R.id.Password);
        signingIN2 = findViewById(R.id.signingIN2);
        checkBox=findViewById(R.id.checkBox1);
        loader = new ProgressDialog(this);
        ForgotPassword = findViewById(R.id.ForgotPass);


checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        else{
Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }



});




//        if(LoginEmail1=="admin@gmail.com"&&Password1=="ahmed409"){
//            Intent intent = new Intent(LoginActivity.this, Admin.class);
//            startActivity(intent);
//            finish();
//        }

        mAuth = FirebaseAuth.getInstance();


    authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            FirebaseUser user = mAuth.getCurrentUser();
///////////////////////////////////////////Work///////////////////////////////////////////////////////////////////////////////////
        }

    };

        LoginQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        signingIN2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final String email = LoginEmail.getText().toString().trim();
            final String password = Password.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                LoginEmail.setError("Email Is Required!");
            }
            else if (TextUtils.isEmpty(password)) {
                Password.setError("Password Is Required!");
            } else {
                loader.setMessage("Signing  In ....");
                loader.setCanceledOnTouchOutside(false);
                loader.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //////////////////////////////////////////////////////////////////////work///////////////////////////////////////////////////////////
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").
                                                document(FirebaseAuth.getInstance().getUid());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    String type = document.getString("type");
                                                    Toast.makeText(LoginActivity.this,type,
                                                            Toast.LENGTH_LONG).show();
                                                    if(type.equals("Admin")){
                                                        Intent intent = new Intent(LoginActivity.this, Admin.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else {
                                                        Intent intent = new Intent(LoginActivity.this,Buyer.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                } else {
                                                    Toast.makeText(LoginActivity.this, task.getException().toString(),
                                                            Toast.LENGTH_LONG).show();
                                                }


                                            }
                                        });


                                    }



                                    else {

                                        Toast.makeText(LoginActivity.this, "Please Verify Your Email Address,First", Toast.LENGTH_SHORT).show();
                                    }

                                }



                                else {
                                    loader.dismiss();



                                }

                            }
                        });

            }

        }
    });
       ForgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText resetMAil=new EditText(view.getContext());
            AlertDialog.Builder passwordReset=new AlertDialog.Builder(view.getContext());
            passwordReset.setTitle("Reset Password");
            passwordReset.setMessage("Enter Your Email to Reset");
            passwordReset.setView(resetMAil);

            passwordReset.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String mail=resetMAil.getText().toString();
                    mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(LoginActivity.this,"Reset Link Sent to your Email",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Error Link Is Not Sent"+e.getMessage(),Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });
            passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            passwordReset.create().show();

        }
    });
}
    @Override
    protected void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(authStateListener);


    }


    protected void onStop() {

        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);

    }
    }


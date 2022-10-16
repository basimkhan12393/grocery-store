package com.example.grocerystore;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public static int cart_count = 0;
    private ImageView imagelogo;
    private Button Letsgo;
    private TextView Rdocu1,Rdocu2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    Animation top_Animation,bottom_Animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getSupportActionBar().setTitle("Grocery App");
        imagelogo=findViewById(R.id.imagelogo);
        Rdocu1=findViewById(R.id.Rdoc1);
        Rdocu2=findViewById(R.id.Rdoc2);
        Letsgo=findViewById(R.id.frontToMain);
        top_Animation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_Animation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imagelogo.setAnimation(top_Animation);
        Rdocu1.setAnimation(bottom_Animation);
        Rdocu2.setAnimation(bottom_Animation);





        Letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null&& user.isEmailVerified()) {
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").
                            document(FirebaseAuth.getInstance().getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                LoginDataForDatabase.id = FirebaseAuth.getInstance().getUid();//////////////////////////////////////////////
                                LoginDataForDatabase.name = document.getString("name");//////////////////////////////////////////////////
                                LoginDataForDatabase.email = document.getString("email");/////// Cart activity
                                //
                                Toast.makeText(MainActivity.this,document.getString("type"),
                                        Toast.LENGTH_LONG).show(); ///showing type buyr or admin
                                String type = document.getString("type");
                                Intent intent;
                                if(type.equals("Buyer")){

                                    intent = new Intent(MainActivity.this, Buyer.class);
                                    //
                                }else{
                                    intent = new Intent(MainActivity.this, Admin.class);
                                }
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().toString(),
                                        Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }


        });

    }
    }
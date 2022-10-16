package com.example.grocerystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class AboutMe extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView text,AboutUs;
    private Button back;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Grocery App");

        text=findViewById(R.id.E_hospital_about);
        navigationView = findViewById(R.id.nav_View);
//        AboutUs=findViewById(R.id.AboutUss);
        back=findViewById(R.id.abouttt);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AboutMe.this,Buyer.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
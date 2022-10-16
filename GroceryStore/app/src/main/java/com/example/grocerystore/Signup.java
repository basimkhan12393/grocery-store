package com.example.grocerystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    private TextView Back;
    private Button buyReg,AdmReg;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery App");
        Back =findViewById(R.id.Back);

        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent( Signup.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        buyReg=findViewById(R.id.BuyerReg);
        AdmReg=findViewById(R.id.AdminReg);


        buyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent =new Intent(Signup.this, BuyerRegistration.class);
                startActivity(intent);
            }
        });

        AdmReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(Signup.this, AdminRegistration.class);
                startActivity(intent);
            }
        });

    }
}
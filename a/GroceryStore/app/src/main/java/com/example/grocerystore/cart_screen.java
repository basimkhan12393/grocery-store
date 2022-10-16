package com.example.grocerystore;

import static com.example.grocerystore.R.id.delete_item_from_cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class cart_screen extends AppCompatActivity {
    private FirebaseFirestore db;
    ImageView list_image_cart1  ;
    public static ListView cartview;
    private int[] images = {
            R.drawable.sprite,
            R.drawable.toothbrsh,R.drawable.det,R.drawable.apple,R.drawable.achargostmasala,R.drawable.balloon,R.drawable.banana
            ,R.drawable.biscuits,R.drawable.cabbage,R.drawable.candles,R.drawable.capsicum,R.drawable.carrot,R.drawable.catfood,R.drawable.chatmasala
            ,R.drawable.cherry,R.drawable.chickentikkamasala,R.drawable.chocolates,R.drawable.cocomo,R.drawable.cocumber,R.drawable.cokecan,R.drawable.colorpencils,R.drawable.corn
            ,R.drawable.cupcake,R.drawable.dates,R.drawable.dew,R.drawable.facewash,R.drawable.fanta,R.drawable.grapes,R.drawable.kormamasala,R.drawable.lays,R.drawable.mangoes,R.drawable.oil
            ,R.drawable.onion,R.drawable.partyhat,R.drawable.pomegranate,R.drawable.potato,R.drawable.rice,R.drawable.shampooo
            ,R.drawable.stawberry,R.drawable.sunflroil,R.drawable.sugar,R.drawable.tissue,R.drawable.toothpaste
    };
    public static TextView grand_total_cart,ProceedtoBuy;

    public static ArrayList<FoodDomain> dataModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        grand_total_cart = findViewById(R.id.grand_total_cart);
        list_image_cart1 = findViewById(R.id.list_image_cart);

        ProceedtoBuy=findViewById(R.id.ProceedtoBuy);
        cartview = findViewById(R.id.recycler_view_cart);

        dataModalArrayList = CoursesLVAdapter.cartModels;

        ProceedtoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();





                HashMap data = new HashMap();
                data.put("username",LoginDataForDatabase.name);
                data.put("userId",LoginDataForDatabase.id);
                data.put(("email"),LoginDataForDatabase .email);
                data.put("cartItems",dataModalArrayList);
                data.put("timeStamp", Timestamp.now());

                db.collection("orders").add(data);



            }

        });






        loadDatainListview();

    }





    private void loadDatainListview() {

        double total=0;
        for(int i=0; i<dataModalArrayList.size(); i++){
            total += dataModalArrayList.get(i).getFee()*dataModalArrayList.get(i).getQuantity();
        }
        grand_total_cart.setText(String.valueOf(total));
        cartAdapter adapter = new cartAdapter(cart_screen.this, dataModalArrayList);
        cartview.setAdapter(adapter);

    }





}
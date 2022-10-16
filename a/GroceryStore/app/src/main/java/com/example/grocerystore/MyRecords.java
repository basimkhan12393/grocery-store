package com.example.grocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRecords extends AppCompatActivity {
    private FirebaseFirestore db;
    FirebaseAuth mauth;
    TextView fullnamemed;
    String users;
    //    public static ListView;
    public static ArrayList<CartHistory> dataModalArrayList;
    //    private ArrayList<String> dataModalArrayList = new ArrayList();
    TextView Check;
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //    ArrayList<String> MedName = new ArrayList<>();
    ArrayList<String> List ;
    ArrayAdapter<String> arrayAdapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_records);
        fullnamemed = findViewById(R.id.product_cart_code);
        dataModalArrayList =new ArrayList<CartHistory>();
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        List=new ArrayList<>();
        Check = findViewById(R.id.ProceedtoBuy);
        arrayAdapter=new ArrayAdapter<String>(MyRecords.this,R.layout.list_row123,R.id.recycler_view_cart,List);
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecords.this, Admin.class);
                startActivity(intent);
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.recycler_view_cart);
        loadDatainListview();

    }



    private void loadDatainListview() {

        db.collection("orders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
                        if (!queryDocumentSnapshots.isEmpty()) {


                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            java.util.List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
//                                Toast.makeText(post_page.this, d.getString("name"), Toast.LENGTH_SHORT).show();
                                // after getting this list we are passing
                                // that list to our object class.

                                List<HashMap> likes = (List<HashMap>) d.get("cartItems");

//                                MedHistoryPatient dataModal = new MedHistoryPatient(d.getString("name"),Double.parseDouble(likes.get(0).get("totalPrice").toString()),d.getString("username"),String.valueOf(d.getString("email"))
//                                        ,Integer.parseInt(likes.get(0).get("quantity").toString()) );
                                CartHistory dataModal = new CartHistory(likes.get(0).get("title").toString(),d.getString("email"),d.getString("username"),
                                        Integer.parseInt(likes.get(0).get("quantity").toString())  ,Double.parseDouble(likes.get(0).get("totalPrice").toString()) );
//                                MedHistoryPatient dataModal1 = new MedHistoryPatient(d.getString("name"),d.getString("email"),d.getString("username"),
//                                        Integer.parseInt(likes.get(0).get("quantity").toString())  ,Double.parseDouble(likes.get(0).get("totalPrice").toString()) );

                                dataModalArrayList.add(dataModal);
                            }
                            CartHistAdapter adapter = new CartHistAdapter(MyRecords.this, dataModalArrayList);

                            listView.setAdapter(adapter);
                        } else {

                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(MyRecords.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(MyRecords.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }

                });
    }

}

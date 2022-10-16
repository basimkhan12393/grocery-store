package com.example.grocerystore;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.Button;


public class Buyer extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    public static int cart_count = 0;

    ImageView home ,support;
    private Button signout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    PackageManager pm;
    ListView coursesLV;
    ArrayList<FoodDomain> dataModalArrayList;
    FloatingActionButton floating;
    ImageView fk;
    String users;
    FirebaseAuth mauth;
    Bitmap mIcon_val;
    MenuItem main_menu;
    private CircleImageView nav_prof_image;
    private TextView fullname, email, gender, contact, type, AboutUs, profile, phone, records;
    StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        signout = findViewById(R.id.Signout);
        fk = findViewById(R.id.nav_userImage);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery App");
 /////////////////////////////////////////////////////////////////////////////////////////////////////////
        coursesLV = findViewById(R.id.listView);
        floating = findViewById(R.id.floating);
        home = findViewById(R.id.imageView);

        support = findViewById(R.id.imageView2);

        dataModalArrayList = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_View);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Buyer.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nav_prof_image = navigationView.getHeaderView(0).findViewById(R.id.nav_userImage);
        fullname = navigationView.getHeaderView(0).findViewById(R.id.nav_user_FullName);
        email = navigationView.getHeaderView(0).findViewById(R.id.nav_user_Gmail);
        AboutUs = navigationView.getHeaderView(0).findViewById(R.id.AboutUss);
        signout = navigationView.getHeaderView(0).findViewById(R.id.Signout);
        dataModalArrayList = new ArrayList<>();
        coursesLV = findViewById(R.id.listView);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buyer.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumberWithCountryCode = "+(923117776777)";
                String message = "";

                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                )
                        )
                );
            }
        });

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buyer.this,cart_screen.class);
                startActivity(intent);
            }
        });

        records=navigationView.getHeaderView(0).findViewById(R.id.MyRecords);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem main_menu) {


                switch (main_menu.getItemId()) {


                    case R.id.myHome:


                        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

                        drawerLayout.closeDrawers();
                }

                switch (main_menu.getItemId()) {


                    case R.id.AboutUss:
                        Intent intent2 = new Intent(Buyer.this, AboutMe.class);
                        startActivity(intent2);


                }
//
                switch (main_menu.getItemId()) {


                    case R.id.Chat:
                        String phoneNumberWithCountryCode = "+(923117776777)";
                        String message = "Hello";

                        startActivity(
                                new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(
                                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                        )
                                )
                        );
                }
                switch (main_menu.getItemId()) {

                    case R.id.Email:

//                        Intent intent = new Intent(Intent.ACTION_SEND);

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setType("text/html");
                        emailIntent.setData(Uri.parse("mailto:irsazeeshan29@gmail.com"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
//

                        startActivity(Intent.createChooser(emailIntent, "Send Email"));
                }
                switch (main_menu.getItemId()) {


                    case R.id.Signout:


                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(Buyer.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                }



                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
//    }    //////delete after recover below
//}        //////delete after recover below


        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mauth.getCurrentUser();
        if(mFirebaseUser != null) {
            users = mFirebaseUser.getUid(); //Do what you need to do with the id


        }
//        users=mauth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").
                document(FirebaseAuth.getInstance().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                Toast.makeText(MainActivity.this,FirebaseAuth.getInstance().getUid(),
//                        Toast.LENGTH_LONG).show();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        new DownloadImageTask(nav_prof_image)
                                .execute(document.getString("ImageUrl"));

                        fullname.setText(document.getString("name"));
                        email.setText(document.getString("email"));
//



                    } else {
                        Toast.makeText(Buyer.this, "No such User exist with this id",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Buyer.this, task.getException().toString(),
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        loadDatainListview();
    }

//        floating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Buyer.this,cart_screen.class);
//                startActivity(intent);
//            }
//        });




        private void loadDatainListview() {
//

            coursesLV = findViewById(R.id.listView);
            dataModalArrayList.add(new FoodDomain("Sprite", "sprite",
                    "Refreshment", 40));
            dataModalArrayList.add(new FoodDomain("ToothBrush", "toothbrsh",
                    "Mouth Cleaner", 50));
            dataModalArrayList.add(new FoodDomain("Dettol", "det",
                    "Body Health", 45));
            dataModalArrayList.add(new FoodDomain("Apple", "apple",
                    "Body Health", 60));
            dataModalArrayList.add(new FoodDomain("Dettol", "achargostmasala",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "balloon",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Banana", "banana",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "biscuits",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cabbage",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "candles",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "capsicum",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "carrot",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "catfood",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "chatmasala",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cherry",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "chickentikkamasala",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "chocolates",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cocomo",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cocumber",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cokecan",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "colorpencils",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "corn",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "cupcake",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "dates",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "dew",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "facewash",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "fanta",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "grapes",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "kormamasala",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "lays",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "mangoes",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "oil",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "onion",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "partyhat",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "pomegranate",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "potato",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "rice",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "shampoo",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "stawberry",
                    "Body Health", 45.4));
            dataModalArrayList.add(new FoodDomain("Dettol", "sunflroil",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "sugar",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "tissue",
                    "Body Health", 45.4));
          dataModalArrayList.add(new FoodDomain("Dettol", "toothpaste",
                    "Body Health", 45.4));

            CoursesLVAdapter adapter = new CoursesLVAdapter(Buyer.this, dataModalArrayList);
            coursesLV.setAdapter(adapter);


        }
















    void getImage(String url) {
        try {
            URL newurl = new URL(url);
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            fk.setImageBitmap(mIcon_val);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask1(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;

    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}
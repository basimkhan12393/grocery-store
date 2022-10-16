package com.example.grocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin extends AppCompatActivity {
    private DrawerLayout drawerLayoutd;
    private Button signoutd;
    private Toolbar toolbar;
    private NavigationView navigationViewd;
    PackageManager pm;

    ImageView image;
    String users;
    FirebaseAuth mauth;
    Bitmap mIcon_val;
    MenuItem main_menu;
    private CircleImageView nav_prof_image;
    private TextView fullname, email, gender, type, AboutUs,profile,records;
    StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        signoutd = findViewById(R.id.Signout);
       image = findViewById(R.id.nav_userImage);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery Store");

        drawerLayoutd = findViewById(R.id.drawerLayout);
        navigationViewd = findViewById(R.id.nav_View);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(com.example.grocerystore.Admin.this, drawerLayoutd, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayoutd.addDrawerListener(toggle);
        toggle.syncState();


        nav_prof_image = navigationViewd.getHeaderView(0).findViewById(R.id.nav_userImage);
        fullname = navigationViewd.getHeaderView(0).findViewById(R.id.nav_user_FullName);
        email = navigationViewd.getHeaderView(0).findViewById(R.id.nav_user_Gmail);
        AboutUs = navigationViewd.getHeaderView(0).findViewById(R.id.AboutUss);
        signoutd = navigationViewd.getHeaderView(0).findViewById(R.id.Signout);

        records=navigationViewd.getHeaderView(0).findViewById(R.id.MyRecords);

        navigationViewd.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem main_menu1) {


                switch (main_menu1.getItemId()) {


                    case R.id.myHome:
                        drawerLayoutd = (DrawerLayout) findViewById(R.id.drawerLayout);
                        drawerLayoutd.closeDrawers();
                }

                switch (main_menu1.getItemId()) {

                    case R.id.AboutUss:
                        Intent intent2 = new Intent(Admin.this, AboutMe1.class);
                        startActivity(intent2);


                }
                switch (main_menu1.getItemId()) {


                    case R.id.MyRecords:
                        Intent intent2 = new Intent(com.example.grocerystore.Admin.this, MyRecords.class);
                        startActivity(intent2);


                }
//
                switch (main_menu1.getItemId()) {


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
                }  switch (main_menu1.getItemId()) {

                    case R.id.Email:

//                        Intent intent = new Intent(Intent.ACTION_SEND);

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setType("text/html");
                        emailIntent.setData(Uri.parse("mailto:irsazeeshan29@gmail.com"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Queries");
//                        emailIntent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                        startActivity(Intent.createChooser(emailIntent, "Send Email"));
                }
                switch (main_menu1.getItemId()) {



                    case R.id.Signout:


                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(com.example.grocerystore.Admin.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                }


                drawerLayoutd.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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

                        new com.example.grocerystore.DownloadImageTask(nav_prof_image)
                                .execute(document.getString("ImageUrl"));

                        fullname.setText(document.getString("name"));
                        email.setText(document.getString("email"));


                    } else {
                        Toast.makeText(com.example.grocerystore.Admin.this, "No such User exist with this id",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(com.example.grocerystore.Admin.this, task.getException().toString(),
                            Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    void getImage(String url) {
        try {
            URL newurl = new URL(url);
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            image.setImageBitmap(mIcon_val);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
    }



}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
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
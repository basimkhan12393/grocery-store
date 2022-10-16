package com.example.grocerystore;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grocerystore.CartHistory;

import java.util.ArrayList;

class CartHistAdapter extends ArrayAdapter<CartHistory> {


    public static CartHistory cartModel1;
    public static ArrayList<CartHistory> cartModelss = new ArrayList<CartHistory>();


    // constructor for our list view adapter.
    public CartHistAdapter(@NonNull Context context, ArrayList<CartHistory> dataModalArrayList2) {
        super(context, 0, dataModalArrayList2);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.list_row123, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        CartHistory dataModal = getItem(position);


        // initializing our UI components of list view item.
        TextView MedName = listitemView.findViewById(R.id.product_cart_code);
        TextView PName = listitemView.findViewById(R.id.nameUserMed);
        TextView Fee = listitemView.findViewById(R.id.FeeMed);
        TextView Quantity = listitemView.findViewById(R.id.Quantity);
        TextView Gmail = listitemView.findViewById(R.id.emailMed);



        //  fetching value
        MedName.setText(dataModal.getMedName());
        Fee.setText(String.valueOf(dataModal.getFee()));
        PName.setText(String.valueOf(dataModal.getPatName()));
        Quantity.setText(String.valueOf(dataModal.getQuantity()));
        Gmail.setText(String.valueOf(dataModal.getEmail()));

        View finalListitemView = listitemView;
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
//                dialog.setContentView(R.layout.item_quantitydoc);
//                dialog.setTitle("Custom Dialog");
//                dialog.show();

            }


        });

        return listitemView;
    }}
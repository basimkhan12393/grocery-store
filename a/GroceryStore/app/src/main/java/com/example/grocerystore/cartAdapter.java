package com.example.grocerystore;

import static com.example.grocerystore.cart_screen.cartview;
import static com.example.grocerystore.cart_screen.dataModalArrayList;
import static com.example.grocerystore.cart_screen.grand_total_cart;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


class cartAdapter extends ArrayAdapter<FoodDomain> {


    Context context;

    private int[] imagess = {
            R.drawable.sprite,R.drawable.toothbrsh,R.drawable.det,R.drawable.apple,R.drawable.achargostmasala,R.drawable.balloon,R.drawable.banana
            ,R.drawable.biscuits,R.drawable.cabbage,R.drawable.candles,R.drawable.capsicum,R.drawable.carrot,R.drawable.catfood,R.drawable.chatmasala
            ,R.drawable.cherry,R.drawable.chickentikkamasala,R.drawable.chocolates,R.drawable.cocomo,R.drawable.cocumber,R.drawable.cokecan,R.drawable.colorpencils,R.drawable.corn
            ,R.drawable.cupcake,R.drawable.dates,R.drawable.dew,R.drawable.facewash,R.drawable.fanta,R.drawable.grapes,R.drawable.kormamasala,R.drawable.lays,R.drawable.mangoes,R.drawable.oil
            ,R.drawable.onion,R.drawable.partyhat,R.drawable.pomegranate,R.drawable.potato,R.drawable.rice,R.drawable.shampooo
            ,R.drawable.stawberry,R.drawable.sunflroil,R.drawable.sugar,R.drawable.tissue,R.drawable.toothpaste
    };
    //    int numberOrder=1;
    private TextView addtoCartt,fee,description,plus,minus,numberAdd;
    private ClassLoader mContext;

    // constructor for our list view adapter.
    public cartAdapter(@NonNull Context context, ArrayList<FoodDomain> dataModalArrayList) {
        super(context, 0, dataModalArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

//        LayoutInflater inflater = LayoutInflater.from();


        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        FoodDomain dataModal = getItem(position);



        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.product_cart_code);
        TextView fee = listitemView.findViewById(R.id.product_cart_price);
        ImageView delete = listitemView.findViewById(R.id.delete_item_from_cart);
        ImageView  image=listitemView.findViewById(R.id.list_image_cart);
        ImageView increment = listitemView.findViewById(R.id.cart_increment);
        TextView quantity = listitemView.findViewById(R.id.cart_product_quantity_tv);
        ImageView decrement = listitemView.findViewById(R.id.cart_decrement);

        quantity.setText(String.valueOf( dataModal.getQuantity()));
        double setFee = dataModal.getFee()*dataModal.getQuantity();
        fee.setText(String.valueOf(setFee));
        name.setText(dataModal.getTitle());
        image.setImageResource(imagess[position]);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModal.setQuantity(dataModal.getQuantity()+1);
                quantity.setText(String.valueOf( dataModal.getQuantity()));
                double setFee = dataModal.getFee()*dataModal.getQuantity();
                fee.setText(String.valueOf(setFee));
//                Toast.makeText(getContext(), total.getText().toString(), Toast.LENGTH_SHORT).show();
                grand_total_cart.setText(String .valueOf(Double.parseDouble(grand_total_cart.getText().toString())+dataModal.getFee()));

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModalArrayList.remove(dataModal);
                cartview.invalidateViews();
                double total=0;
                for(int i=0; i<dataModalArrayList.size(); i++){
                    total += dataModalArrayList.get(i).getFee()*dataModalArrayList.get(i).getQuantity();
                }
                grand_total_cart.setText(String.valueOf(total));
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataModal.getQuantity() >1){
                    dataModal.setQuantity(dataModal.getQuantity()-1);
                    quantity.setText(String.valueOf( dataModal.getQuantity()));
                    double setFee = dataModal.getFee()*dataModal.getQuantity();
                    fee.setText(String.valueOf(setFee));
                    grand_total_cart.setText(String .valueOf(Double.parseDouble(grand_total_cart.getText().toString())-dataModal.getFee()));

                }

//                total.setText( String .valueOf(Integer.parseInt(total.getText().toString())-dataModal.getFee()));

//                if((dataModal.getQuantity())>1){
//                    quantity.setText(String.valueOf(1));
//                   quantity.setText("item Cant Be Less Than 0 ");
//
//                }


            }
        });



        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return listitemView;




    }


}
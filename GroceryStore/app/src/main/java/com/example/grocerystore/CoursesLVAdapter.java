package com.example.grocerystore;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class CoursesLVAdapter extends ArrayAdapter<FoodDomain> {


    Context context;

    public static FoodDomain cartModel;
    public static ArrayList<FoodDomain> cartModels = new ArrayList<FoodDomain>();
    private int[] images = {
            R.drawable.sprite,R.drawable.toothbrsh,R.drawable.det,R.drawable.apple,R.drawable.achargostmasala,R.drawable.balloon,R.drawable.banana
            ,R.drawable.biscuits,R.drawable.cabbage,R.drawable.candles,R.drawable.capsicum,R.drawable.carrot,R.drawable.catfood,R.drawable.chatmasala
            ,R.drawable.cherry,R.drawable.chickentikkamasala,R.drawable.chocolates,R.drawable.cocomo,R.drawable.cocumber,R.drawable.cokecan,R.drawable.colorpencils,R.drawable.corn
            ,R.drawable.cupcake,R.drawable.dates,R.drawable.dew,R.drawable.facewash,R.drawable.fanta,R.drawable.grapes,R.drawable.kormamasala,R.drawable.lays,R.drawable.mangoes,R.drawable.oil
            ,R.drawable.onion,R.drawable.partyhat,R.drawable.pomegranate,R.drawable.potato,R.drawable.rice,R.drawable.shampooo
            ,R.drawable.stawberry,R.drawable.sunflroil,R.drawable.sugar,R.drawable.tissue,R.drawable.toothpaste
       };

    private TextView addtoCartt,fee,description,plus,minus,numberAdd;
    private ClassLoader mContext;

    // constructor for our list view adapter.
    public CoursesLVAdapter(@NonNull Context context, ArrayList<FoodDomain> dataModalArrayList) {
        super(context, 0, dataModalArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.viewfile, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        FoodDomain dataModal = getItem(position);


        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.title1);
        ImageView pic = listitemView.findViewById(R.id.pic);
        TextView fee = listitemView.findViewById(R.id.fee12);
        TextView desc=listitemView.findViewById(R.id.description);


      //  fetching value
        nameTV.setText(dataModal.getTitle());
        desc.setText(dataModal.getDescription());
        fee.setText(String.valueOf(dataModal.getFee()));
        pic.setImageResource(images[position]);






        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.item_quantity);
                // Set dialog title
                dialog.setTitle("Custom Dialog");
                dialog.show();

                final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
                ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);

                TextView updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);

                final TextView quantity = dialog.findViewById(R.id.cart_product_quantity_tv);
                quantity.setText(String.valueOf(1));
                final int[] cartCounter = {1};//{(arrayListImage.get(position).getStocks())};
                cartDecrement.setEnabled(false);
                cartDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cartCounter[0] != 1) {
                            cartCounter[0] -= 1;
                            quantity.setText(String.valueOf(cartCounter[0]));

                        }

                    }
                });
                cartIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDecrement.setEnabled(true);
                        cartCounter[0] += 1;
                        quantity.setText(String.valueOf(cartCounter[0]));


                    }
                });


                dialog.show();
                updateQtyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // from these line of code we add items in cart
                        cartModel = new FoodDomain();
                        cartModel.setQuantity(cartCounter[0]);
                        cartModel.setFee(dataModal.getFee());
                        cartModel.setTitle(dataModal.getTitle());
                        cartModel.setFee(dataModal.getFee());
                        cartModel.setPic(dataModal.getPic());
                        cartModel.setTotalPrice(cartCounter[0] *
                                dataModal.getFee());
//                        Log.d("pos", String.valueOf(i));



//
                        // from these lines of code we update badge count value
                        MainActivity.cart_count = 0;
                        boolean already = false;
                        for (int i = 0; i < cartModels.size(); i++) {
                            if(cartModels.get(i).getTitle() == dataModal.getTitle()){
                                cartModels.get(i).setQuantity(cartModels.get(i).getQuantity()+cartCounter[0]);
                                already = true;
                            }
                        }
                        if(already == false){
                            cartModels.add(cartModel);
                        }
                        MainActivity.cart_count = cartModels.size();

                        // from this interface method calling we show the updated value of cart cout in badge
//                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                        // on the item click on our list view.
                        // we are displaying a toast message.

                        Toast.makeText(getContext(), "" + dataModal.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        return listitemView;




    }


}








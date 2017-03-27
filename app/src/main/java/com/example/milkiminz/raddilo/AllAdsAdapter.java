package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by 1415033 on 27-03-2017.
 */

public class AllAdsAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] desc;
    private String[] price,email,add,ph;
    private TextView textViewdesc;
    private TextView textViewprice,textViewemail,textViewadd,textViewph;


    public AllAdsAdapter(Activity context, String[] price, String[] desc,String[] email,String[] add,String[] ph) {
        super(context, R.layout.ads, desc);
        this.context = context;
        this.price = price;
        this.desc = desc;
        this.email=email;
        this.add=add;
        this.ph=ph;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View view = inflater.inflate(R.layout.ads, null, true);
        //listview implementation
        textViewdesc = (TextView) view.findViewById(R.id.addesc);
        textViewprice = (TextView) view.findViewById(R.id.price);
        textViewemail = (TextView) view.findViewById(R.id.email);
        textViewadd = (TextView) view.findViewById(R.id.add);
        textViewph = (TextView) view.findViewById(R.id.ph);


        textViewdesc.setText(desc[position]);
        textViewprice.setText(price[position]);
        textViewemail.setText(email[position]);
        textViewph.setText(ph[position]);
        textViewadd.setText(add[position]);



        return view;
    }
}

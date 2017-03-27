package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by 1415044 on 26-03-2017.
 */

public class MyAdsListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] desc;
    private String[] price;
    private TextView textViewdesc;
    private TextView textViewprice;


    public MyAdsListAdapter(Activity context,String[] price, String[] desc) {
        super(context, R.layout.mysinglead, desc);
        this.context = context;
        this.price = price;
        this.desc = desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View view = inflater.inflate(R.layout.mysinglead, null, true);
        //listview implementation
        textViewdesc = (TextView) view.findViewById(R.id.addesc);
        textViewprice = (TextView) view.findViewById(R.id.price);


        textViewdesc.setText(desc[position]);
        textViewprice.setText(price[position]);


        return view;
    }
}

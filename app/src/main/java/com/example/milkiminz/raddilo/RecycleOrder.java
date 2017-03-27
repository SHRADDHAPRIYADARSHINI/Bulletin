package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressWarnings("ALL")
class RecycleOrder extends ArrayAdapter<String> {

    private Activity context;
    private String[] name;
    private String[] email;
    private String[] phone;
    private String[] add;
    private String[] qty;
    private String[] ppr;
    private String[] gls;
    private String[] pls;
    private String[] mel;
    private String[] oth;

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAdd;
    private TextView textViewQty;
    private TextView textViewPpr;
    private TextView textViewMel;
    private TextView textViewGls;
    private TextView textViewPls;
    private TextView textViewOth;

    public RecycleOrder(Activity context, String[] name, String[] email, String[] phone, String[] add, String[] qty, String[] ppr, String[] gls, String[] mel, String[] oth, String[] pls) {
        super(context, R.layout.singleorder, name);
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.add = add;
        this.qty = qty;
        this.ppr = ppr;
        this.mel = mel;
        this.gls = gls;
        this.pls = pls;
        this.oth = oth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View view = inflater.inflate(R.layout.singleorder, null, true);
        //setting values
        textViewName = (TextView) view.findViewById(R.id.custname);
        textViewEmail = (TextView) view.findViewById(R.id.custemail);
        textViewPhone = (TextView) view.findViewById(R.id.custphone);
        textViewAdd = (TextView) view.findViewById(R.id.custadd);
        textViewQty = (TextView) view.findViewById(R.id.custitem);
        textViewPpr = (TextView) view.findViewById(R.id.paper);
        textViewMel = (TextView) view.findViewById(R.id.metal);
        textViewGls = (TextView) view.findViewById(R.id.glass);
        textViewPls = (TextView) view.findViewById(R.id.plastic);
        textViewOth = (TextView) view.findViewById(R.id.others);

        textViewName.setText(name[position]);
        textViewEmail.setText(email[position]);
        textViewPhone.setText(phone[position]);
        textViewAdd.setText(add[position]);
        textViewQty.setText(qty[position]);
        textViewPpr.setText(ppr[position]);
        textViewMel.setText(mel[position]);
        textViewGls.setText(gls[position]);
        textViewPls.setText(pls[position]);
        textViewOth.setText(oth[position]);


        return view;
    }
}

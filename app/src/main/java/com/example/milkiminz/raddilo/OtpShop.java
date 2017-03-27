package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

public class OtpShop extends AppCompatActivity {

    private String Url;
    private ProgressDialog pDialog;
    private EditText sotp;
    private String ss;
    private RequestQueue requestQueue;

    String out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpshop);
        sotp = (EditText) findViewById(R.id.sotp);
        Url = getResources().getString(R.string.verifyshop);
        Toast.makeText(this, getResources().getString(R.string.otpver), Toast.LENGTH_SHORT).show();
    }


    public void sub(View view) {
        if (!sotp.getText().toString().equals("")) {
            ss = sotp.getText().toString();
            new verify().execute();

        } else {
            Toast.makeText(this, getResources().getString(R.string.enterotp), Toast.LENGTH_SHORT).show();
        }
    }


    private String loadData() {
        String URL = "content://com.example.milkiminz.raddilo.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
    }

    private class verify extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtpShop.this);
            pDialog.setMessage("Checking...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals(getResources().getString(R.string.success))) {


                                Toast.makeText(OtpShop.this, getResources().getString(R.string.success) + " Verified", Toast.LENGTH_LONG).show();

                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(OtpShop.this, getResources().getString(R.string.vf), Toast.LENGTH_LONG).show();//otp does not match

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(OtpShop.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("semail", loadData());

                    params.put("sotp", ss);


                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(OtpShop.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }


}

package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import static android.R.attr.password;
import static com.example.milkiminz.raddilo.R.id.email;

public class PostAds extends AppCompatActivity {
    private EditText ades;
    private EditText price;
    private Button post;
    private String loadUrl;
    String out;
    RequestQueue requestQueue;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ads);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ades=(EditText)findViewById(R.id.ades);
        price=(EditText)findViewById(R.id.apr);
        post=(Button)findViewById(R.id.abutt);
        loadUrl="http://139.59.47.63/postads.php";


    }
    public void post(View view){
        if (!ades.getText().toString().equals("") || !price.getText().toString().equals("")) {
            if (isNetworkAvailable()) {
                new AttemptPost().execute();
            } else {
                Toast.makeText(PostAds.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();//for slow interner
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.enteremailpassword), Toast.LENGTH_SHORT).show();//for wrong email or password
        }
    }

    protected String loadData() {
        String FILENAME = "email.txt";

        try {
            out="";
            FileInputStream fis1 = getApplication().openFileInput(FILENAME);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1 = null;

            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return out;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    class AttemptPost extends AsyncTask<String, String, String> {

        String adesc = ades.getText().toString();
        String prc = price.getText().toString();
        String success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PostAds.this);
            pDialog.setMessage("Logging in....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, loadUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals(getResources().getString(R.string.success))) {

                                Toast.makeText(PostAds.this, "Successfully Posted", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PostAds.this, HomeAd.class));
                                finish();
                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(PostAds.this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(PostAds.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(PostAds.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("price", prc);
                    params.put("desc",adesc);
                    params.put("email", loadData());

                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(PostAds.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }
}

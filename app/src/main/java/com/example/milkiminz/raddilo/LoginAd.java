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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class LoginAd extends AppCompatActivity {

    private String loginUrl;

    private EditText email;
    private EditText password;
    private ProgressDialog pDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ad);
        email = (EditText) findViewById(R.id.aemail);
        password = (EditText) findViewById(R.id.apass);
        loginUrl = "http://139.59.47.63/loginad.php";


    }

    public void Login_now(View view) {
        if (!email.getText().toString().equals("") || !password.getText().toString().equals("")) {
            if (isNetworkAvailable()) {
               new AttemptLogin().execute();
            } else {
                Toast.makeText(LoginAd.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();//for slow interner
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.enteremailpassword), Toast.LENGTH_SHORT).show();//for wrong email or password
        }
    }

    private void saveData2(String email){
        String FILENAME1 = "email.txt";
        String verifyme=email;

        try {
            FileOutputStream fos1 = getApplication().openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fos1.write(verifyme.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        String em = email.getText().toString();
        String pass = password.getText().toString();
        String success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginAd.this);
            pDialog.setMessage("Logging in....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals(getResources().getString(R.string.success))) {

                                saveData2(em);
                                Toast.makeText(LoginAd.this, getResources().getString(R.string.success), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginAd.this, HomeAd.class));
                                finish();
                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(LoginAd.this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(LoginAd.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(LoginAd.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("apassword", pass);
                    params.put("aemail", em);

                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(LoginAd.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }
}

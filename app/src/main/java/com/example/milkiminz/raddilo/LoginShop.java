package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


@SuppressWarnings("ALL")
public class LoginShop extends AppCompatActivity {

    private String loginUrl;

    private EditText email;
    private EditText password;
    private ProgressDialog pDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginshop);
        loginUrl = getResources().getString(R.string.loginshop);
        email = (EditText) findViewById(R.id.aemail);
        password = (EditText) findViewById(R.id.apass);

    }

    public void Login_now(View view) {
        if (!email.getText().toString().equals("") || !password.getText().toString().equals("")) {
            if (isNetworkAvailable()) {
                new AttemptLogin().execute();
            } else {
                Toast.makeText(LoginShop.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.enteremailpassword), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData2(String email){
        ContentValues values = new ContentValues();
        values.put(DBHelper.EMAIL,
                (email));
        getContentResolver().insert(
                DBHelper.CONTENT_URI, values);

    }
    private String loadData() {
        String URL = "content://com.example.milkiminz.raddilo.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
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
            pDialog = new ProgressDialog(LoginShop.this);
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
                                Toast.makeText(LoginShop.this, getResources().getString(R.string.success), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginShop.this, HomeShop.class));
                                finish();
                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(LoginShop.this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(LoginShop.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(LoginShop.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("spassword", pass);
                    params.put("semail", em);

                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(LoginShop.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }


}





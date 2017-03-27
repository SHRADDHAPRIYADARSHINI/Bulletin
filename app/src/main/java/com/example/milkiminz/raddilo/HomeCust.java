package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HomeCust extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Switch s1;
    private Switch s2;
    private Switch s3;
    private Switch s4;
    private Switch s5;
    private EditText aw;
    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    private String[] arr;
    private Spinner sp;
    private String spstring;
    private String s1string;
    private String s2string;
    private String s3string;
    private String s4string;
    private String s5string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cust);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s1 = (Switch) findViewById(R.id.s1);
        s2 = (Switch) findViewById(R.id.s2);
        s3 = (Switch) findViewById(R.id.s3);
        s4 = (Switch) findViewById(R.id.s4);
        s5 = (Switch) findViewById(R.id.s5);
        aw = (EditText) findViewById(R.id.aw);
        requestQueue = Volley.newRequestQueue(HomeCust.this);

        new AttemptLogin().execute();


        sp = (Spinner) findViewById(R.id.spinnershop);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cust, menu);
        return true;
    }

    public void sellNow(View view) {
        if (!aw.getText().toString().equals("")) { //checking the values
            if (s1.isChecked()) {
                s1string = "yes";

            } else {
                s1string = "no";
            }
            if (s2.isChecked()) {
                s2string = "yes";

            } else {
                s2string = "no";
            }
            if (s3.isChecked()) {
                s3string = "yes";

            } else {
                s3string = "no";
            }
            if (s4.isChecked()) {
                s4string = "yes";

            } else {
                s4string = "no";
            }
            if (s5.isChecked()) {
                s5string = "yes";

            } else {
                s5string = "no";
            }


            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.homecusturl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(HomeCust.this, response, Toast.LENGTH_LONG).show();
                    if (response.equals(getResources().getString(R.string.success))) {
                        Toast.makeText(HomeCust.this, getResources().getString(R.string.successful_order), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(HomeCust.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();
                    params.put("email", loadData());
                    params.put("switch1", s1string);
                    params.put("switch2", s2string);
                    params.put("switch3", s3string);
                    params.put("switch4", s4string);
                    params.put("switch5", s5string);
                    params.put("aw", aw.getText().toString());
                    params.put("shopname", spstring);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            //if approx weight is empty
            Toast.makeText(this, getResources().getString(R.string.enterweight), Toast.LENGTH_SHORT).show();
        }
    }

    private String loadData() {
        String URL = "content://com.example.milkiminz.raddilo.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feeback) {
            startActivity(new Intent(HomeCust.this, Feedback.class));
            return true;
        } else if (id == R.id.action_logout) {

            Intent p = new Intent(this, LoginCust.class);
            startActivity(p);
            finish();

        } else if (id == R.id.action_aboutdevelopers) {
            startActivity(new Intent(HomeCust.this, AboutDevelopers.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chome) {
            startActivity(new Intent(HomeCust.this, HomeCust.class));
            finish();

        } else if (id == R.id.nav_cprofile) {
            startActivity(new Intent(HomeCust.this, ProfileCust.class));
            finish();

        } else if (id == R.id.nav_cbook) {
            startActivity(new Intent(HomeCust.this, Bookings.class));
            finish();

        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(HomeCust.this, AboutUs.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class AttemptLogin extends AsyncTask<String, String, String> {


        String success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeCust.this);
            pDialog.setMessage("Logging in....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            JSONObject params = new JSONObject();
            String load_url = getResources().getString(R.string.fetchshopurl);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url, params, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jarr = response.getJSONArray("shopname");
                        arr = new String[jarr.length() + 1];
                        arr[0] = "--Select--";
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject job = jarr.getJSONObject(i);
                            arr[i + 1] = job.getString("sname");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeCust.this, android.R.layout.simple_spinner_item, arr);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        sp.setAdapter(adapter);


                        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                spstring = sp.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }


                        });
                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HomeCust.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }
}

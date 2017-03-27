package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HomeSp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lv;
    private String[] price;
    private String[] ades,email,ph,add;
    String out;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        setSupportActionBar(toolbar);

        lv=(ListView)findViewById(R.id.lv);
        requestQueue= Volley.newRequestQueue(HomeSp.this);


        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());

        } catch (JSONException e) {

        }
        String load_url = "http://139.59.47.63/fetchallads.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url, params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    //entering the values in array
                    int l = jsonArray.length();
                    price=new String[l];
                    ades=new String[l];
                    email=new String[l];
                    ph=new String[l];
                    add=new String[l];
                    for (int i = 0; i < l; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        price[i] = jsonObject.getString("aprice");
                        ades[i] = jsonObject.getString("adesc");
                        email[i] = jsonObject.getString("aemail");
                        ph[i] = jsonObject.getString("aphone");
                        add[i] = jsonObject.getString("aadd");

                    }
                    AllAdsAdapter rc = new AllAdsAdapter(HomeSp.this,price,ades,email,add,ph);
                    lv.setAdapter(rc);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeSp.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home_sp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutdevelopers) {
            startActivity(new Intent(this, AboutDevelopers.class));


        } else if (id == R.id.action_logout) {

            Intent p = new Intent(this, Loginsp.class);
            startActivity(p);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sphome) {
            startActivity(new Intent(HomeSp.this, HomeSp.class));
            finish();
        } else if (id == R.id.nav_spprofile) {
            startActivity(new Intent(HomeSp.this, ProfileSp.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

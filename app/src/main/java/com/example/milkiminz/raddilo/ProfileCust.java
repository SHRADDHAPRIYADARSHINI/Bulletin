package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileCust extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RequestQueue requestQueue;
    private TextView nm;
    private TextView add;
    private TextView ph;
    private TextView em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cust);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(ProfileCust.this);
        nm = (TextView) findViewById(R.id.name);
        add = (TextView) findViewById(R.id.address);
        ph = (TextView) findViewById(R.id.phone);
        em = (TextView) findViewById(R.id.email);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProfileCust.this, UpdateCustomer.class);
                i.putExtra("name", nm.getText().toString());
                i.putExtra("email", em.getText().toString());
                i.putExtra("address", add.getText().toString());
                i.putExtra("phone", ph.getText().toString());
                startActivity(i);
            }
        });
        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());

        } catch (JSONException e) {

        }
        String load_url = getResources().getString(R.string.custprofile);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url, params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray ar = response.getJSONArray("custdetail");

                    JSONObject ob1 = ar.getJSONObject(0);

                    nm.setText(ob1.getString("cname"));
                    add.setText(ob1.getString("cadd"));
                    ph.setText(ob1.getString("cph"));
                    em.setText(loadData());
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileCust.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    private String loadData() {
        String URL = "content://com.example.milkiminz.raddilo.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
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
        getMenuInflater().inflate(R.menu.profile_cust, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feeback) {
            startActivity(new Intent(ProfileCust.this, Feedback.class));
            return true;
        } else if (id == R.id.action_aboutdevelopers) {
            startActivity(new Intent(this, AboutDevelopers.class));


        } else if (id == R.id.action_logout) {
            Intent p = new Intent(this, LoginCust.class);
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

        if (id == R.id.nav_chome) {
            startActivity(new Intent(ProfileCust.this, HomeCust.class));
            finish();

        } else if (id == R.id.nav_cprofile) {
            startActivity(new Intent(ProfileCust.this, ProfileCust.class));
            finish();

        } else if (id == R.id.nav_cbook) {
            startActivity(new Intent(ProfileCust.this, Bookings.class));
            finish();

        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(ProfileCust.this, AboutUs.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

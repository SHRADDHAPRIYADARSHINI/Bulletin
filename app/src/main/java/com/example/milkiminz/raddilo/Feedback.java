package com.example.milkiminz.raddilo;

import android.database.Cursor;
import android.net.Uri;
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

public class Feedback extends AppCompatActivity {

    private EditText gfb;
    private String fb;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        requestQueue = Volley.newRequestQueue(this);
        gfb = (EditText) findViewById(R.id.feedback);
    }

    public void sendFeedback(View view) {
        fb = gfb.getText().toString();
        if (!fb.equals("")) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.feedback_url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals(getResources().getString(R.string.success))) {
                        Toast.makeText(Feedback.this, getResources().getString(R.string.successfully_send), Toast.LENGTH_LONG).show();//received feedback
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Feedback.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    params.put("feedback", "By" + loadData() + ":" + fb);


                    return params;
                }
            };
            // for blanck feedback
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, getResources().getString(R.string.enter_query), Toast.LENGTH_SHORT).show();
        }
    }

    private String loadData() {
        String URL = "content://com.example.milkiminz.raddilo.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
    }
}


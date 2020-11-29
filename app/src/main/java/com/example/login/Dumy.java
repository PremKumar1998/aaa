package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dumy extends AppCompatActivity implements View.OnClickListener{

    private TextView home;
    private Button reportt;
    DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumy);
        home=findViewById(R.id.home);
        reportt=findViewById(R.id.report);
        home.setOnClickListener(this);
        reportt.setOnClickListener(this);
    }
    private void report() {
        final String token = SharedPrefManager.getInstance(this).getToken();
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                Connections.URL_EXCEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                Dumy.this,
                                "Failed to download",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("Authorization","Bearer"+token);
                return map;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(getRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == home){
            startActivity(new Intent(getApplicationContext(), HomePage.class));
        }
        else if(view == reportt)
        {
            report();
        }
    }
}
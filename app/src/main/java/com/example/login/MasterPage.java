package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MasterPage extends AppCompatActivity implements View.OnClickListener {
    private EditText item,type,task,dur,loc,date,act,cat;
    private Button savebtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        date=findViewById(R.id.date);
        item=findViewById(R.id.item);
        type=findViewById(R.id.type);
        act=findViewById(R.id.activity);
        cat=findViewById(R.id.cat);
        task=findViewById(R.id.task);
        loc=findViewById(R.id.loc);
        dur=findViewById(R.id.dur);
        savebtn=findViewById(R.id.save);
        savebtn.setOnClickListener(this);
    }
    private void createLog() {

        final String token = SharedPrefManager.getInstance(this).getToken();

        final String item_id = item.getText().toString().trim();
        final String act_id = act.getText().toString().trim();
        final String task_id = task.getText().toString().trim();
        final String type_id = type.getText().toString().trim();
        final String cat_id = cat.getText().toString().trim();
        final String loc_id = loc.getText().toString().trim();
        final String duration = dur.getText().toString().trim();
        final String datee = date.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Connections.URL_CREATE_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success"))
                            {
                                Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                                finish();
                            }
                            else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Something went wrong",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                MasterPage.this,
                                "Error",
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("item", item_id);
                params.put("activity", act_id);
                params.put("task", task_id);
                params.put("type", type_id);
                params.put("location", loc_id);
                params.put("category", cat_id);
                params.put("duration", duration);
                params.put("date", datee);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if (view == savebtn) {
            createLog();
        }
    }
}
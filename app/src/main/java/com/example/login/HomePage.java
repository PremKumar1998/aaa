package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class HomePage extends AppCompatActivity {
    private TextView empcode;
    private TextView name;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        empcode=findViewById(R.id.code);
        name=findViewById(R.id.name);

        home(empcode,name);


       // empcode.setText(SharedPrefManager.getInstance(this).getEmpcode());
        //name.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    private void home(final TextView empcode, final TextView name) {
       final String token = SharedPrefManager.getInstance(this).getToken();
        progressDialog.show();
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                Connections.URL_HOME,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                //empcode.setText(obj.getString("date"));
                                JSONArray user =obj.getJSONArray("weekly_hrs");
                                for(int i=0;i<user.length();i++) {
                                    JSONObject a= user.getJSONObject(i);
                                    empcode.setText(a.getString("date"));
                                    name.setText(a.getString("day"));
                                    // name.setText(user.getString("f_name")+' '+user.getString("l_name"));
                                }
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Failed to Fetch",
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
                                HomePage.this,
                                "Login fail",
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch(id) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,MainActivity.class));
            break;
        }
        return true;
    }
}


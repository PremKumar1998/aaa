package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class HomePage extends AppCompatActivity implements View.OnClickListener {
    private TextView date;
    private TextView dummy;
    private TextView day;
    private TextView dur;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        date=findViewById(R.id.date);
        day=findViewById(R.id.day);
        dur=findViewById(R.id.dur);
        dummy=findViewById(R.id.dummy);
        dummy.setOnClickListener(this);

        home(date,day,dur);


       // empcode.setText(SharedPrefManager.getInstance(this).getEmpcode());
        //name.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    private void home(final TextView date, final TextView day, final TextView dur) {
       final String token = SharedPrefManager.getInstance(this).getToken();
        progressDialog.show();
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                Connections.URL_HOME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            StringBuilder b1= new StringBuilder();
                            StringBuilder b2= new StringBuilder();
                            StringBuilder b3= new StringBuilder();

                            //StringBuilder b2= new StringBuilder();
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                //empcode.setText(obj.getString("date"));
                                JSONArray user =obj.getJSONArray("weekly_hrs");
                                for(int i=0;i<user.length();i++) {
                                    JSONObject a= user.getJSONObject(i);
                                    b1.append(a.getString("date"));
                                    b1.append("\n");
                                    b2.append(a.getString("day"));
                                    b2.append("\n");
                                    b3.append(a.getString("duration"));
                                    b3.append("\n");
                                    // name.setText(user.getString("f_name")+' '+user.getString("l_name"));
                                }
                                date.setText(b1.toString());
                                day.setText(b2.toString());
                                dur.setText(b3.toString());
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

    @Override
    public void onClick(View view) {
        if (view == dummy){
            startActivity(new Intent(getApplicationContext(), Dumy.class));
        }
    }
}


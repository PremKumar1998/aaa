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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText empcode, passwd;
    private Button loginbtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startActivity(new Intent(getApplicationContext(), excel.class));

        setContentView(R.layout.activity_main);
        empcode = (EditText) findViewById(R.id.emp_code);
        passwd = (EditText) findViewById(R.id.pwd);
        loginbtn = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        loginbtn.setOnClickListener(this);
    }

    private void userLogin() {
        final String employeecode = empcode.getText().toString().trim();
        final String password = passwd.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Connections.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                JSONObject user = obj.getJSONObject("user");
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(

                                                user.getString("employee_code"),
                                                obj.getString("token"),
                                                user.getString("f_name"),
                                                user.getString("l_name")
                                        );
                                Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), bar.class));
                                //startActivity(new Intent(getApplicationContext(), HomePage.class));
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Login Failed",
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
                                MainActivity.this,
                                "Login fail",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_code", employeecode);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == loginbtn) {
            userLogin();
        }
    }
}
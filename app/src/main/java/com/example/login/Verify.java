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

public class Verify extends AppCompatActivity implements View.OnClickListener {
    private EditText empcode, passwd ,confpwd;
    private Button verifybtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        empcode = (EditText) findViewById(R.id.emp_code);
        passwd = (EditText) findViewById(R.id.pwd);
        confpwd =(EditText) findViewById(R.id.cpwd);
        verifybtn = (Button) findViewById(R.id.verify);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        verifybtn.setOnClickListener(this);
    }
    private void userVerify() {
        final String employeecode = empcode.getText().toString().trim();
        final String password = passwd.getText().toString().trim();
        final String conpwd = confpwd.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Connections.URL_VERIFY_REGISTER,
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
                                Toast.makeText(getApplicationContext(),"Verified Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                                finish();
                            } else if(!obj.getBoolean("success")) {
                                Toast.makeText(
                                        getApplicationContext(),obj.getString("message"),
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
                                Verify.this,
                                "Verification fail",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_code", employeecode);
                params.put("password", password);
                params.put("password_confirmation", conpwd);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == verifybtn) {
            userVerify();
        }
    }
}
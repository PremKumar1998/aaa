package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bar extends AppCompatActivity {
    private static final String TAG = "TAG";
    float barSpace = 0.05f;
    private static String FILE = "mnt/sdcard/FirstPdf.pdf";
    float groupSpace = 0.3f;
    List<String> log = new ArrayList<>();
    String date = new String();
    List<Date> dateList = new ArrayList<>();
    String resp = new String();
    List<Resp> respList= new ArrayList<>();

    BarChart barChart;
    Button switchChart,export;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        barChart = findViewById(R.id.bar_chart);
        //progressBar = findViewById(R.id.progress_circular);
        switchChart = findViewById(R.id.switchChart);
        export=findViewById(R.id.switchChart2);
        switchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChartData(switchChart.getText().toString());
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // export_pdf();
            }
        });
    }

    private void getChartData(String type) {
        final String token = SharedPrefManager.getInstance(this).getToken();
        progressDialog.show();
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                Connections.URL_EXCEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {

                                JSONArray user =obj.getJSONArray("weekly_hrs");
                                for(int i=0;i<user.length();i++) {
                                    JSONObject a= user.getJSONObject(i);
                                    resp=a.getString("date");
                                    respList.add(new Resp(resp));
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
                                bar.this,
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
    public static class Resp
    {
        String resp;
        public Resp(String resp){
            this.resp=resp;
        }
    }
    public static class Date
    {
        String date;
        public Date(String date){
            this.date=date;
        }
    }
}
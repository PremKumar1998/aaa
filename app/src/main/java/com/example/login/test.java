package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {
    private static final String TAG = "TAG";
    float barSpace = 0.05f;
    private static String FILE = "mnt/sdcard/FirstPdf.pdf";
    float groupSpace = 0.3f;

    String respid = new String();
    String datee = new String();
    int dur;
    List<Log> logList = new ArrayList<>();
    String date = new String();
    List<bar.Date> dateList = new ArrayList<>();
    String resp = new String();
    List<bar.Resp> respList= new ArrayList<>();
    TextView textView;
    BarChart barChart;
    Button switchChart,export;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView= findViewById(R.id.text);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        barChart = findViewById(R.id.bar_chart);
        //progressBar = findViewById(R.id.progress_circular);
        switchChart = findViewById(R.id.switchChart);
        export=findViewById(R.id.switchChart2);
        getChartData(textView);

        /*switchChart.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }
    private void getChartData(final TextView textView) {
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
                            //StringBuilder b1= new StringBuilder();
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {

                                JSONArray user =obj.getJSONArray("resp");
                                for(int i=0;i<user.length();i++) {
                                    JSONObject a= user.getJSONObject(i);

                                    //b1.append(a.getString("resp_name"));
                                    resp=a.getString("resp_name");
                                    respList.add(new bar.Resp(resp));

                                    //textView.setText("resp: "+respp.get(i).resp);
                                }
                                JSONArray user1 =obj.getJSONArray("dates");
                                for(int i=0;i<user1.length();i++) {
                                    JSONObject a= user1.getJSONObject(i);
                                    date=a.getString("date");
                                    dateList.add(new bar.Date(date));
                                }
                                JSONArray user2 =obj.getJSONArray("tlog");
                                for(int i=0;i<user2.length();i++) {
                                    JSONObject a= user2.getJSONObject(i);
                                    respid = a.getString("responsibility_id");
                                    datee = a.getString("date");
                                    dur = a.getInt("dur_total");
                                    logList.add(new Log(respid,datee,dur));
                                }
                                textView.setText("RespId: "+logList.get(1).respid+" \nDate: "+logList.get(1).datee+" \nDuration: "+logList.get(1).dur);
                                //textView.setText("resp: "+respp.get(i).resp);
                                //textView.setText(b1.toString());
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
                                test.this,
                                "error",
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
}
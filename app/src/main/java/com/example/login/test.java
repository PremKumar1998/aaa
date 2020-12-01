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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {
   /* private static final String TAG = "TAG";
    float barSpace = 0.05f;
    private static String FILE = "mnt/sdcard/FirstPdf.pdf";
    float groupSpace = 0.3f;
    String respnamee = new String();
    int respid2;
    String datee = new String();
    int dur;
    List<Log> logList = new ArrayList<>();
    String date = new String();
    List<bar.Date> dateList = new ArrayList<>();
    String respname = new String();
    int respid1;
    List<bar.Resp> respList= new ArrayList<>();

    List<String> respList2 = new ArrayList<>();
    List<String> dateList2 = new ArrayList<>();

    TextView textView;
    BarChart barChart;
    Button switchChart,export;*/
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //textView= findViewById(R.id.text);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        //barChart = findViewById(R.id.bar_chart);
        //progressBar = findViewById(R.id.progress_circular);
        //switchChart = findViewById(R.id.switchChart1);
        //export=findViewById(R.id.switchChart2);
        //getChartData(textView);

    }
    /*private void getChartData(final TextView type) {
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
                                    respid1 = a.getInt("id");
                                    respname=a.getString("resp_name");
                                    respList.add(new bar.Resp(respid1,respname));
                                    respList2.add(respname);
                                    //textView.setText("resp: "+respp.get(i).resp);
                                }
                                JSONArray user1 =obj.getJSONArray("dates");
                                for(int i=0;i<user1.length();i++) {
                                    JSONObject a= user1.getJSONObject(i);
                                    date=a.getString("date");
                                    dateList.add(new bar.Date(date));
                                    dateList2.add(date);
                                }
                                JSONArray user2 =obj.getJSONArray("tlog");
                                for(int i=0;i<user2.length();i++) {
                                    JSONObject a= user2.getJSONObject(i);
                                    respid2 = a.getInt("responsibility_id");
                                    if (respid2==1) {
                                        respnamee=respList.get(0).resp;
                                    }
                                    else if(respid2==2)
                                    {
                                        respnamee=respList.get(1).resp;
                                    }
                                    else
                                    {
                                        respnamee=respList.get(2).resp;
                                    }
                                    datee = a.getString("date");
                                    dur = a.getInt("dur_total");
                                    logList.add(new Log(respnamee,datee,dur));
                                }
                                textView.setText("RespId: "+dateList2.get(6));
                                //textView.setText("RespName: "+logList.get(0).respnamee+" \nDate: "+logList.get(0).datee+" \nDuration: "+logList.get(0).dur);
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
    public static class Resp
    {
        int id;
        String resp;
        public Resp(int id,String resp){
            this.id=id;
            this.resp=resp;
        }
    }
    public static class Date
    {
        String date;
        public Date(String date){
            this.date=date;
        }
    }*/
}
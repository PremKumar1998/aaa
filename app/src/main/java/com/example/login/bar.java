package com.example.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bar extends AppCompatActivity {
    private static final String TAG = "TAG";
    float barSpace = 0.05f;
    private static String FILE = "mnt/sdcard/FirstPdf.pdf";
    float groupSpace = 0.3f;
    String respnamee = new String();
    int respid2;
    String datee = new String();
    int dur;
    List<Log> logList = new ArrayList<>();
    String date = new String();
    List<Date> dateList = new ArrayList<>();
    String respname = new String();
    int respid1;
    List<Resp> respList= new ArrayList<>();

    List<String> respList2 = new ArrayList<>();
    List<String> dateList2 = new ArrayList<>();
    List<String> dateList3 = new ArrayList<>();
    TextView textView;
    BarChart barChart;
    Button switchChart,export;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        textView= findViewById(R.id.text);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        barChart = findViewById(R.id.bar_chart);
        //progressBar = findViewById(R.id.progress_circular);
        switchChart = findViewById(R.id.switchChart1);
        export=findViewById(R.id.switchChart2);
        //getChartData("Show Hours/Responsibility");

        switchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChartData(switchChart.getText().toString());
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                export_pdf();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void export_pdf() {
        barChart.buildDrawingCache();
        Bitmap bm=barChart.getDrawingCache();
        PdfDocument pdfDocument=new PdfDocument();
        PdfDocument.PageInfo pi=new PdfDocument.PageInfo.Builder(bm.getWidth(),bm.getHeight(),1).create();
        PdfDocument.Page page=pdfDocument.startPage(pi);
        Canvas canvas=page.getCanvas();
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawBitmap(bm,0,0,null);
        pdfDocument.finishPage(page);
        File root=new File(Environment.getExternalStorageDirectory(),"PDF");
        if(!root.exists()){
            root.mkdir();
        }
        File file=new File(root,"chart.pdf");
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            pdfDocument.writeTo(fileOutputStream);
            Toast.makeText(getApplicationContext(),"PDF Exported Successfully....",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }
    private void getChartData(final String type) {
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
                                    respList.add(new Resp(respid1,respname));
                                    respList2.add(respname);
                                    //textView.setText("resp: "+respp.get(i).resp);
                                }
                                JSONArray user1 =obj.getJSONArray("dates");
                                for(int i=0;i<user1.length();i++) {
                                    JSONObject a= user1.getJSONObject(i);
                                    date=a.getString("date");
                                    dateList.add(new Date(date));
                                    dateList2.add(date);
                                    dateList3.add(date);
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

                                if(type.equals("Show Hours/Responsibility")){
                                    setBarChart(respList2,dateList2,dateList3,"responsibility");
                                    switchChart.setText("Show Hours/Date");
                                }
                                else if(type.equals("Show Hours/Date")){
                                    //setBarChart(dateList2,respList2,"date");
                                    switchChart.setText("Show Hours/Responsibility");
                                }
                                //textView.setText("RespId: "+respList.get(0).id+" \nName: "+respList.get(0).resp);
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
                                bar.this,
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
        int id = 0;
        String resp = null;
        public Resp(int id,String resp){
            this.id=id;
            this.resp=resp;
        }
    }
    public static class Date
    {
        String date =null;
        public Date(String date){
            this.date=date;
        }
    }
    private void setBarChart(List<String> dataSets, List<String> groupList,List<String> groupList2, String type) {
        int[] MyColors={
                getResources().getColor(R.color.bar1),
                getResources().getColor(R.color.bar2),
                getResources().getColor(R.color.bar3),
                getResources().getColor(R.color.bar4),
                getResources().getColor(R.color.bar5),
                getResources().getColor(R.color.bar6),
                getResources().getColor(R.color.bar7),
                getResources().getColor(R.color.bar8),
                getResources().getColor(R.color.bar9),
                getResources().getColor(R.color.bar10),
                getResources().getColor(R.color.bar11),
                getResources().getColor(R.color.bar12),

        };

        ArrayList<BarDataSet> arrayList=new ArrayList<>();
        for(int i=0;i<dataSets.size();i++){
            BarDataSet barDataSet;
            barDataSet = new BarDataSet(barEntries(dataSets.get(i),groupList,groupList2.get(i),type),dataSets.get(i));
            barDataSet.setColor(MyColors[i]);
            barDataSet.setValueTextSize(5f);
            arrayList.add(barDataSet);
        }
        BarData barData=new BarData();
        for(int i=0;i<arrayList.size();i++){
            barData.addDataSet(arrayList.get(i));
        }
        barData.setValueTextSize(7f);
        barChart.setData(barData);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(groupList));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawAxisLine(true);
        barData.setBarWidth(.02f);
        Legend legend=barChart.getLegend();
        legend.setTextSize(10);
        legend.setWordWrapEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.getXAxis().setAxisMinimum(0);
        float defaultBarWidth;
        int groupCount=groupList.size();
        defaultBarWidth=(1-groupSpace)/arrayList.size()-barSpace;
        if(defaultBarWidth>=0){
            barData.setBarWidth(defaultBarWidth);
        }
        if(groupCount!=-1) {
            barChart.getXAxis().setAxisMinimum(0);
            barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupList.size());
            barChart.getXAxis().setCenterAxisLabels(true);
        }
        if(barData.getDataSetCount()>1){
            barChart.groupBars(0,groupSpace,barSpace);

        }
        barChart.invalidate();
    }

    private List<BarEntry> barEntries(String groupName, List<String> groupList,String groupList2, String type) {
        ArrayList<BarEntry> arrayList=new ArrayList<>();
        int i=0;
        while (i<groupList.size()){
            for(Log l:logList){
                if(type.equals("date")){
                    BarEntry barEntry=new BarEntry(i,l.getDur());
                    arrayList.add(barEntry);
                    i++;
                }
                else if(type.equals("responsibility")){
                    if(l.getResp().equals(groupName)) {
                        //if (l.getDate().equals(groupList2)) {
                            BarEntry barEntry = new BarEntry(i, l.getDur());
                            arrayList.add(barEntry);
                            i++;
                        //}
                    }
                }
            }
        }
        return arrayList;
    }
}
package com.example.cybercop;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    public static TextView viewresult;
    private RequestQueue mQueue;
    int backpress=0;
    public String loc,sub_loc;
    TextView locality,sub_locality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
         loc = intent.getStringExtra("loc");
        sub_loc=intent.getStringExtra("sub_loc");

        locality=(TextView)findViewById(R.id.city);
        locality.setText(loc);
        sub_locality=(TextView)findViewById(R.id.area);
        sub_locality.setText(sub_loc);
        viewresult = findViewById(R.id.crime);
        //Button buttonparse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);
        jsonParse();

      //  Toast.makeText(getApplicationContext(), loc, Toast.LENGTH_SHORT).show();

    }
    private void jsonParse() {
        String url = "http://192.168.1.42/dataset.json";
       // Toast.makeText(getApplicationContext(), "inside call", Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                          //  Toast.makeText(getApplicationContext(), "inside parse "+loc, Toast.LENGTH_SHORT).show();
                            JSONArray jsonarray = response.getJSONArray("data");

                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject crimes = jsonarray.getJSONObject(i);
                                String area = crimes.getString("location");
                                String status=crimes.getString("status");
                               // String weapon=crimes.getString("weaponUsedCd");
                                String lat=crimes.getString("lat");
                                String premis =crimes.getString("premisDesc");
                                String lon=crimes.getString("lon");
                                String statusDesc=crimes.getString("statusDesc");
                                if(area.equals(loc))
                                {
                                   // Toast.makeText(getApplicationContext(), "This area is crime zone", Toast.LENGTH_SHORT).show();
                                    viewresult.append("This area is a Crime Zone\n\n");
                                    //viewresult.append(area+ "\n\n");
                                    viewresult.append("Status: "+status+"\n\n");
                                    viewresult.append("Description: "+statusDesc+"\n\n");
                                    viewresult.append("Crime Description: "+premis+"\n\n");
                                    viewresult.append("Latitude: "+lat+"\n\n");
                                    viewresult.append("Longitude :"+lon+"\n\n");
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("VOLLEY", "ERROR");
                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void location_page(View view){
        Intent i=new Intent(MainActivity2.this,Location_crime_rate.class);
        startActivity(i);
    }
    public void safe_search_page(View view){
        Intent i=new Intent(MainActivity2.this,Safe_Search.class);
        startActivity(i);
    }
    public void OpenCamera(View view){
        Intent i=new Intent(MainActivity2.this,CameraApplication.class);
        startActivity(i);
    }
    public void sms_page(View view){
        Intent i=new Intent(MainActivity2.this,Sms.class);
        startActivity(i);
    }
    public void onBackPressed(){
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
        if (backpress>1) {
            super.onBackPressed();
        }

    }


}
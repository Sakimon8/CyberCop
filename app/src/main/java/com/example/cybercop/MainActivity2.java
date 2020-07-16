package com.example.cybercop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity2 extends AppCompatActivity {
    public static TextView viewresult;
    private RequestQueue mQueue;
    int backpress=0;
    public String loc,sub_loc;
    TextView locality,sub_locality;
    private static final String JSON_DATA_URL="https://api.npoint.io/5220f324c5e1891eabb2";
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
        parse();

      //  Toast.makeText(getApplicationContext(), loc, Toast.LENGTH_SHORT).show();

    }

    public void parse(){
        AndroidNetworking.get(JSON_DATA_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jo;
                        Location_crime_rate.Spacecraft s;
                        try
                        {
                            for(int i=0;i<response.length();i++)
                            {
                                jo=response.getJSONObject(i);

                                // int id=jo.getInt("premisCd");
                                String name=jo.getString("location");
                                String propellant=jo.getString("statusDesc");
                                //  String techExists=jo.getString("weaponDesc");
                                // String imageURL=jo.getString("imageurl");
                                String area = jo.getString("location");
                                String status=jo.getString("status");
                                // String weapon=crimes.getString("weaponUsedCd");
                                String lat=jo.getString("lat");
                                String premis =jo.getString("premisDesc");
                                String lon=jo.getString("lon");
                                String statusDesc=jo.getString("statusDesc");
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


                        }catch (JSONException e)
                        {

                            Toast.makeText(getApplicationContext(), "GOOD RESPONSE BUT JAVA CAN'T PARSE JSON IT RECEIEVED. "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();

                        Toast.makeText(getApplicationContext(), "UNSUCCESSFUL :  ERROR IS : "+anError.getMessage(), Toast.LENGTH_LONG).show();
                    }


                });

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
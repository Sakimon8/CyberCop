package com.example.cybercop;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
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
    private static final String JSON_DATA_URL="https://api.npoint.io/d17a7ef257bacbc597a5";
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
                        Location_crime_rate.Location s;
                        try
                        {
                            for(int i=0;i<response.length();i++)
                            {
                                jo=response.getJSONObject(i);
                                String area = jo.getString("location");
                                String premis =jo.getString("premisDesc");
                                String statusDesc=jo.getString("statusDesc");
                                String crime = jo.getString("crimeDesc");
                                String weapon =jo.getString("weaponDesc");
                                String gender=jo.getString("victSex");

                                if(area.equals(loc))
                                {
                                    viewresult.append("\n");
                                    viewresult.append(Html.fromHtml("<b>" + "Status Descp: " + "</b>")+statusDesc+"\n\n");
                                    viewresult.append("Premise Description: "+premis+"\n\n");
                                    viewresult.append("Crime Description: "+crime+"\n\n");
                                    viewresult.append("Weapon Description: "+weapon+"\n\n");
                                    viewresult.append("Victim Gender: "+gender+"\n\n");
                                    viewresult.setTypeface(Typeface.DEFAULT_BOLD);
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
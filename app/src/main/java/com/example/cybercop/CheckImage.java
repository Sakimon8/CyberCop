package com.example.cybercop;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CheckImage extends AsyncTask {
    private static final int REQUEST_CODE = 000;
    // public DatabaseHelper myDB;
    public String msg,msg_from;
    private TextView load;
    public String link;
    private Context context;
    private int byGetOrPost = 0;
    public CheckImage(Context context, int flag, TextView result) {
        this.context = context;
        byGetOrPost = flag;
        this.load=result;
    }
    @Override
    protected Object doInBackground(Object[] args) {
        try{
            msg = String.valueOf(args[0]);

            link = "https://hrishi1.herokuapp.com/";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(msg, "UTF-8");

            Log.i("Data",data);
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(((URLConnection) conn).getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            Log.i("result",sb.toString());

            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }
    @Override
    protected void onPostExecute(Object result){
        Toast.makeText(context,"THE IMAGE CONVERTED :\n"+String.valueOf(result),Toast.LENGTH_LONG).show();
        load.setText(String.valueOf(result));
        Log.e("LOOK", link);
        Log.e("LOOK", String.valueOf(result));



    }



}

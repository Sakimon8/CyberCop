package com.example.cybercop;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
    public String link;
    private Context context;
    private int byGetOrPost = 0;
    public CheckImage(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;

    }
    @Override
    protected Object doInBackground(Object[] args) {
//        try{
//
//             msg = String.valueOf(args[0]);
//             String msg2="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gqxzW1LYwY3Zd7ui961VMrg0TDcdXAYyvIPaRdfnfzktdzM8iN19wchAVREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAOqpgbbDZVRAcJIxJ1xhdWS1wSHJAyfILuogPKlsFM/fo7wPiujNpCGZxLg1xPi5XGiAtF+hWjJaR1/SXUfoUlxyfgFfKICPX6CfnbAGNsgrr/iE97gwDLz4YKkpM7Y8EBGbuH8nOdm8ueoaVxPD+QuJbGCB5jCk5EBGDdASE/RA94XNvD6QuAAbk/wVJiICNm8PZWBxIBc07gNOy7DNBPJJA2x5KQUQFis0KdsHA5d9wF3Y9DsY0Fzt8eLuv2K7UQFuQaQihLT7IPocrvx6ep493DnPu6L1EQHUitcELeURtPryjK7LY2t6N+JXJEBQDGcbZVURAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQH//2Q==";
//             link = "https://face-3.herokuapp.com/?bla="+URLEncoder.encode(msg, "UTF-8");
//            //link = "https://face-3.herokuapp.com/?bla="+"helloooo";
//
//            URL url = new URL(link);
//            HttpClient client = new DefaultHttpClient();
//            HttpGet request = new HttpGet();
//            request.setURI(new URI(link));
//            HttpResponse response = client.execute(request);
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(response.getEntity().getContent()));
//            StringBuffer sb = new StringBuffer("");
//            String line="";
//            while ((line = in.readLine()) != null) {
//                sb.append(line);
//                break;
//            }
//            in.close();
//            Object obj;
//            if(sb!=null) {
//                 obj = sb.toString();
//                Log.i("notnUll","not null"+String.valueOf(sb)+"no of  values");
//                return obj;
//            }
//            else {
//                Log.i("nUll","nullllll");
//                return null;
//
//            }
//
//        } catch(Exception e){
//            Object obj=new String("Exception: " + e.getMessage());
//            return obj;
//        }

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

        Log.e("LOOK", link);
        Log.e("LOOK", String.valueOf(result));



    }



}
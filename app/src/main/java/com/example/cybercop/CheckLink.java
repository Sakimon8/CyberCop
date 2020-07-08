package com.example.cybercop;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class CheckLink extends AsyncTask {
    private static final int REQUEST_CODE = 000;
    // public DatabaseHelper myDB;
    public String string;
    public String msg,msg_from;
    private Context context;
    private int byGetOrPost = 0;
    public CheckLink(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }
    @Override
    protected Object doInBackground(Object[] args) {
        try{
             msg = String.valueOf(args[0]);
//            URL aURL = new URL(msg);
//
//            System.out.println("protocol = " + aURL.getProtocol());
//            System.out.println("authority = " + aURL.getAuthority());
//            System.out.println("host = " + aURL.getHost());
//            System.out.println("port = " + aURL.getPort());
//            System.out.println("path = " + aURL.getPath());
//            System.out.println("query = " + aURL.getQuery());
//            System.out.println("filename = " + aURL.getFile());
//            System.out.println("ref = " + aURL.getRef());
            string= msg.replace("https://","").replace("https:// www.","").replace("www.","").replace("http://","").replace("http:// www.","");

            String link = "https://link-detection.herokuapp.com/?bla="+string;
         //  String link="https://link-detection.herokuapp.com/?bla=purplehorses.net/?page=bleach-244-online";
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            Object obj;
            if(sb!=null) {
                 obj = sb.toString();
                return obj;
            }
            else {
                return null;
            }

        } catch(Exception e){
            Object obj=new String("Exception: " + e.getMessage());
            return obj;
        }

    }
    @Override
    protected void onPostExecute(Object result){
        Toast.makeText(context,"The Level of maliciousness of  "+string+"  is  "+String.valueOf(result),Toast.LENGTH_LONG).show();



    }




}

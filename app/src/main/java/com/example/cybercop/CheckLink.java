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
           //  msg_from=String.valueOf(args[1]);
           // Toast.makeText(context,""+msg_from,Toast.LENGTH_LONG).show();

          //  String replacemsg=msg.replace(" ","%20");
            URL aURL = new URL(msg);

//                    System.out.println("protocol = " + aURL.getProtocol());
//                    System.out.println("authority = " + aURL.getAuthority());
//                    System.out.println("host = " + aURL.getHost());
//                    System.out.println("port = " + aURL.getPort());
//                    System.out.println("path = " + aURL.getPath());
//                    System.out.println("query = " + aURL.getQuery());
//                    System.out.println("filename = " + aURL.getFile());
//                    System.out.println("ref = " + aURL.getRef());
         //   Toast.makeText(getApplicationContext(),"Inside Try"+aURL,Toast.LENGTH_LONG).show();
            String string= msg.replace("https://","").replace("https:// www.","").replace("www.","");
           // Toast.makeText(context,""+string,Toast.LENGTH_LONG).show();

            String link = "https://link-detection.herokuapp.com/?bla="+string;
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
        Toast.makeText(context,"The Level of maicous is "+String.valueOf(result),Toast.LENGTH_LONG).show();
        if(String.valueOf(result).equals("Spam"))
        {
           // addtodb(msg,msg_from);
        }


    }

    private void addtodb(String msg,String msg_from) {
        DatabaseHelper myDB=new DatabaseHelper(context);
        boolean insertData = myDB.addData(msg,msg_from);
        if(insertData==true){
            Toast.makeText(context, "Data  from "+msg_from+"Successfully Inserted!", Toast.LENGTH_LONG).show();
            // viewItems();
        }else{
            Toast.makeText(context, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

}

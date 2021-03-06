package com.example.cybercop;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMessage extends AsyncTask {
    private static final int REQUEST_CODE = 000;
    // public DatabaseHelper myDB;
    public String msg,msg_from;

    private Context context;
    private int byGetOrPost = 0;
    public CheckMessage(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;

    }
    @Override
    protected Object doInBackground(Object[] args) {
        try{
             msg = String.valueOf(args[0]);
             msg_from=String.valueOf(args[1]);

            //post method

            String link = "https://message-check.herokuapp.com/";
            String data  = URLEncoder.encode("message", "UTF-8") + "=" +
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
        Toast.makeText(context,""+String.valueOf(result),Toast.LENGTH_LONG).show();
        String link= extractUrls(msg);
        String level="0";

        if(!link.equals("")) {
            Toast.makeText(context, "The extracted link from message is " + link, Toast.LENGTH_LONG).show();

            try {
                level = new CheckLink_forsms(context, 0).execute(link).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "level of maliciousness is " + level, Toast.LENGTH_LONG).show();


        }

        if(String.valueOf(result).equals("Spam")&& !level.equals("0"))
        {
            // Toast.makeText(context,"level of maliciousness is "+level,Toast.LENGTH_LONG).show();
            msg=msg+"\n\n\u001B SPAM  and  Link is malicious LEVEL="+level;
            addtodb(msg,msg_from);
        }
        else if(String.valueOf(result).equals("Spam"))
        {
            msg=msg+"\n\n SPAM";
            addtodb(msg,msg_from);
        }
        else if(!level.equals("0"))
        {
            msg=msg+"\n\n Link is malicious LEVEL="+level;
            addtodb(msg,msg_from);
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
    public static String extractUrls(String text)
    {
        String s="";
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            //containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
            s=text.substring(urlMatcher.start(0), urlMatcher.end(0));
        }

        return s;
    }

}

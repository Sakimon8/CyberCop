package com.example.cybercop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

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
    LinearLayout status;
    public String string;
    public String msg,msg_from;
    private Context context;
    private int byGetOrPost = 0;
    public CheckLink(Context context, int flag, LinearLayout status) {
        this.context = context;
        byGetOrPost = flag;
        this.status=status;
    }
    @Override
    protected Object doInBackground(Object[] args) {
        try{
             msg = String.valueOf(args[0]);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostExecute(Object result){
       // Toast.makeText(context,"The Level of maliciousness of  "+string+"  is  "+String.valueOf(result),Toast.LENGTH_LONG).show();
        Integer a=Integer.parseInt(String.valueOf(result));
        if(a>0) {
            status.setBackgroundColor(Color.parseColor("#FF0000"));
            int NOTIFICATION_ID = 234;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String CHANNEL_ID = "my_channel_01";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CHANNEL_ID = "my_channel_01";
                CharSequence name = "my_channel";
                String Description = "This is my channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Caution")
                    .setContentText(a+" websites have blacklisted the current website")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            notificationManager.notify(NOTIFICATION_ID, builder.build());



    }


        else {


            status.setBackgroundColor(Color.parseColor("#008000"));

        }



    }




}

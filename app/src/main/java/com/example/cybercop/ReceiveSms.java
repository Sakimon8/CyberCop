package com.example.cybercop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ReceiveSms extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i("TagInformation","Test 1");
        Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();
        final PendingResult pendingResult = goAsync();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TagInformation","Test 2");
                        Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();
                    }
                });
                SystemClock.sleep(10000);
                if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                    Bundle bundle=intent.getExtras();
                    if(bundle!=null){
                        try{
                            Object[] pdus=(Object[])bundle.get("pdus");
                            final SmsMessage msgs[] = new SmsMessage[pdus.length];
                            for(int i = 0;i < msgs.length;i++){
                                msgs[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                                final String msg_from=msgs[i].getOriginatingAddress();
                                final  String msgBody=msgs[i].getMessageBody();

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"From"+msg_from+"\nBody:"+msgBody,Toast.LENGTH_LONG).show();
                                        Log.i("TagInformation",msgBody);
                                    }
                                });

                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        }
}
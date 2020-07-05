package com.example.cybercop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceiveSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Sms Received", Toast.LENGTH_LONG).show();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle=intent.getExtras();
            SmsMessage[]msgs=null;
            String msg_from;
            if(bundle!=null){
                try{
                    Object[] pdus=(Object[])bundle.get("pdus");
                    msgs=new SmsMessage[pdus.length];
                    for(int i=0;i<msgs.length;i++){
                        msgs[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from=msgs[i].getOriginatingAddress();
                        String msgBody=msgs[i].getMessageBody();
                        new CheckMessage(context,0).execute(msgBody,msg_from);
                        Toast.makeText(context,"From"+msg_from+"\nBody:"+msgBody,Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        }
}
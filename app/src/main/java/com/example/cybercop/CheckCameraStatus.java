package com.example.cybercop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;

public class CheckCameraStatus extends AsyncTask {
    private Context context;

    public CheckCameraStatus(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected Object doInBackground(Object[] objects) {
//        Log.i("Execution Progress","Method Before Execution");

        if(isCancelled())
            return null;

        CameraManager manager = null;
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        Objects.requireNonNull(manager).registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {

            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                if(isCancelled())
                    return;

                //Camera isn't used
                super.onCameraAvailable(cameraId);

//                Log.i("Execution Progress","Method Inside");
//                Toast.makeText(context,"Camera Not in Use",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCameraUnavailable(@NonNull String cameraId) {
                if(isCancelled())
                    return;
                //Camera is Being Used
                super.onCameraUnavailable(cameraId);

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
                        .setSmallIcon(R.drawable.cybercop)
                        .setContentTitle("Caution")
                        .setContentText("Camera Being Used, Ignore this notification if you're using it.");

                Intent resultIntent = new Intent(context, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);
                notificationManager.notify(NOTIFICATION_ID, builder.build());

                Log.i("Execution Progress","Method Inside");
//                Toast.makeText(context,"Camera In Use",Toast.LENGTH_LONG).show();
            }
        }, new Handler());
        return 1;
    }
    protected void onPostExecute(Object result){
        Log.i("Execution Progress","Method Execution Finished");
    }

}

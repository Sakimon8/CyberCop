package com.example.cybercop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class CameraApplication extends AppCompatActivity {
    public static final int DPM_ACTIVATION_REQUEST_CODE = 100;

    private ComponentName adminComponent;
    private DevicePolicyManager devicePolicyManager;
    private Switch cameraSwitch;
    private Switch checkSwitch;
    private CheckCameraStatus checkCameraStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hello Tanisa
        super.onCreate(savedInstanceState);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(getPackageName(),getPackageName() + ".DeviceAdministrator");
        //hello
        // Request device admin activation if not enabled.
        if (!devicePolicyManager.isAdminActive(adminComponent)) {

            Intent activateDeviceAdmin = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            activateDeviceAdmin.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            startActivityForResult(activateDeviceAdmin, DPM_ACTIVATION_REQUEST_CODE);
        }
//
        setContentView(R.layout.activity_camera_application); // This must be called before initializing our switch!

        cameraSwitch = (Switch)this.findViewById(R.id.cameraSwitch);
        cameraSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
                try {
                    if (isEnabled) {
                        devicePolicyManager.setCameraDisabled(adminComponent, false); // Enable camera.
                    } else {
                        devicePolicyManager.setCameraDisabled(adminComponent, true); // Disable camera.
                    }
                } catch (SecurityException securityException) {
                    Log.i("Device Administrator", "Error occurred while disabling/enabling camera - " + securityException.getMessage());
                }
            }
        });
        final Context mContext = this;
        checkSwitch = (Switch)this.findViewById(R.id.CheckSwitch);
        checkSwitch.setChecked(false);
        checkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
                try {
                    if (isEnabled) {
                        checkCameraStatus = new CheckCameraStatus(mContext);
                        checkCameraStatus.doInBackground(null);
                    } else {
                        checkCameraStatus.cancel(true);
                    }
                } catch (SecurityException securityException) {
                    Log.i("Exception", "Error occurred while checking status - ");
                }
            }
        });

        if (devicePolicyManager.getCameraDisabled(adminComponent)) {
            cameraSwitch.setChecked(false);
        } else {
            cameraSwitch.setChecked(true);
        }
    }
}
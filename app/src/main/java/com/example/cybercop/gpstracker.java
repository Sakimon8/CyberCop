package com.example.cybercop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class gpstracker implements LocationListener {
    Context context;
    public static String []currentLocation=new String[2];
    public gpstracker(Context context){
        this.context=context;
    }


    public static String[] data(){
        return  currentLocation;
    }
    public Location getlocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            int MY_PERMISSIONS=0;
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions((Activity)context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS);

                }
            }
        }
     
        LocationManager lm =(LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGpsEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Location location=null;
        int MIN_TIME_BW_UPDATES = 10000;
        int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000;
        boolean canGetLocation;
        double latitude=0,longitude=0;
        try {
            LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                         location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                        Geocoder gcd = new Geocoder(context,
                                Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = gcd.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String locality = addresses.get(0).getLocality();
                                String subLocality = addresses.get(0).getSubLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                if (subLocality != null) {

                                    currentLocation[0] = locality ;
                                    currentLocation[1]=subLocality;
                                } else {

                                    currentLocation[0] = locality;
                                }



                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Not possible to load location", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

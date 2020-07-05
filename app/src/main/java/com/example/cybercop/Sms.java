package com.example.cybercop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Sms extends AppCompatActivity {
    DatabaseHelper myDB;
   public String msg;
   public boolean insertData;
    ArrayList<message> messagelist;

    message mess;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        myDB = new DatabaseHelper(this);
//Comment
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
        }
        viewItems();



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void viewItems() {

        ListView listView = (ListView) findViewById(R.id.listView);
        Cursor data = myDB.getListContents();
        messagelist = new ArrayList<>();

        int numrows=data.getCount();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{

            while(data.moveToNext()){
                //theList.add(data.getString(data.getColumnIndex(COL2)));
                mess=new message(data.getString(2),data.getString(1));
                messagelist.add(mess);
            }
            TwoColumListadapter adapter=new TwoColumListadapter(this, R.layout.list_adapter,messagelist);
            listView.setAdapter(adapter);
        }


    }



}
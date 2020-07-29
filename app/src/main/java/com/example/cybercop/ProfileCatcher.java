package com.example.cybercop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileCatcher extends AppCompatActivity {
    private static int PICK_IMAGE = 100;
    Uri imageUri;
    String encoded;
    ImageView imageView;
    TextView result;
    Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_catcher);
        imageView = (ImageView)findViewById(R.id.imgView);
        button1 = (Button)findViewById(R.id.buttonLoadPicture);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        button2 = (Button)findViewById(R.id.buttonUploadPicture);
        result=(TextView)findViewById(R.id.result);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("Loading");
                new CheckImage(getApplicationContext(),0,result).execute(encoded);
            }
        });
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inSampleSize = 6;
                Bitmap bitmap1;

                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                encoded=encodeTobase64(bitmap1,imageUri);
                //Toast.makeText(getApplicationContext(),"Returns"+encoded,Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            String s= String.valueOf(encoded.length());
            Toast.makeText(getApplicationContext(),"Image sent size=\n"+s,Toast.LENGTH_LONG).show();
            Log.i("size",s);

        }
    }
    public String encodeTobase64(Bitmap image,Uri imageUri) {

        String imageEncoded="";
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();

        if (image != null) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
            byte[] b1 = baos1.toByteArray();
            imageEncoded = Base64.encodeToString(b1, Base64.DEFAULT);

           // Toast.makeText(this, "Returns encode\n\n" + imageEncoded, Toast.LENGTH_LONG).show();

            Log.e("LOOK", imageEncoded);

        }
        return imageEncoded;
    }
}
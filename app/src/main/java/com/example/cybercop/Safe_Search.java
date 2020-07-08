package com.example.cybercop;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Safe_Search extends AppCompatActivity  {
    WebView myWebView ;
    LinearLayout status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe__search);
        myWebView= (WebView) findViewById(R.id.webview);
        status=(LinearLayout)findViewById(R.id.status);
        myWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
              //  Toast.makeText(getApplicationContext(),"Before checklink"+url,Toast.LENGTH_SHORT).show();

                new CheckLink(getApplicationContext(),0,status).execute(url);

              //  Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();

                Log.d("WebView", "your current url when webpage loading.." + url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
               // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();

                Log.d("WebView", "your current url when webpage loading.. finish" + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
               // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();

                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }


        });
        WebSettings websettings=myWebView.getSettings();
        websettings.getJavaScriptEnabled();
        myWebView.loadUrl("http://www.google.com");

    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }
}
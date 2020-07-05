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
import android.widget.Toast;

public class Safe_Search extends AppCompatActivity  {
    WebView myWebView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe__search);
        myWebView= (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();

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
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();

                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                return super.shouldOverrideUrlLoading(view, url);
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
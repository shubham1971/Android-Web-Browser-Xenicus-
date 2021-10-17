package com.example.xenicus;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {

    EditText edturl;
    Button btngo,btnrefresh;
    ProgressBar progressBar;
    WebView webview;

    String url = "https://www.google.com";


    private java.util.Timer timers = new Timer();
    private double backpress = 0;
    private TimerTask Tim;


    private BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener
           = new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.nagivation_home:

                            return true;

                        case R.id.nagivation_dashboard:

                            return true;

                        case R.id.nagivation_Back:
                            onBackPressed();
                            return true;

                        case R.id.nagivation_Forward:
                            onForwardPressed();

                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edturl=findViewById(R.id.edturl);
        btnrefresh=findViewById(R.id.btnrefresh);
        btngo=findViewById(R.id.btngo);
        webview=findViewById(R.id.webviews);
        progressBar=findViewById(R.id.progressBar);




        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);

        WebSettings websettings=webview.getSettings();
        websettings.setJavaScriptEnabled(true);


        progressBar.setProgress(100);

        webview.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);

            }
        });

        webview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });






        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.reload();

            }
        });

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edturl=findViewById(R.id.edturl);
                String urls=edturl.getText().toString();
                webview.setWebViewClient(new WebViewClient(){


                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(View.VISIBLE);
                        edturl.setText(url);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        progressBar.setVisibility(View.GONE);

                    }

                });

                webview.loadUrl(urls);

                WebSettings websettings=webview.getSettings();
                websettings.setJavaScriptEnabled(true);

            }
        });





        BottomNavigationView navigation=(BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener);

    }




    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            if (backpress == 1) {
                Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_LONG).show();
                Tim = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                backpress = 2;
                            }
                        });
                    }
                };
                timers.schedule(Tim, (int) (0));
            }
            Tim = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backpress = 1;
                        }
                    });
                }
            };
            timers.schedule(Tim, (int) (3000));
            if (backpress == 2) {
                finish();
            }
        }
    }


    public void onForwardPressed(){
        if (webview.canGoForward()){
            webview.goForward();
        }
        else{
            Toast.makeText(this, "Cant go Forward", Toast.LENGTH_SHORT).show();
        }
    }


}
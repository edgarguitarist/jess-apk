package com.letrasyvida.evaluaciones_letrasyvida;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class consulta extends AppCompatActivity {
    WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dir = preferences.getString("direccion","");
        // Toast.makeText(this, dir, Toast.LENGTH_LONG).show(); // DEBUG
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setInitialScale(100);
        webView.loadUrl(dir);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
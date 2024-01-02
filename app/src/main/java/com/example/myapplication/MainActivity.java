package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Patterns;


public class MainActivity extends AppCompatActivity {
EditText urlinput;
ImageView clearUrl;
WebView webView;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlinput = findViewById(R.id.input_text);
        clearUrl = findViewById(R.id.cancel_button);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progres_bar);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
webView.setWebChromeClient(new WebChromeClient(){
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        progressBar.setProgress(newProgress);
    }
});
loadMyUrl("google.com");
urlinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE ) {
            loadMyUrl(urlinput.getText().toString());
            return true;
        }
        return false;
    }
});
//tombol hapus(cancel)
clearUrl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        urlinput.setText("");
    }
});
        }

    void loadMyUrl (String url){
        boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
        if (matchUrl){
            webView.loadUrl(url);
        }else {
            webView.loadUrl("google.com/search?q="+url);
        }
    }
//    fungsi tombol back
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }



    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
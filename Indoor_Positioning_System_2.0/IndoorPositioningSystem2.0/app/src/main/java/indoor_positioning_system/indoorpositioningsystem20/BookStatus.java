package indoor_positioning_system.indoorpositioningsystem20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BookStatus extends Activity
{
    WebView webView;
    private ProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_status);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        Toast.makeText(getApplicationContext(), "The application will keep loading until you have the internet connection.", Toast.LENGTH_LONG).show();
        webView.setWebViewClient(new WebViewClient() {
            private static final String TAG = "HelloWebViewClient";
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "About to load:" + url);
                view.loadUrl(url);
                return true;
            }
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        webView.loadUrl(IndoorPositioning.bookStatusTwo);
    }
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_indoor_positioning, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(BookStatus.this, Help.class));
                return true;
            case R.id.history:
                startActivity(new Intent(BookStatus.this, SearchHistory.class));
                return true;
            default:
                return false;
        }
    }
}

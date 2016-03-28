package indoor_positioning_system.ips;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BookInformation extends AppCompatActivity  //AppCompatActivity
{
    WebView webView;
    //private ProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);
        String loadBookInformation = MainActivity.loadbookInformation;
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 11){
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //progressDialog = ProgressDialog.show(this, "Loading book information...", "You must have internet or network connection to view book information.");
        //progressDialog.setCancelable(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //progressDialog = ProgressDialog.show(BookInformation.this, "Loading book information...", "You must have internet or network connection to view book information.");
                //progressDialog.setCancelable(true);
                view.loadUrl("about:blank");
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            public void onPageFinished(WebView view, String url) {
                /*if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
            }
        });
        webView.loadUrl(loadBookInformation);      //displays book information with the help of MainActivity class.
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
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(BookInformation.this, Help.class));
                return true;
            /*case R.id.history:
                startActivity(new Intent(BookInformation.this, SearchHistory.class));
                return true;*/
            default:
                return false;
        }
    }
}

package smartfixsa.com.smartfix.acitivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import smartfixsa.com.smartfix.R;

public class RegisterStuffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_stuff);
        // todo url = > http://www.smartfixsa.com/register/
        WebView webView=(WebView)findViewById(R.id.webview_register);
        webView.setWebViewClient(new WebViewClient());
        // you must enable java script option
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.smartfixsa.com/register/");
    }
}

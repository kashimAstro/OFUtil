package openframeworks.util.of.OFUtil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{
    public static final String USER_AGENT_FAKE = "Mozilla/5.0 (Linux; Android 7.0; SM-G892A Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/60.0.3112.107 Mobile Safari/537.36";
    private ProgressBar ofbar;

    private class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_forum:
                    of_tools("https://forum.openframeworks.cc",ofbar,false);
                    return true;
                case R.id.navigation_addons:
                    of_tools("http://ofxaddons.com/categories",ofbar,false);
                    return true;
                case R.id.navigation_documentation:
                    of_tools("http://openframeworks.cc/documentation/",ofbar,true);
                    return true;
                case R.id.navigation_github:
                    of_tools("https://github.com/openframeworks/openFrameworks",ofbar,false);
                    return true;
            }
            return false;
        }
    };

    public void of_tools(String _url, ProgressBar _ofbar, Boolean _scale)
    {
        WebView forum = (WebView) findViewById(R.id.forum);
        forum.clearCache(true);
        forum.clearHistory();
        forum.getSettings().setJavaScriptEnabled(true);
        forum.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        forum.setWebViewClient(new AppWebViewClients(_ofbar));
        forum.getSettings().setUserAgentString(USER_AGENT_FAKE);
        forum.getSettings().setBuiltInZoomControls(_scale);
        if(_scale) {
            forum.getSettings().setLoadWithOverviewMode(true);
            forum.getSettings().setUseWideViewPort(true);
        }
        forum.loadUrl(_url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ofbar = (ProgressBar)findViewById(R.id.pbarof);
        of_tools("https://forum.openframeworks.cc",ofbar,false);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}

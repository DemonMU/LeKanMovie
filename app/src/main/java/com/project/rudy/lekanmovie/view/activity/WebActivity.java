package com.project.rudy.lekanmovie.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.utils.NoAdWebViewClient;
import com.project.rudy.lekanmovie.view.widget.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by maoyan on 2018/5/11.
 */

public class WebActivity extends BaseActivity {
    private X5WebView mX5WebView;
    private LinearLayout mLayout;
    private TextView mWebViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_web, null);
        mX5WebView = findViewById(R.id.webview);
        mWebViewProgress = findViewById(R.id.webview_progress);


        initWebView(getIntent().getStringExtra("url"));
    }

    private void initWebView(String url) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat
                    .checkSelfPermission(WebActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat
                        .requestPermissions(WebActivity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat
                        .requestPermissions(WebActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        WebSettings webSettings = mX5WebView.getSettings();
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //开启DOM形式存储
        webSettings.setDomStorageEnabled(true);

        //开启数据库形式存储
        webSettings.setDatabaseEnabled(true);

        //缓存数据的存储地址
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();

        webSettings.setAppCachePath(appCacheDir);

        //开启缓存功能
        webSettings.setAppCacheEnabled(true);

        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setAllowFileAccess(true);

        mX5WebView.setWebChromeClient(new WebChromeClient() {

            //配置webview地理位置定位的权限
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    mWebViewProgress.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    mWebViewProgress.setText(progress);
                }
            }
        });
        mX5WebView.setWebViewClient(new NoAdWebViewClient(mX5WebView));
        mX5WebView.loadUrl(url);

    }

    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i("zhangle15", "onKeyDown: "+mX5WebView.canGoBack());
            if (mX5WebView != null && mX5WebView.canGoBack()) {
                mX5WebView.goBack();
                return true;
            } else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }
}

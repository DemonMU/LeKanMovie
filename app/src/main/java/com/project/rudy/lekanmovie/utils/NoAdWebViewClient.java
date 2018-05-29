package com.project.rudy.lekanmovie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.SslErrorHandler;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by maoyan on 2018/5/11.
 */

public class NoAdWebViewClient extends WebViewClient {
    private WebView webView;
    private boolean isClose;


    public NoAdWebViewClient(WebView webView) {
        this.webView = webView;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(isClose){ //如果线程正在运行就不用重新开启一个线程了
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                isClose = true;
                while (isClose){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0x001);
                }
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String js = getClearAdDivJs();
            Log.v("adJs",js);
            webView.loadUrl(js);
        }
    };


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String js = getClearAdDivJs();
        Log.v("zhangle15", "adJs" + js);
        view.loadUrl(js);
    }


    public String getClearAdDivJs() {
        String js = "javascript:";
        js += "var adDiv" + "= document.getElementById('" + "clientDownload" + "');if(adDiv != null)adDiv.parentNode.removeChild(adDiv);"+
              "var adDiv2"+ "= document.getElementById('" + "clientadcinema" + "');if(adDiv2 != null)adDiv2.parentNode.removeChild(adDiv2);"+
              "var adDiv1"+ "= document.getElementsByClassName('" + "_1QFIS" + "');if(adDiv1[0] != null)adDiv1[0].parentNode.removeChild(adDiv1[0]);";

        return js;
    }


}

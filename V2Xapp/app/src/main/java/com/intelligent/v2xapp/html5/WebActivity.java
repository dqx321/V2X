package com.intelligent.v2xapp.html5;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseActivity;
import com.vise.common_utils.log.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView myWebView;
    @BindView(R.id.refrush)
    View refrush;
    @BindView(R.id.progress)
    View progress;
    boolean loadError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initWeb();
    }

    private void initWeb() {
        myWebView.getSettings().setJavaScriptEnabled(true);//可以使用js

        myWebView.getSettings().setDefaultTextEncodingName("GBK");//设置编码格式

        myWebView.setWebViewClient(new WebViewClient());//限制在webview中打开网页，不用默认浏览器

        myWebView.getSettings().setBuiltInZoomControls(false);//设置是否支持缩放

//        myWebView.addJavascriptInterface(obj,str);//向html页面注入java对象，在Android4.2之
        myWebView.loadUrl("file:///android_asset/a.html");
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//如果网页里面有超链接,点击在本网页打开,如果不设置,会打开系统浏览器
                return true;
            }
            /**
             * 网页页面开始加载的时候，执行的回调方法
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//网页页面开始加载的时候

                refrush.setVisibility(View.GONE);//隐藏加载错误界面
                progress.setVisibility(View.GONE);//隐藏网页内容界面
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 网页加载结束的时候执行的回调方法
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {//网页加载结束的时候
                if (!loadError) {

                    refrush.setVisibility(View.GONE);
                    myWebView .setVisibility(View.VISIBLE);
                } else {
                    //您找的页面暂时走丢了...

                    refrush.setVisibility(View.VISIBLE);//显示加载错误界面
                }
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadError = true;

                LogUtils.e("1111======");
            }

            //
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                loadError = true;
                LogUtils.e("22222======");

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
//                loadError = true;
                LogUtils.e("333333======" + errorResponse.getEncoding());


            }

            /**
             * 页面加载错误时执行的方法，但是在6.0以下，有时候会不执行这个方法
             * @param view
             * @param request
             * @param error
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadError = true;
               LogUtils.e("web===error===" + error.toString());

            }
        });

    }

}

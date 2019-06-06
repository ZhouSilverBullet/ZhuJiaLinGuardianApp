package com.sdxxtop.zjlguardian.ui.learn.news;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sdxxtop.ui.widget.TitleView;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.base.GBaseMvpActivity;
import com.sdxxtop.zjlguardian.wxapi.WxShareHelper;

import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Des:  文章详情
 */
public class NewsDetailsActivity extends GBaseMvpActivity<NewsDetailPresenter> implements NewDetailContract.IView {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tv_title)
    TitleView mTitleView;

    private String article_path; // 文章id
    private boolean isHasShare;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitleView.setTitleValue(title);
            }
        });

        article_path = getIntent().getStringExtra("article_path");
        isHasShare = getIntent().getBooleanExtra("isHasShare", false);

        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示

        // 设置WebView的客户端
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(false);
        // 设置缓存模式,一共有四种模式
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(false);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(false);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        webView.loadUrl(article_path);

        if (isHasShare) {
            mTitleView.getTvRight().setVisibility(View.VISIBLE);
            mTitleView.getTvRight().setText("");
            mTitleView.getTvRight().setBackgroundResource(R.drawable.share);
            mTitleView.getTvRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WxShareHelper.getInstance().shareWebpage(mTitleView.getTvTitle().getText().toString().trim(),
                            mTitleView.getTvTitle().getText().toString().trim(), article_path);
                }
            });
        }
    }

    @Override
    protected void initData() {

    }
}

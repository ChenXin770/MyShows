package myshow.cx.com.myshows.user;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by chenxin
 */

public class WebChromeClientImpl extends WebChromeClient {


    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    // 播放网络视频时全屏会被调用的方法
    public void onShowCustomView(View view,
                                 CustomViewCallback callback) {
        // 如果一个视图已经存在，那么立刻终止并新建一个

    }

    @Override
    // 视频播放退出全屏会被调用的
    public void onHideCustomView() {
        // Log.i(LOGTAG, "set it to webVew");
    }

    // 网页标题
    @Override
    public void onReceivedTitle(WebView view, String title) {
    }

    @Override
    // 当WebView进度改变时更新窗口进度
    public void onProgressChanged(WebView view, int newProgress) {

    }

}

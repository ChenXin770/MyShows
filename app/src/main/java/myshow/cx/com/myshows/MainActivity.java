package myshow.cx.com.myshows;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import myshow.cx.com.myshows.user.WebChromeClientImpl;
import myshow.cx.com.myshows.user.WebViewClientImpl;
import myshow.cx.com.myshows.user.WuJIManager;
import myshow.cx.com.myshows.user.wuji_interface.OnAutoClickListener;
import myshow.cx.com.myshows.user.wuji_interface.OnChangeIPSuccess;
import myshow.cx.com.myshows.user.wuji_interface.OnLoginSuccess;
import myshow.cx.com.myshows.utils.Rand;
import myshow.cx.com.myshows.utils.ThreadManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnChangeIPSuccess, OnLoginSuccess,OnAutoClickListener {

    private TextView tv_status;
    private Button bt_start;
    private Button bt_login;
    private Button bt_reset;
    private Button bt_exit;
    private WebView webView0;
  /*  private WebView webView1;
    private WebView webView2;
    private WebView webView3;
    private WebView webView4;*/
    private String[] urls = {"https://cpu.baidu.com/1001/12abe6c0", "http://www.sina.com.cn/", "http://www.163.com/", "https://www.baidu.com/", "http://www.sina.com.cn/"};
    private List<WebViewClientImpl> webViewClients = new ArrayList<>();

    private Timer timer;
    private int totalVisitor;

    private WuJIManager wuJIManager;
    private boolean isLogin;
    //是否要去显示
    private boolean isShow = true;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        wujiInit();
        initTimer();
    }

    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                totalVisitor = 0;
                for (WebViewClientImpl webViewClient : webViewClients) {
                    totalVisitor += webViewClient.count;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("状态：  浏览量 = "+totalVisitor);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void wujiInit() {
        wuJIManager = new WuJIManager(this);
        wuJIManager.setOnChangeIPSuccess(this);
        wuJIManager.setOnLoginSuccess(this);
        wuJIManager.myLogin(AppConfig.VPN_USER, AppConfig.VPN_PASS, AppConfig.VPN_ZYZX, AppConfig.VPN_DULI);
    }

    private void initView() {
        tv_status = (TextView) findViewById(R.id.tv_status);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_reset = (Button) findViewById(R.id.bt_reset);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        webView0 = (WebView) findViewById(R.id.wv_show0);
       /* webView1 = (WebView) findViewById(R.id.wv_show1);
        webView2 = (WebView) findViewById(R.id.wv_show2);
        webView3 = (WebView) findViewById(R.id.wv_show3);
        webView4 = (WebView) findViewById(R.id.wv_show4);*/
        bt_exit.setOnClickListener(this);
        bt_start.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        initWebView(webView0);
        webView0.loadUrl(urls[0]);
       /* initWebView(webView1);
        initWebView(webView2);
        initWebView(webView3);
        initWebView(webView4);*/
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initWebView(WebView webView) {
        WebViewClientImpl client = new WebViewClientImpl();
        client.setAutoClickListener(this);
        webViewClients.add(client);
        webView.setWebViewClient(client);
        webView.setWebChromeClient(new WebChromeClientImpl());

        // WebView.setWebContentsDebuggingEnabled(true);
        //cookie
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        CookieManager.setAcceptFileSchemeCookies(true);

        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "Latte");
        settings.setUserAgentString(Rand.raUserAgent());

        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                if (isShow) {
                    bt_start.setText("停止");
                    isShow = false;
                } else {
                    bt_start.setText("开始");
                    isShow = true;
                    stopShow();
                }
                startShow();
                break;
            case R.id.bt_reset:
                resetConfig();
                break;
            case R.id.bt_exit:
                exitApp();
                break;
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void stopShow() {
        wuJIManager.setLoop(false);
    }

    private void login() {
        if (isLogin) {
            Toast.makeText(this, "您已经登录VPN", Toast.LENGTH_SHORT).show();
            return;
        }
        wuJIManager.myLogin(AppConfig.VPN_USER, AppConfig.VPN_PASS, AppConfig.VPN_ZYZX, AppConfig.VPN_DULI);
    }

    private void exitApp() {
        finish();
        System.exit(0);
    }

    private void resetConfig() {

    }

    private void startShow() {
        wuJIManager.startShow();
        if (!isLogin) {
            Toast.makeText(this, "还未登入VPN", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onChangeIPSuccess() {
        Toast.makeText(this, "Change IP Success", Toast.LENGTH_SHORT).show();
        clearWebViewAllData(webView0);
       /* clearWebViewAllData(webView1);
        clearWebViewAllData(webView2);
        clearWebViewAllData(webView3);
        clearWebViewAllData(webView4);*/
        webView0.loadUrl(urls[0]);
      /*  webView1.loadUrl(urls[1]);
        webView2.loadUrl(urls[2]);
        webView3.loadUrl(urls[3]);
        webView4.loadUrl(urls[4]);*/

    }

    private void clearWebViewAllData(WebView webView) {
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearMatches();
        webView.clearDisappearingChildren();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        isLogin = true;
    }

    private void autoClick(final WebView wv) {
        Log.d(TAG, "autoClick:" + wv);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = wv.getUrl();
                Log.d(TAG, "autoClick " + url);
                if (url != null && (url.startsWith("http://cpu.baidu.com/") || url.startsWith("https://cpu.baidu.com/")) && !url.contains("/detail/")) {
                    String  strCmd = "javascript:function GetRandomNum(Min,Max)" +
                            "{   \n" +
                            "var Range = Max - Min;   \n" +
                            "var Rand = Math.random();   \n" +
                            "return(Min + Math.round(Rand * Range));   \n" +
                            "}   " +
                            "if($(\".video-img\").length>0){$(\".video-img\")[GetRandomNum(0,$(\".video-img\").length)].click();}";
                    wv.loadUrl(strCmd);
                    ThreadManager.getLongPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(Rand.RaInt(1000, 4000));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    clickVideo(wv);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void clickVideo(final WebView wv) {
        Log.d(TAG, "videoClick:" + wv.getUrl());
        wv.setWebViewClient(new WebViewClient());
        String strCmd = "javascript:function GetRandomNum(Min,Max)" +
                "{   \n" +
                "var Range = Max - Min;   \n" +
                "var Rand = Math.random();   \n" +
                "return(Min + Math.round(Rand * Range));   \n" +
                "}   " +
                "if($(\".vjs-big-play-button\").length>0){$(\".vjs-big-play-button\")[0].click();}";
        wv.loadUrl(strCmd);
    }

    @Override
    public void onAutoClick() {
        autoClick(webView0);
    }
}

package myshow.cx.com.myshows;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import myshow.cx.com.myshows.user.WebChromeClientImpl;
import myshow.cx.com.myshows.user.WebViewClientImpl;
import myshow.cx.com.myshows.user.WuJIManager;
import myshow.cx.com.myshows.user.wuji_interface.OnAutoClickListener;
import myshow.cx.com.myshows.user.wuji_interface.OnChangeIPSuccess;
import myshow.cx.com.myshows.user.wuji_interface.OnCloseIPSuccess;
import myshow.cx.com.myshows.user.wuji_interface.OnLoginSuccess;
import myshow.cx.com.myshows.utils.Rand;
import myshow.cx.com.myshows.utils.ThreadManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnChangeIPSuccess, OnLoginSuccess,OnAutoClickListener,OnCloseIPSuccess {

    private TextView tv_status;
    private Button bt_start;
    private Button bt_exit;
    private Button bt_setting;
    private Button bt_changeIP;
    private WebView webView;
    private String[] urls = {"https://cpu.baidu.com/1025/c5b5643f", "https://cpu.baidu.com/1001/12abe6c0", "https://cpu.baidu.com/1001/12abe6c0", "https://cpu.baidu.com/1001/12abe6c0", "https://cpu.baidu.com/1001/12abe6c0"};
    //private List<WebViewClientImpl> webViewClients = new ArrayList<>();


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
             /*   for (WebViewClientImpl webViewClient : webViewClients) {
                    totalVisitor = webViewClient.getCount();
                }*/
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
        wuJIManager.setOnCloseIPSuccess(this);
        //wuJIManager.initAndShow();
    }

    private void initView() {
        tv_status = (TextView) findViewById(R.id.tv_status);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_setting = (Button) findViewById(R.id.bt_setting);
        bt_changeIP = (Button) findViewById(R.id.bt_changeIP);
        webView = (WebView) findViewById(R.id.wv_show0);
        bt_changeIP.setOnClickListener(this);
        bt_start.setOnClickListener(this);
        bt_setting.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        //initWebView(webView);
        //webView.loadUrl(urls[0]);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initWebViewSetting(WebView webView) {
        WebViewClientImpl client = new WebViewClientImpl();
        //client.setAutoClickListener(this);
        //webViewClients.add(client);
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
               /* if (isShow) {
                    bt_start.setText("停止");
                    isShow = false;
                } else {
                    bt_start.setText("开始");
                    isShow = true;
                    stopShow();
                }*/
                startShow();
                break;
            case R.id.bt_setting:
                resetConfig();
                break;
            case R.id.bt_changeIP:
                changeIPAndShow();
                break;
            case R.id.bt_exit:
                exitApp();
                break;
        }
    }

    private void changeIPAndShow() {
        wuJIManager.changeIPAndShow();
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
        wuJIManager.closeIPOnThread();
    }

    private void resetConfig() {
        showConfigDialog();
    }

    private void startShow() {
       // wuJIManager.startShow();
        wuJIManager.initAndShow();
      /*  if (!isLogin) {
            Toast.makeText(this, "还未登入VPN", Toast.LENGTH_SHORT).show();
            return;
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onChangeIPSuccess() {
        removeAllView();
        Toast.makeText(this, "Change IP Success", Toast.LENGTH_SHORT).show();
        clearWebViewAllData(webView);
        resetUserAgent();
        webView.loadUrl(urls[0]);
        totalVisitor ++;
        tv_status.setText("状态： 浏览量="+totalVisitor);
    }

    private void removeAllView() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void clearWebViewAllData(WebView webView) {
        webView.clearMatches();
        webView.clearDisappearingChildren();
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        resetUserAgent();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void resetUserAgent() {
        initWebViewSetting(webView);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        isLogin = true;
    }

    private void showConfigDialog() {
    /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.config_layout, null);
        customizeDialog.setTitle("设置");
        customizeDialog.setView(dialogView);

        final EditText txtUrl =
                (EditText) dialogView.findViewById(R.id.txtUrl);
        final EditText txtVpnUserName = (EditText) dialogView.findViewById(R.id.txtVpnUserName);
        final EditText txtVpnPwd = (EditText) dialogView.findViewById(R.id.txtVpnpwd);
        final Switch swVpnType = (Switch) dialogView.findViewById(R.id.swVpnType);
        final Switch swIsVpnDuLi = (Switch) dialogView.findViewById(R.id.swIsVpnDuLi);

        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* UserInfo.showURL = txtUrl.getText().toString();
                        UserInfo.vpnUser = txtVpnUserName.getText().toString();
                        UserInfo.vpnPass = txtVpnPwd.getText().toString();
                        UserInfo.vpnDuLi = swIsVpnDuLi.isChecked() ? 1 : 0;
                        UserInfo.vpnZYZX = swVpnType.isChecked() ? 0 : 1;*/
                    }
                });
        customizeDialog.show();
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
        autoClick(webView);
    }

    @Override
    public void onCloseIPSuccess() {
        finish();
    }
}

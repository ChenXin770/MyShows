package myshow.cx.com.myshows.user;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import myshow.cx.com.myshows.user.wuji_interface.OnAutoClickListener;
import myshow.cx.com.myshows.utils.Rand;
import myshow.cx.com.myshows.utils.ThreadManager;


/**
 * Created by chenxin
 */

public class WebViewClientImpl extends WebViewClient {

    private int count = 0;
    public static String TAG = "WebViewClientImpl";
    private OnAutoClickListener autoClickListener;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
       /* count = count + 1;
        Log.d("WebViewClientImpl", count+"");
        onMyPageFinished(view);
        if (autoClickListener != null) {
            autoClickListener.onAutoClick();
        }*/
    }

    public int getCount() {
        return count;
    }

    private void onMyPageFinished(WebView wv){
        String nowUrl=wv.getUrl();
        Log.d(TAG, "onMyPageFinished:" + nowUrl);
        autoScroll(wv);
        String cmd="javascript:function GetRandomNum(Min,Max)" +
                "{   \n" +
                "var Range = Max - Min;   \n" +
                "var Rand = Math.random();   \n" +
                "return(Min + Math.round(Rand * Range));   \n" +
                "}   " ;

        String reg1="http[s]?://cpu\\.baidu\\.com/\\d+/\\w+[^/]*?";
        String reg2="http[s]?://cpu\\.baidu\\.com/\\d+/\\w+/detail/.*";
        String reg3="http[s]?://cpro\\.baidu\\.com/cpro/ui/uijs\\.php.*";
        if(nowUrl==null || !nowUrl.startsWith("http")){
            return;
        }else if(nowUrl.matches(reg1)){
            Log.d(TAG, "onPageFinished:主页"+nowUrl );
            Log.d(TAG, "onPageFinished:主页--点击详情"+nowUrl );
            cmd+="var b=$(\"a\").filter(function(){return($(this).attr(\"href\").indexOf(\"/detail/\")>0)});" +
                    "var link=b[GetRandomNum(0,b.length-1)];" +
                    "$('html, body').animate({  \n" +
                    "                    scrollTop: $(link).parent().offset().top  \n" +
                    "                }, 500); $(link).click();";

        }else if(nowUrl.matches(reg2)){
            Log.d(TAG, "onPageFinished:详情页"+nowUrl );
            cmd+="var b=$(\".ad-item\").find(\"a\");" +
                    "var link=b[GetRandomNum(0,b.length-1)];" +
                    "$('html, body').animate({  \n" +
                    "                    scrollTop: $(link).parent().offset().top  \n" +
                    "                }, 500); $(link).click();";
        }else if(nowUrl.matches(reg3)){
            cmd+="var link=document.links[GetRandomNum(0,document.links.length-2)];link.click()";
            Log.d(TAG, "onPageFinished:百度广告页" +nowUrl);
            //  videoClickCount.incrementAndGet();
        }else if(!nowUrl.contains("//cpu.baidu.com/redirect") && !nowUrl.contains("//m.baidu.com/baidu.php") && !nowUrl.contains("//m.baidu.com/mobads.php")){
            Log.d(TAG, "onPageFinished:第三方广告页" +nowUrl);
        }else {
            Log.d(TAG, "onPageFinished:其他页面" +nowUrl);
        }
        wv.loadUrl(cmd);

    }

    private void autoScroll(final WebView wv) {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Rand.RaInt(10, 50));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int scrollTimes = Rand.RaInt(5, 10);
                //   int[] heights = new int[scrollTimes];
                for (int i = 0; i < scrollTimes; i++) {
                    wv.post(new Runnable() {
                        @Override
                        public void run() {
                            int measuredHeight = wv.getContentHeight();
                            if (measuredHeight > 0) {
                                final int heightf = measuredHeight * Rand.RaInt(50, 100) / 100;
                                Log.d(TAG, "autoScroll:" + heightf + "/" + measuredHeight);
                                wv.scrollTo(0, heightf);
                            }
                        }
                    });
                    try {
                        Thread.sleep(Rand.RaInt(10, 50));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setAutoClickListener(OnAutoClickListener autoClickListener) {
        this.autoClickListener = autoClickListener;
    }
}

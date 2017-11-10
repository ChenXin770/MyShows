package myshow.cx.com.myshows.user;

import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import myshow.cx.com.myshows.user.wuji_interface.OnChangeIPSuccess;
import myshow.cx.com.myshows.user.wuji_interface.OnLoginSuccess;
import myshow.cx.com.myshows.utils.ThreadManager;


import static org.wuji.wujivpninterface.changeip;
import static org.wuji.wujivpninterface.closeip;
import static org.wuji.wujivpninterface.init;
import static org.wuji.wujivpninterface.login;

/**
 * Created by Administrator on 2017/11/9.
 */

public class WuJIManager {

    private static final int INIT = 1000;
    private static final int LOGIN = 1001;
    private static final int CLOSE = 1002;
    private static final int CHANGE= 1003;
    private static final String TAG = "WuJIManager";
    private OnChangeIPSuccess onChangeIPSuccess;
    private OnLoginSuccess onLoginSuccess;
    private boolean isLoop = true;
    private Context mContext;

    public WuJIManager(Context context) {
        this.mContext = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INIT:
                        break;
                    case LOGIN:
                        if (onLoginSuccess != null) {
                            onLoginSuccess.onLoginSuccess();
                        }
                        break;
                    case CLOSE:
                        break;
                    case CHANGE:
                        if (onChangeIPSuccess != null) {
                            onChangeIPSuccess.onChangeIPSuccess();
                        }
                        break;
                }
        }
    };

    public void  startShow(){
        ThreadManager.getLongPool().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        isLoop = true;
                        while (isLoop) {
                            SystemClock.sleep(11000);
                            String closeIP = closeip(mContext);
                            Log.d(TAG, "closeIP::::"+closeIP);
                            if (closeIP.startsWith("0")) {
                                String result = changeip(mContext);
                                Log.d(TAG, "changeip::::"+result);
                                if (result.startsWith("0")) {
                                    Message message = handler.obtainMessage();
                                    message.what = CHANGE;
                                    handler.sendMessage(message);
                                }
                            }
                        }
                    }
                }
        );
    }

    public void myLogin(final String vpnUser, final String vpnPass, final int vpnZyzx, final int vpnDuli) {
        ThreadManager.getLongPool().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        String init = init(mContext);
                        Log.d(TAG, "init::::" + init);
                        //ip is working
                        if (init.startsWith("3")) {
                           /* String closeIP = closeip(mContext);
                            Log.d(TAG, "closeip::::" + closeIP);
                            if (closeIP.startsWith("0")) {

                            }*/
                           return;
                        }
                        if (init.startsWith("0")) {
                            String result = login(mContext,vpnUser, vpnPass, vpnZyzx, vpnDuli);
                            Log.d(TAG, "login::::" + result);
                            if (result.startsWith("0")) {
                                Message message = handler.obtainMessage();
                                message.what = LOGIN;
                                handler.sendMessage(message);
                            }
                        }
                    }
                }
        );
    }

    public void setOnChangeIPSuccess(OnChangeIPSuccess onChangeIPSuccess) {
        this.onChangeIPSuccess = onChangeIPSuccess;
    }

    public void setOnLoginSuccess(OnLoginSuccess onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }
}

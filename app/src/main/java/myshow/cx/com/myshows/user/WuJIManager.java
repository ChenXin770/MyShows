package myshow.cx.com.myshows.user;

import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import myshow.cx.com.myshows.AppConfig;
import myshow.cx.com.myshows.user.wuji_interface.OnChangeIPSuccess;
import myshow.cx.com.myshows.user.wuji_interface.OnCloseIPSuccess;
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
    private static final int CHANGE = 1003;
    private static final String TAG = "WuJIManager";
    private OnChangeIPSuccess onChangeIPSuccess;
    private OnLoginSuccess onLoginSuccess;
    private OnCloseIPSuccess onCloseIPSuccess;
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
                    if (onCloseIPSuccess != null) {
                        onCloseIPSuccess.onCloseIPSuccess();
                    }
                    break;
                case CHANGE:
                    if (onChangeIPSuccess != null) {
                        onChangeIPSuccess.onChangeIPSuccess();
                    }
                    break;
            }
        }
    };

    public void startShow() {
        ThreadManager.getLongPool().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        isLoop = true;
                        while (isLoop) {
                            SystemClock.sleep(10500);
                            closeAndChangeIP();
                        }
                    }
                }
        );
    }

    public void initAndShow() {
        ThreadManager.getLongPool().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        String init = init(mContext);
                        Log.d(TAG, "init::::" + init);
                        if (init.startsWith("0")) {
                            String login = login(mContext, AppConfig.VPN_USER, AppConfig.VPN_PASS, AppConfig.VPN_ZYZX, AppConfig.VPN_DULI);
                            Log.d(TAG, "login::::" + login);
                            if (login.startsWith("0")) {
                                Message message = handler.obtainMessage();
                                message.what = LOGIN;
                                handler.sendMessage(message);

                                String changeip = changeip(mContext);
                                Log.d(TAG, "changeip::::" + changeip);
                                if (changeip.startsWith("0")) {
                                    message = handler.obtainMessage();
                                    message.what = CHANGE;
                                    handler.sendMessage(message);
                                }
                            } else if (login.startsWith("6")) {

                            }
                        } else if (init.startsWith("3")) {
                                closeAndChangeIP();
                        }
                    }
                }
        );
    }

    private void closeAndChangeIP() {
        String closeIP = closeip(mContext);
        Log.d(TAG, "closeIP::::" + closeIP);
        if (closeIP.startsWith("0")) {
            String changeIP = changeip(mContext);
            Log.d(TAG, "changeip::::" + changeIP);
            if (changeIP.startsWith("0")) {
                Message message2 = handler.obtainMessage();
                message2.what = CHANGE;
                handler.sendMessage(message2);
            } else if (changeIP.startsWith("5")) {
                closeIP = closeip(mContext);
                Log.d(TAG, "closeIP::::" + closeIP);
            }
        }
    }

    public void changeIPAndShow() {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                String closeIP = closeip(mContext);
                Log.d(TAG, "closeIP::::" + closeIP);
                if (closeIP.startsWith("0")) {
                    String changeIP = changeip(mContext);
                    Log.d(TAG, "changeip::::" + changeIP);
                    if (changeIP.startsWith("0")) {
                        Message message2 = handler.obtainMessage();
                        message2.what = CHANGE;
                        handler.sendMessage(message2);
                    } else if (changeIP.startsWith("5")) {
                        closeIP = closeip(mContext);
                        Log.d(TAG, "closeIP::::" + closeIP);
                    }
                }
            }
        });
    }

    public void closeIPOnThread() {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                String closeIP = closeip(mContext);
                Log.d(TAG, "closeIP::::" + closeIP);
                if (closeIP.startsWith("0")) {
                    Message message2 = handler.obtainMessage();
                    message2.what = CLOSE;
                    handler.sendMessage(message2);
                }
            }
        });
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
                            String result = login(mContext, vpnUser, vpnPass, vpnZyzx, vpnDuli);
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

    public void setOnCloseIPSuccess(OnCloseIPSuccess onCloseIPSuccess) {
        this.onCloseIPSuccess = onCloseIPSuccess;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }
}

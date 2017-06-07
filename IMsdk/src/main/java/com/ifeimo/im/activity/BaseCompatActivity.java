package com.ifeimo.im.activity;

import android.content.Context;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.ifeimo.im.common.bean.chat.BaseChatBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.util.PManager;
import com.ifeimo.im.framwork.ChatWindowsManage;
import com.ifeimo.im.framwork.interface_im.ILife;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.framwork.connect.IMConnectManager;
import com.ifeimo.im.framwork.message.MessageManager;


/**
 * Created by lpds on 2017/1/9.
 */
abstract class BaseCompatActivity extends AppCompatActivity implements IMWindow {

    protected Toast toast;

    protected Handler handler = new Handler();

    protected Loader loader;

    protected static final String TGA = "XMPP_Activity";

    private final String tip1 = this.getClass().getSimpleName() + " 登陆成功 ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ILife) ChatWindowsManage.getInstence()).onCreate(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void setSoft() {
        getListView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm.isActive() && getCurrentFocus() != null) {
                            if (getCurrentFocus().getWindowToken() != null) {
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (IMConnectManager.getInstance().getConnection() == null) {
            IMConnectManager.getInstance().runConnectThread();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadCaheUser();
    }

    @Override
    protected void onDestroy() {
        ((ILife) ChatWindowsManage.getInstence()).onDestroy(this);
        if (loader != null) {
            loader.cancelLoad();
        }
        super.onDestroy();
    }

    @Override
    public void showTipToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isFinish() {
        return isFinishing();
    }

    @Override
    public int getType() {
        return NULL_TYPE;
    }

    @Override
    public void loginSucceed() {
        log(tip1);
    }

    @Override
    public void finishing() {
        finish();
    }

    @Override
    @Deprecated
    public AbsListView getListView() {
        return null;
    }

    @Override
    public BaseChatBean getBean() {
        return null;
    }


    @Override
    public String getRoomId() {
        return null;
    }

    @Override
    public String getReceiver() {
        return null;
    }

    protected boolean isHadIMWindow() {
        for (IMWindow windows : ChatWindowsManage.getInstence().getAllIMWindows()) {

            if (this == windows) {
                return true;
            }

        }
        return false;
    }

    @Override
    public void loadCaheUser() {
        if (UserBean.isMemberIdNull()) {
            PManager.getCacheUser(getContext());
        }
    }

    public void log(String msg) {
        Log.i(TGA, msg);
    }


}

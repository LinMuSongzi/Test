package com.ifeimo.im.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;

import com.ifeimo.im.common.bean.chat.BaseChatBean;
import com.ifeimo.im.framwork.interface_im.IMWindow;

/**
 * Created by lpds on 2017/2/6.
 */
public abstract class BaseActivity extends AppCompatActivity {


    private IMWindow imWindow = new IMWindow() {
        @Override
        public void close() {

        }

        @Override
        public String getKey() {
            return null;
        }

        @Override
        public boolean isFinish() {
            return false;
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public void loginSucceed() {

        }

        @Override
        public void finishing() {

        }

        @Override
        public BaseChatBean getBean() {
            return null;
        }

        @Override
        public AbsListView getListView() {
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

        @Override
        public void showTipToast(String s) {

        }

        @Override
        public void loadCaheUser() {

        }

        @Override
        public Context getContext() {
            return BaseActivity.this;
        }

        @Override
        public void runOnUiThread(Runnable runnable) {
            BaseActivity.this.runOnUiThread(runnable);
        }
    };

    public IMWindow getImWindow() {
        return imWindow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

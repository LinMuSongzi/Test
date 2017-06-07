package com.ifeimo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ifeimo.im.IMSdk;
import com.ifeimo.test.R;

/**
 * Created by lpds on 2017/1/3.
 */
public class SelectActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void siClick(View v){
//        IMkit.getInstance().IMConversation(this,"12345","12345","");
    }

    public void twClick(View v){
        IMSdk.createMuccRoom(this,"10000","群聊");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

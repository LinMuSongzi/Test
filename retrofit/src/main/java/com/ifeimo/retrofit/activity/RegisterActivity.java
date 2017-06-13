package com.ifeimo.retrofit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.ifeimo.retrofit.R;
import com.ifeimo.retrofit.model.UserEntity;
import com.ifeimo.retrofit.retrofit.RequestManager;
import com.l.linframwork.framework.activity.BaseActivity;
import com.l.linframwork.framework.entity.JsonEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lpds on 2017/6/5.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_user)
    TextInputLayout id_user;
    @Bind(R.id.id_password)
    TextInputLayout id_password;
    @OnClick(R.id.id_register)
    public void register(){
//        if(TextUtils.isEmpty(id_user.getEditText().getText().toString().trim())
//               || id_user.getEditText().getText().toString().trim().length() < 6 ){
//            id_user.setError("用户名不少于6位");
//        }
//        if(TextUtils.isEmpty(id_password.getEditText().getText().toString().trim())
//                || id_password.getEditText().getText().toString().trim().length() < 6 ){
//            id_password.setError("密码不少于6位");
//        }


//        startActivity(new Intent(this,MainActivity.class));

        RequestManager.getInstances().register(getKey(),id_user.getEditText().getText().toString().trim(),
                id_password.getEditText().getText().toString().trim());
//        RequestManager.getInstances().baidu(getKey());

    }


    private TextWatcher inputCheckWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(id_user.getEditText().isFocused()){

            }else if(id_password.getEditText().isFocused()){

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JsonEntity jsonEntity) {
        Snackbar.make(id_password,jsonEntity.getJson(),3500).show();
        Log.i(TAG, "onEvent: "+jsonEntity.getJson());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitle("登录");

        id_user.getEditText().addTextChangedListener(inputCheckWatcher);
        id_password.getEditText().addTextChangedListener(inputCheckWatcher);
    }

    @Override
    public void beforeInit(Bundle bundle) {

    }
}

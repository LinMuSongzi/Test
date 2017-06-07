package com.ifeimo.test;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ifeimo.im.IMSdk;
import com.ifeimo.im.common.callback.LoginCallBack;
import com.ifeimo.test.activity.RegisterActivity;
import com.ifeimo.test.request.RequestUtil;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView readme;

    View login;
    View reg;
    EditText account;
    EditText password;
    String pciPath = "http://apps.ifeimo.com/Public/Uploads/Member/Avatar/56a0d1b3eef0e.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        rawtest();
        new Thread(){
            @Override
            public void run() {
                RequestUtil.test2();
            }
        }.start();
    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = findViewById(R.id.login);
        reg = findViewById(R.id.reg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void rawtest() {
        try {
            readme = (TextView) findViewById(R.id.readme);
            InputStream is = getResources().openRawResource(R.raw.readme);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            readme.setText(new String(buffer));

            readme.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestUtil.test1(MainActivity.this,readme);
                }
            },2000);

        }catch (Exception e){

        }
    }





    public void login(){
//        if(StringUtil.isNullTextView(account) || StringUtil.isNullTextView(password)){
//            Toast.makeText(this,"请输入正确用户名密码",Toast.LENGTH_SHORT).show();
//        }
        final String ac = account.getText().toString();
        final String pw = password.getText().toString();
        System.err.print("ac = "+ac+"   pw = "+pw);
        IMSdk.Login(this, ac, "自己", "", new LoginCallBack() {
            @Override
            public void callSuccess() {

            }

            @Override
            public void callFail() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMSdk.logout(this,false);
    }

    public void register(){
        startActivity(new Intent(this,RegisterActivity.class));
    }

}

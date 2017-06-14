package com.lin.haisen.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lin.haisen.R;
import com.lin.haisen.ui.dialog.MainUserDialog;
import com.lin.haisen.util.ActivityToggle;

import butterknife.OnClick;

/**
 * Created by lpds on 2017/6/10.
 */
public class AppStartActivity extends BaseActivity {

    private MainUserDialog mainUserDialog;

    @OnClick(R.id.id_center_layout)
    public void wellcome(){

        mainUserDialog.show();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_appstart;
    }

    @Override
    protected void initView() {
        mainUserDialog = new MainUserDialog(this).setOnClickLogin(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityToggle.startLoginActivity(v.getContext());
            }
        }).setOnClickRegister(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityToggle.startRegisterActivity(v.getContext());
            }
        });
    }

    @Override
    public void beforeInit(Bundle bundle) {

    }




}

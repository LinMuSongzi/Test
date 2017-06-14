package com.lin.haisen.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lin.haisen.R;
import com.lin.haisen.data.entity.BaseEntity;

public class UserInfoActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        initToolBar("You Name");
//        id_toolbar.setme
    }

    @Override
    public void beforeInit(Bundle bundle) {

    }


}

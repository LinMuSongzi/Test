package com.lin.haisen.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.haisen.R;
import com.lin.haisen.ui.activity.support.IToolBar;

/**
 * Created by lpds on 2017/6/10.
 */
public abstract class BaseActivity extends com.l.linframwork.framework.activity.BaseActivity implements IToolBar{
    protected Toolbar id_toolbar;
    private TextView id_title_center;

    public void initToolBar(String title){
        if(id_toolbar == null){
            id_toolbar = (Toolbar) findViewById(R.id.id_toolbar);
            setSupportActionBar(id_toolbar);
            id_title_center = (TextView) findViewById(R.id.id_title_center);
            if(id_title_center!=null) {
                id_title_center.setText(title);
                id_toolbar.setTitle("");
            }else{
                id_toolbar.setTitle(title);
                id_toolbar.setTitleTextColor(Color.WHITE);
            }
            id_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            id_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leave();
                }
            });
        }
    }

}

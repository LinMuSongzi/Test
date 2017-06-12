package com.lin.haisen.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lin.haisen.R;

/**
 * Created by lpds on 2017/6/10.
 */
public class MainUserDialog extends BaseAlertDialog {

    private View id_login_layout;
    private View id_register_layout;

    public MainUserDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView(View v) {
        id_login_layout = v.findViewById(R.id.id_login_layout);
        id_register_layout = v.findViewById(R.id.id_register_layout);
    }

    public MainUserDialog setOnClickLogin(View.OnClickListener onClickLogin) {
        id_login_layout.setOnClickListener(onClickLogin);
        return this;
    }

    public MainUserDialog setOnClickRegister(View.OnClickListener onClickRegister) {
        id_register_layout.setOnClickListener(onClickRegister);
        return this;
    }

    @Override
    public int getContextView() {
        return R.layout.dialog_user;
    }
}

package com.lin.haisen.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.l.linframwork.framework.fragment.IBaseFragment;
import com.lin.haisen.R;
import com.lin.haisen.ui.dialog.BaseAlertDialog;
import com.lin.haisen.ui.fragment.LoginFragment;
import com.lin.haisen.ui.fragment.RegisterFragment;
import com.lin.haisen.ui.fragment.support.IActionBar;

/**
 * Created by lpds on 2017/6/10.
 */
public class UserOperateActivity extends BaseActivity {

    public static final int REGISTER = 100;
    public static final int LOGIN = 101;

    public static final String ACTION = "acount_tag";

    private int tag;

    private IBaseFragment<IActionBar> iBaseFragment;



    @Override
    protected int getContentView() {
        return R.layout.activity_user_operate;
    }

    @Override
    protected void initView() {
        if(iBaseFragment != null) {
            initToolBar(iBaseFragment.getT().getTitle());
            getFragmentManager().beginTransaction().add(R.id.id_content, iBaseFragment.getFragment()).commit();
        }
    }

    @Override
    public void beforeInit(Bundle bundle) {
        tag = getIntent().getIntExtra(ACTION,-1);
        if(tag == REGISTER){
            iBaseFragment = new RegisterFragment();
        }else if(tag == LOGIN){
            iBaseFragment = new LoginFragment();
        }
    }
}

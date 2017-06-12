package com.lin.haisen.ui.fragment;

import com.lin.haisen.R;
import com.lin.haisen.ui.activity.BaseActivity;
import com.lin.haisen.ui.fragment.support.IActionBar;

/**
 * Created by lpds on 2017/6/12.
 */
public class LoginFragment extends BaseFragment<IActionBar> implements IActionBar{

    @Override
    protected void initView() {

    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_login;
    }

    @Override
    public IActionBar getT() {
        return this;
    }

    @Override
    public String getTitle() {
        return "Login";
    }
}

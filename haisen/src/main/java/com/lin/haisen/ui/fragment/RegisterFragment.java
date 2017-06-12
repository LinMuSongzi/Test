package com.lin.haisen.ui.fragment;

import com.lin.haisen.R;
import com.lin.haisen.ui.fragment.support.IActionBar;

/**
 * Created by lpds on 2017/6/10.
 */
public class RegisterFragment extends BaseFragment<IActionBar> implements IActionBar {
    @Override
    protected void initView() {



    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_register;
    }

    @Override
    public String getTitle() {
        return "Register";
    }

    @Override
    public IActionBar getT() {
        return this;
    }
}

package com.l.linframwork.framework.fragment;

import com.l.linframwork.framework.activity.IActivity;

/**
 * Created by lpds on 2017/6/12.
 */
public interface IBaseFragment<T> {

    BaseFragment getFragment();

    T getT();

    IActivity getIActivity();

}

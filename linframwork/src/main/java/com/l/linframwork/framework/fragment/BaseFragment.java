package com.l.linframwork.framework.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.l.linframwork.R;
import com.l.linframwork.framework.activity.IActivity;
import com.l.linframwork.framework.entity.EmptyEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/6/10.
 */
public abstract class BaseFragment<T> extends Fragment implements IBaseFragment<T>{

    protected View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            contentView = LayoutInflater.from(getActivity()).inflate(getContextView(),null);
            EventBus.getDefault().register(this);
            ButterKnife.bind(this.getActivity());
            initView();
        }
        return contentView;
    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected View  findViewById(int id){
       return contentView.findViewById(id);
    }

    protected abstract void initView();

    protected abstract int getContextView();


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmpty(EmptyEntity e){

    }


    @Override
    public IActivity getIActivity() {
        return (IActivity) getActivity();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}

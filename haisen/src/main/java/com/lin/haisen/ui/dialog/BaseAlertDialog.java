package com.lin.haisen.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/6/10.
 */
public abstract class BaseAlertDialog extends AlertDialog{
    private View contentView;
    private boolean isFirster = true;
    public BaseAlertDialog(@NonNull Context context) {
        super(context,0);
        createView();
        initView(contentView);
    }

    private final void createView(){
        contentView = LayoutInflater.from(getContext()).inflate(getContextView(),null);
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initView(View contentView);

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void show() {
        super.show();
        firsterAction();


    }

    protected final void firsterAction(){
        if(isFirster) {
            isFirster = false;
            setContentView(contentView);
            Window window = getWindow();
            WindowManager manager = window.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = display.getWidth() * 8/10;
            window.setAttributes(params);
            window.setBackgroundDrawable(new BitmapDrawable());
        }
    }

    public abstract int getContextView();
}

package com.lin.haisen.ui.popupwindow;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.l.linframwork.framework.activity.IActivity;
import com.l.linframwork.util.WindowUtil;

/**
 * Created by lpds on 2017/6/13.
 */
public class PopupWindowFactory{


    public static PopupWindow getProgressPopupWindow(final IActivity context){

        ProgressBar progressBar = new ProgressBar(context.getActivity());
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        PopupWindow popupWindow = new PopupWindow(progressBar,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true){
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                WindowUtil.addShadowBG(context,true);
                super.showAtLocation(parent, gravity, x, y);
            }

            @Override
            public void dismiss() {
                WindowUtil.removeShadowBG(context);
                super.dismiss();
            }
        };
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        popupWindow.setOutsideTouchable(true);
        if(Build.VERSION.SDK_INT > 21) {
            popupWindow.setElevation(10);
        }

        return popupWindow;
    }



}

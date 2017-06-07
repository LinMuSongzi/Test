package com.ifeimo.im.framwork;

import android.app.Activity;
import android.util.Log;

import com.ifeimo.im.ManagerList;
import com.ifeimo.im.common.callback.LoginCallBack;
import com.ifeimo.im.framwork.interface_im.IHierarchy;
import com.ifeimo.im.framwork.interface_im.ILife;
import com.ifeimo.im.framwork.interface_im.IMWindow;

import java.util.LinkedList;

/**
 * Created by lpds on 2017/1/10.
 */
public final class ChatWindowsManage implements IHierarchy,LoginCallBack,ILife{

    private static ChatWindowsManage chatWindowsManage;
    private LinkedList<IMWindow> windowses;
    public static final String TGA = "XMPP_ChatWindowsManage";

    static {
        chatWindowsManage = new ChatWindowsManage();
    }

    private ChatWindowsManage() {
        windowses = new LinkedList();
        ManagerList.getInstances().addManager(this);
    }

    public static IHierarchy getInstence() {
        return chatWindowsManage;
    }

    /**
     * 添加聊天窗口
     *
     * @param imWindow
     */
    public void onCreate(IMWindow imWindow) {
        if (imWindow != null) {
            windowses.add(imWindow);
            Log.i(TGA,"------ 进入 " + imWindow.getType() + "------ size = " + windowses.size());
        }
    }

    /**
     * 获取当前聊天的窗口
     *
     * @return
     */
    public IMWindow getFirstWindow() {
        if (windowses.size() == 0) {
            return null;
        }
        return windowses.getFirst();
    }

    /**
     * 移除所有imwindow
     */
    @Override
    public void releaseAll() {
        for(IMWindow imWindow : windowses){
            ((Activity)imWindow.getContext()).finish();
        }
    }

    /**
     * 销毁窗口
     *
     * @param imWindow
     */
    public void onDestroy(IMWindow imWindow) {
        if (imWindow != null) {
            imWindow.close();
            windowses.remove(imWindow);
            Log.i(TGA,"------ 退出 " + imWindow.getType() + "------ size = " + windowses.size());
        }
        checkLiveWindow();
    }

    /**
     * 检查
     */
    public void checkLiveWindow() {
    }

    /**
     * 刷新connection
     */
    @Deprecated
    public void refreshCon() {
        IMWindow imWindow = getFirstWindow();
        if (imWindow != null) {
            if (imWindow.getContext() != null) {
//                IMkit.getInstance().reconnect(imWindow.getContext(), this);
            }
        }
    }

    @Override
    public void callSuccess() {
        IMWindow imWindow = getFirstWindow();
        if (imWindow != null) {
            imWindow.loginSucceed();
        }
    }

    @Override
    public void callFail() {

    }

    public LinkedList<IMWindow> getAllIMWindows(){
        return windowses;
    }

    @Override
    public boolean isInitialized() {
        return windowses.size() > 0;
    }
}

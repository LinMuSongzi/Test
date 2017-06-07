package com.ifeimo.im;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ifeimo.im.activity.ChatActivity;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.callback.LoginCallBack;
import com.ifeimo.im.common.IntentManager;
import com.ifeimo.im.common.callback.LogoutCallBack;
import com.ifeimo.im.common.callback.OnLoginSYSJCallBack;
import com.ifeimo.im.common.util.PManager;
import com.ifeimo.im.framwork.connect.IMConnectManager;
import com.ifeimo.im.service.LoginService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpds on 2017/1/12.
 */
public class IMSdk {

    /**
     *注销登录
     * @param context
     * @param isClearCache 是否清空用户缓存
     */
    public static void logout(Context context, boolean isClearCache) {
        if (isClearCache) {
            PManager.clearCacheUser(context);
        }

        IMConnectManager.getInstance().addLogoutCallBack(new LogoutCallBack() {
            @Override
            public void logoutSuccess() {
                Log.i("XMPP","------ 注销成功 ------");
            }
        });

        IMConnectManager.getInstance().disconnect();
    }

    @Deprecated
    public static void Login(Context context, String myMemberID, String myNickName,
                             String myAvatarUrl, String first, String last,
                             String email, String city, String phone, String text) {
        UserBean.setLoginUser(myMemberID, myNickName, myAvatarUrl, first, last, phone, email, city, text);
        PManager.saveUser(context);
        context.startService(new Intent(context, LoginService.class));
    }

    public static void Login(Context context, String myMemberID, String myNickName, String myAvatarUrl,LoginCallBack loginCallBack) {
        UserBean.setLoginUser(myMemberID, myNickName, myAvatarUrl, null, null, null, null, null, null);
        PManager.saveUser(context);
        addLoginCallBack(loginCallBack);
        context.startService(new Intent(context, LoginService.class));
    }

    /**
     * 更新用户信息
     * @param context
     * @param myNickName
     * @param myAvatarUrl
     * @param onLoginSYSJCallBack
     */
    public static void upDateUser(Context context, String myNickName, String myAvatarUrl, OnLoginSYSJCallBack onLoginSYSJCallBack){
        UserBean.setAvatarUrl(myAvatarUrl);
        UserBean.setNickName(myNickName);
        PManager.saveUser(context);
        IMConnectManager.getInstance().addOnSYSJLoginListener(onLoginSYSJCallBack);
        Intent intent = new Intent(context, LoginService.class);
        intent.putExtra(LoginService.RELOGIN_KEY,LoginService.RELOGIN);
        context.startService(intent);
    }


    //群聊
    public static void createMuccRoom(Context context, String roomJID, String roomName) {
        if (IMConnectManager.getInstance() == null || !IMConnectManager.getInstance().isConnect()) {
            Toast.makeText(context, "网络不稳定，无法进入群聊！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("roomJID", roomJID);
        map.put("roomName", roomName);
        IntentManager.createIMWindows(context, map);
        Log.i("XMPP","------ 进入群聊 ------");
    }

    public static void createChat(Context context, String receiverID,
                                  String receiverNickName,String receiverAvatarUrl){

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("receiverID", receiverID);
        intent.putExtra("receiverNickName", receiverNickName);
        intent.putExtra("receiverAvatarUrl", receiverAvatarUrl);
        context.startActivity(intent);
    }


    public static void addLoginCallBack(LoginCallBack loginCallBack) {
        IMConnectManager.getInstance().addLoginListener(loginCallBack);
    }
    public static void removeLoginCallBack() {
        IMConnectManager.getInstance().addLoginListener(null);
    }

    public static void addLogoutCallBack(LogoutCallBack logoutCallBack){

        IMConnectManager.getInstance().addLogoutCallBack(logoutCallBack);

    }

    public static void removeLogoutCallBack(){
        IMConnectManager.getInstance().removeLogoutCallBack();
    }


}

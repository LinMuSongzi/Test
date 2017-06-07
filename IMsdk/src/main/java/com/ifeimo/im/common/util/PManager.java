package com.ifeimo.im.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ifeimo.im.common.bean.ConnectBean;
import com.ifeimo.im.common.bean.UserBean;

/**
 * Created by lpds on 2017/1/11.
 */
public final class PManager {

    public static final String NAME = "ifeimo";
    private final static String CONNECT_CONFIG = String.format("%s_connnect_config",NAME);
    private final static String USER = String.format("%s_user_config",NAME);

    public static void saveConnectConfig(Context context, ConnectBean connectBean) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONNECT_CONFIG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("HOST", connectBean.getHost())
                .putInt("PROT", connectBean.getPort())
                .putString("SERVICE_NAME", connectBean.getServiceName()).commit();
    }
    public static ConnectBean getConnectConfig(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(CONNECT_CONFIG, Context.MODE_PRIVATE);
        ConnectBean connectBean = new ConnectBean(
                sharedPreferences.getString("HOST", "juliet"),
                sharedPreferences.getInt("PROT", 5222),
                sharedPreferences.getString("SERVICE_NAME", "juliet"));
        Log.e("IM_Connect",connectBean.toString());
        return connectBean;
    }

    public static void saveUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MEMBER_ID", UserBean.getMemberID())
                .putString("AVATARURL", UserBean.getAvatarUrl())
                .putString("NICK_NAME", UserBean.getNickName()).commit();
    }

    public static void getCacheUser(Context context){
        if(context == null){
            return ;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        UserBean.setMemberID(sharedPreferences.getString("MEMBER_ID", UserBean.getMemberID()));
        UserBean.setAvatarUrl(sharedPreferences.getString("AVATARURL", UserBean.getAvatarUrl()));
        UserBean.setNickName(sharedPreferences.getString("NICK_NAME", UserBean.getNickName()));
    }

    public static void clearCacheUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    @Deprecated
    public static String getDefaultRoom(Context context){

        return context.getSharedPreferences(CONNECT_CONFIG, Context.MODE_PRIVATE).getString("room","10098");

    }

    @Deprecated
    public static void saveDefaultRoom(Context context,String roomid){

        context.getSharedPreferences(CONNECT_CONFIG, Context.MODE_PRIVATE).edit().putString("room",roomid).commit();

    }

}

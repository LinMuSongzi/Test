package com.ifeimo.im.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ifeimo.im.common.ILog;

import java.security.Provider;

/**
 * Created by lpds on 2017/1/11.
 */
public abstract class BaseProvider extends ContentProvider{

    public static int SEND_FINISH = 2001;
    public static int SEND_UNCONNECT = 2002;
    public static final int SEND_WAITING = 2003;
    public static final String DB_ID = "_id";
    public static final String DB_TB_MEMBER = "tb_member";
    public static final String DB_MEMBER_ID = "memberId";
    public static final String DB_MEMBER_NICKNAME = "member_nick_name";
    public static final String DB_MEMBER_AVATARURL= "avatarUrl";
    public static final String DB_REMARK = "remark";
    public static final String DB_MSG_ID = "mesgId";
    public static final String TGA = "XMPP_provide";

    public static final String DB_TB_USER = "tb_user";


    protected void log(String msg) {
        Log.i(TGA, msg);
    }


}

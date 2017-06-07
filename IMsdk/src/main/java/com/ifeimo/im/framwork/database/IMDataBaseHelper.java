package com.ifeimo.im.framwork.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ifeimo.im.provider.BaseProvider;
import com.ifeimo.im.provider.CacheMsgListProvide;
import com.ifeimo.im.provider.ChatProvider;
import com.ifeimo.im.provider.MuccProvider;

/**
 * Created by lpds on 2017/1/11.
 */
public class IMDataBaseHelper extends SQLiteOpenHelper {


    public IMDataBaseHelper(Context context) {
        super(context, "IM_chat.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 = String.format("CREATE TABLE %s (%s integer primary key autoincrement," +
                        "%s integer,%s varchat(10),%s text,%s text,%s varchat(10),%s text,%s varchat(10),%s text)",
                MuccProvider.DB_TB_NAME, MuccProvider.DB_ID, MuccProvider.DB_SEND_TYPE,MuccProvider.DB_MSG_ID,
                MuccProvider.DB_MEMBER_ID, MuccProvider.DB_CREATE_TIME,
                MuccProvider.DB_ROOM_ID, MuccProvider.DB_CONTENT, BaseProvider.DB_MEMBER_NICKNAME,
                BaseProvider.DB_MEMBER_AVATARURL);
        String sql2 = String.format("CREATE TABLE %s (%s integer primary key autoincrement," +
                        "%s integer,%s varchat(10),%s varchat(10),%s text,%s text,%s text)",
                ChatProvider.DB_TB_NAME, ChatProvider.DB_ID, ChatProvider.DB_SEND_TYPE, ChatProvider.DB_MEMBER_ID,
                ChatProvider.DB_RECEIVER_ID, ChatProvider.DB_CREATE_TIME, ChatProvider.DB_CONTENT, ChatProvider.DB_MSG_ID);

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3());
        db.execSQL(sql4());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String sql3() {

        return String.format("CREATE TABLE %s (%s integer primary key autoincrement," +
                        "%s varchat(10), %s varchat(10), %s varchar(10)," +
                        "%s varchat(10), %s text," +
                        "%s text, %s integer,%s varchat(10),%s integer,%s blob)",
                CacheMsgListProvide.TB_NAME, CacheMsgListProvide.DB_ID,CacheMsgListProvide.DB_MEMBER_ID,
                CacheMsgListProvide.DB_CACHEID, CacheMsgListProvide.DB_TITLE,
                CacheMsgListProvide.DB_LAST_CONTENT, CacheMsgListProvide.DB_LAST_CREATETIME,
                CacheMsgListProvide.DB_PICURL, CacheMsgListProvide.DB_TYPE,CacheMsgListProvide.DB_NAME,
                CacheMsgListProvide.DB_UNREAD_COUNT,CacheMsgListProvide.DB_IS_ME_SEND);


    }

    private String sql4(){

        String sql = String.format("CREATE TABLE %s " +
                "(%s integer primary key autoincrement,%s varchat(10),%s varchat(10),%s text)"
        ,BaseProvider.DB_TB_USER,BaseProvider.DB_ID,BaseProvider.DB_MEMBER_ID,
                BaseProvider.DB_MEMBER_NICKNAME,BaseProvider.DB_MEMBER_AVATARURL);

        return sql;
    }

}

package com.ifeimo.im.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ifeimo.im.common.bean.AccountBean;
import com.ifeimo.im.common.bean.CacheMainMsgItem;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.common.bean.MuccMsgBean;
import com.ifeimo.im.framwork.database.DataBaseThread;
import com.ifeimo.im.provider.BaseProvider;
import com.ifeimo.im.provider.CacheMsgListProvide;
import com.ifeimo.im.provider.ChatProvider;
import com.ifeimo.im.provider.MuccProvider;

import org.jivesoftware.smackx.muc.provider.MUCAdminProvider;

/**
 * Created by lpds on 2017/1/11.
 */
public class MsgService extends Service {

    public static ContentResolver contentResolver;
    public static Service service;

    private DataBaseThread dataBaseThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        service = this;
        contentResolver = getContentResolver();
        dataBaseThread = new DataBaseThread(contentResolver);
        dataBaseThread.clearChatMsg();
        dataBaseThread.clearMuccMsg();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contentResolver = null;
    }

    public static void insertMucc(MuccMsgBean muccMsgBean) {
        ContentValues values = new ContentValues();
        values.put(MuccProvider.DB_MEMBER_ID, muccMsgBean.getMemberId());
        values.put(MuccProvider.DB_MEMBER_NICKNAME, muccMsgBean.getMemberNickName());
        values.put(MuccProvider.DB_MEMBER_AVATARURL, muccMsgBean.getMemberAvatarUrl());
        values.put(MuccProvider.DB_CONTENT, muccMsgBean.getContent());
        values.put(MuccProvider.DB_SEND_TYPE, muccMsgBean.getSendType());
        values.put(MuccProvider.DB_CREATE_TIME, muccMsgBean.getCreateTime());
        values.put(MuccProvider.DB_ROOM_ID, muccMsgBean.getRooomID());
        values.put(MuccProvider.DB_MSG_ID, muccMsgBean.getMsgId());
        contentResolver.insert(MuccProvider.CONTENT_URI, values);
    }

    public static void upDataMucc(MsgBean msgBean) {
        ContentValues values = new ContentValues();
        values.put(MuccProvider.DB_CREATE_TIME, msgBean.getCreateTime());
        contentResolver.update(MuccProvider.CONTENT_URI, values,
                String.format(" %s = %s ", MuccProvider.DB_ID, msgBean.getId()), null);
    }

    public static void insertChat(MsgBean msgBean) {
        ContentValues values = new ContentValues();
        values.put(ChatProvider.DB_MEMBER_ID, msgBean.getMemberId());
        values.put(ChatProvider.DB_RECEIVER_ID, msgBean.getReceiverId());
        values.put(ChatProvider.DB_CONTENT, msgBean.getContent());
        values.put(ChatProvider.DB_SEND_TYPE, msgBean.getSendType());
        values.put(ChatProvider.DB_CREATE_TIME, msgBean.getCreateTime());
        contentResolver.insert(ChatProvider.CONTENT_URI, values);
        msgBean.setId(values.getAsInteger(BaseProvider.DB_ID));
    }

    public static void upDataChat(MsgBean msgBean) {
        ContentValues values = new ContentValues();
        values.put(ChatProvider.DB_MEMBER_ID, msgBean.getMemberId());
        values.put(ChatProvider.DB_RECEIVER_ID, msgBean.getReceiverId());
        values.put(ChatProvider.DB_CONTENT, msgBean.getContent());
        values.put(ChatProvider.DB_SEND_TYPE, msgBean.getSendType());
        values.put(ChatProvider.DB_CREATE_TIME, msgBean.getCreateTime());
        contentResolver.update(ChatProvider.CONTENT_URI, values,
                String.format(" %s = %s ", ChatProvider.DB_ID, msgBean.getId()), null);
    }

    public void deleteChat(MsgBean msgBean) {
        contentResolver.delete(ChatProvider.CONTENT_URI,
                String.format(" %s = '?' and %s = '?' and %s = '?' and %s = '?' ",
                        ChatProvider.DB_MEMBER_ID, ChatProvider.DB_CONTENT,
                        ChatProvider.DB_RECEIVER_ID, ChatProvider.DB_CREATE_TIME),
                new String[]{
                        msgBean.getMemberId(), msgBean.getContent(),
                        msgBean.getReceiverId(), msgBean.getCreateTime()
                });
    }

    public static void deleteMucc(MuccMsgBean msgBean) {
        contentResolver.delete(ChatProvider.CONTENT_URI,
                String.format(" %s = '?' and %s = '?' and %s = '?' and %s = '?' ",
                        MuccProvider.DB_MEMBER_ID, MuccProvider.DB_CONTENT,
                        MuccProvider.DB_ROOM_ID, MuccProvider.DB_CREATE_TIME),
                new String[]{
                        msgBean.getMemberId(), msgBean.getContent(),
                        msgBean.getRooomID(), msgBean.getCreateTime()
                });
    }

    public static void deleteMuccById(MuccMsgBean msgBean) {
        contentResolver.delete(MuccProvider.CONTENT_URI,
                String.format(" %s = ? ",
                        MuccProvider.DB_ID),
                new String[]{
                        msgBean.getId() + ""});
    }
}

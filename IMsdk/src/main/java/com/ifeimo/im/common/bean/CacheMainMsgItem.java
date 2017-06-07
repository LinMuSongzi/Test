package com.ifeimo.im.common.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.ifeimo.im.provider.CacheMsgListProvide;

import java.io.Serializable;

/**
 * Created by lpds on 2017/1/24.
 */
public class CacheMainMsgItem implements Serializable{


    private int id;
    private String title;
    /**
     * 群聊时，cacheid 为 roomid
     * 单聊时，cacheid 为 memberid + receiverid;
     * 广告时，cacheid 为 广告id
     * 系统推送时 cacheid 为系统推送id
     */
    private String cacheid;
    private String lastContent;
    private String lastCreateTime;
    private String picUrl;
    private int type;

    public static final int Default = 0x110;
    public static final int ROOM = 0x111; //群聊
    public static final int ROOM1 = 0x112; //群聊临时，
    public static final int Main = 0x113; //单聊 userid+@+receiverid
    public static final int Advertisement = 0x114; //广告
    public static final int SystemPut = 0x115; //系统推送

    public CacheMainMsgItem(String title, String cacheid, String lastContent, String lastCreateTime, String picUrl, int type) {
        this.title = title;
        this.cacheid = cacheid;
        this.lastContent = lastContent;
        this.lastCreateTime = lastCreateTime;
        this.picUrl = picUrl;
        this.type = type;
    }

    public CacheMainMsgItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    public String getLastCreateTime() {
        return lastCreateTime;
    }

    public void setLastCreateTime(String lastCreateTime) {
        this.lastCreateTime = lastCreateTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public static CacheMainMsgItem buildCacheMainMsgItemByCursor(Cursor cursor) {

        CacheMainMsgItem cacheMainMsgItem = new CacheMainMsgItem();
        cacheMainMsgItem.id = cursor.getInt(cursor.getColumnIndex(CacheMsgListProvide.DB_ID));
        cacheMainMsgItem.cacheid = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_CACHEID));
        cacheMainMsgItem.title = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_TITLE));
        cacheMainMsgItem.lastContent = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_LAST_CONTENT));
        cacheMainMsgItem.lastCreateTime = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_LAST_CREATETIME));
        cacheMainMsgItem.picUrl = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_CACHEID));
        cacheMainMsgItem.type = cursor.getInt(cursor.getColumnIndex(CacheMsgListProvide.DB_TYPE));

        return cacheMainMsgItem;
    }

    public static ContentValues buildContentValuesByClass(CacheMainMsgItem cacheMainMsgItem){

        ContentValues contentValues = new ContentValues();

        if(cacheMainMsgItem == null){
            return null;
        }
        contentValues.put(CacheMsgListProvide.DB_CACHEID,cacheMainMsgItem.getCacheid());
        contentValues.put(CacheMsgListProvide.DB_TITLE,cacheMainMsgItem.getTitle());
        contentValues.put(CacheMsgListProvide.DB_LAST_CONTENT,cacheMainMsgItem.getLastContent());
        contentValues.put(CacheMsgListProvide.DB_LAST_CREATETIME,cacheMainMsgItem.getLastCreateTime());
        contentValues.put(CacheMsgListProvide.DB_PICURL,cacheMainMsgItem.getPicUrl());
        contentValues.put(CacheMsgListProvide.DB_TYPE,cacheMainMsgItem.getType());

        return contentValues;
    }


    @Override
    public String toString() {
        return "CacheMainMsgItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cacheid='" + cacheid + '\'' +
                ", lastContent='" + lastContent + '\'' +
                ", lastCreateTime='" + lastCreateTime + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", type=" + type +
                '}';
    }
}

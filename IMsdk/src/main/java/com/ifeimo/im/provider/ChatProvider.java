package com.ifeimo.im.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ifeimo.im.common.bean.CacheMainMsgItem;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.framwork.database.IMDataBaseHelper;
import com.ifeimo.im.service.MsgService;

/**
 * Created by lpds on 2017/1/11.
 */
public class ChatProvider extends BaseProvider{

    public static final String DB_TB_NAME = "tb_chat";
    public static final String DB_ID = "_id";
    public static final String DB_RECEIVER_ID = "receiverId";
    public static final String DB_RECEIVER_NICK_NAME = "receiver_name";
    public static final String DB_RECEIVER_AVATARURL = "receiver_avatarUrl";
    public static final String DB_CONTENT = "content";
    public static final String DB_SEND_TYPE = "send_type";
    public static final String DB_CREATE_TIME = "create_time";

    public static final String PROVIDER_NAME = "com.ifeimo.im.db.chat";
    private IMDataBaseHelper dbHelper;
    private SQLiteDatabase dbWrite;
    private static UriMatcher matcher;
    private static final int CHAT = 1;
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/chat");
    //public static final Uri CONTEXT_BY_ID = Uri.parse("content://" + PROVIDER_NAME + "/deletebyid");

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME, "chat", CHAT);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new IMDataBaseHelper(this.getContext());
        dbWrite = dbHelper.getWritableDatabase();
        return dbWrite == null ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case CHAT:
//                SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
//                qBuilder.setTables(DB_TB_NAME);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String sql = "SELECT * FROM " +
                        " tb_chat  WHERE " +
                        " (receiverId = ? and memberId = ?) or (receiverId = ? and memberId = ?) " +
                        "ORDER BY "+DB_CREATE_TIME;
                Cursor ret = db.rawQuery(sql, new String[]{
                        selectionArgs[0], selectionArgs[1],
                        selectionArgs[1], selectionArgs[0],
                }, null);


                ret.setNotificationUri(getContext().getContentResolver(), uri);
                return ret;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case CHAT:
                return "vnd.android.cursor.dir/com.ifeimo.im";
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = -1;
        switch (matcher.match(uri)) {
            case CHAT:
                dbWrite.beginTransaction();
                try {

                    if(contentValues.getAsString(DB_MEMBER_ID) == null ||
                            contentValues.getAsString(DB_MEMBER_ID).equals("")){
                        rowID = -1;
                    }else {
                        rowID = dbWrite.insert(DB_TB_NAME, null, contentValues);

                    }
                    dbWrite.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dbWrite.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                if (rowID > 0) {
                    Uri wordUri = ContentUris.withAppendedId(uri, rowID);
                    log(" ------- 数据插入成功 ------ " + contentValues);
                    contentValues.put(DB_ID,rowID);
                    tellToCache(contentValues);
                    return wordUri;
                } else if (rowID == -10) {
                    log(" -------  重复数据不插入 -------" + contentValues);
                } else {
                    log(" -------  数据插入失败 -------" + contentValues);
                }
                break;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
        return null;
    }

    protected void tellToCache(ContentValues contentValues) {
        ContentValues c = new ContentValues();
        String receiverid = contentValues.getAsString(DB_MEMBER_ID);
        if(UserBean.getMemberID().equals(receiverid)){
            c.put(CacheMsgListProvide.DB_IS_ME_SEND,true);
            receiverid = contentValues.getAsString(DB_RECEIVER_ID);
        }else{
            c.put(CacheMsgListProvide.DB_IS_ME_SEND,false);
        }
        c.put(CacheMsgListProvide.DB_TYPE, CacheMainMsgItem.Main);
        c.put(CacheMsgListProvide.DB_LAST_CONTENT,contentValues.getAsString(DB_CONTENT));
        c.put(CacheMsgListProvide.DB_LAST_CREATETIME,contentValues.getAsString(DB_CREATE_TIME));
        c.put(CacheMsgListProvide.DB_MEMBER_ID,UserBean.getMemberID());
        c.put(CacheMsgListProvide.DB_CACHEID,receiverid);
        c.put(CacheMsgListProvide.DB_TITLE,receiverid);
        MsgService.contentResolver.update(CacheMsgListProvide.CONTENT_URI,c,null,null);


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int num = 0;
        switch (matcher.match(uri)) {
            case CHAT:
                dbWrite.beginTransaction();
                try {
                    num = dbWrite.delete(DB_TB_NAME, selection, selectionArgs);
                    dbWrite.setTransactionSuccessful();
                } catch (Exception e) {
//                    Log.e(TAG, e.toString());
                } finally {
                    dbWrite.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int num = 0;
        switch (matcher.match(uri)) {
            case CHAT:
                dbWrite.beginTransaction();
                try {
                    num = dbWrite.update(DB_TB_NAME, values, selection, selectionArgs);
                    dbWrite.setTransactionSuccessful();
                } catch (Exception e) {
//                    Log.e(TAG, e.toString());
                } finally {
                    dbWrite.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }




}

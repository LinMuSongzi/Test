package com.ifeimo.im.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ifeimo.im.common.bean.CacheMainMsgItem;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.framwork.database.IMDataBaseHelper;

/**
 * Created by lpds on 2017/1/24.
 */
public class CacheMsgListProvide extends BaseProvider {


    /**
     * 数据库结构信息
     */
    public static final String TB_NAME = "tb_cacheContach";//表格
    public static final String DB_CACHEID = "cacheid";
    public static final String DB_TITLE = "title";
    public static final String DB_LAST_CONTENT = "lastContent";
    public static final String DB_LAST_CREATETIME = "lastCreateTime";
    public static final String DB_PICURL = "picUrl";
    public static final String DB_TYPE = "type";
    public static final String DB_NAME = "name";//对方姓名，或者房间名字
    public static final String DB_UNREAD_COUNT = "unread";
    public static final String DB_IS_ME_SEND = "ismesend";

    private IMDataBaseHelper dbHelper;
    private SQLiteDatabase dbWrite;
    public static final String PROVIDER_NAME = "com.ifeimo.im.db.cachemsglist";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/Cache");
    public static final Uri ClearUnread_URI = Uri.parse("content://" + PROVIDER_NAME + "/ClearUnread");
    private static UriMatcher matcher;
    private static final int Cache = 0x111;
    private static final int ClearUnread = 0x110;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME, "Cache", Cache);
        matcher.addURI(PROVIDER_NAME, "ClearUnread", ClearUnread);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new IMDataBaseHelper(this.getContext());
        dbWrite = dbHelper.getWritableDatabase();
        return dbWrite == null ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        switch (matcher.match(uri)) {
            case Cache:
                try {
                    Cursor cursor = dbWrite.query(TB_NAME, null,
                            s, strings1, s1, null, null);
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case Cache:
                dbWrite.delete(TB_NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
        }
        return 0;
    }

    @Override
    @Deprecated
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case Cache:
                try {
                    dbWrite.beginTransaction();
                    if (contentValues == null) {
                        return -2;
                    }
                    Cursor cursor = dbWrite.query(TB_NAME, null,
                            String.format("%s = '%s' AND %s = %s",
                                    DB_CACHEID, contentValues.getAsString(DB_CACHEID),
                                    DB_TYPE, contentValues.getAsInteger(DB_TYPE)), null, null, null, null);
                    final int count = cursor.getCount();
                    int re;
                    int unreadCount = 1;
                    if (count == 1) {
                        cursor.moveToFirst();
                        if (!contentValues.getAsBoolean(DB_IS_ME_SEND)) {
                            unreadCount = cursor.getInt(cursor.getColumnIndex(DB_UNREAD_COUNT));
                            unreadCount++;
                            contentValues.put(DB_UNREAD_COUNT, unreadCount);
                        }
                        final int id = cursor.getInt(cursor.getColumnIndex(DB_ID));

                        re = dbWrite.update(TB_NAME, contentValues, DB_ID + " = " + id, null);
                        if (re > 0) {
                            Log.i(TGA, "------- 修改缓存聊天 ------" + contentValues);
                            dbWrite.setTransactionSuccessful();
                        }
                    } else {
                        if(!contentValues.getAsBoolean(DB_IS_ME_SEND)) {
                            contentValues.put(DB_UNREAD_COUNT, unreadCount);
                        }

                        re = (int) dbWrite.insert(TB_NAME, null, contentValues);
                        if (re > 0) {
                            Log.i(TGA, "------- 新增缓存聊天 ------" + contentValues);
                            dbWrite.setTransactionSuccessful();
                        }
                    }
                    cursor.close();
                    getContext().getContentResolver().notifyChange(uri, null);
                    return re;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dbWrite.endTransaction();
                }
                break;
            case ClearUnread:
                dbWrite.execSQL(String.format("UPDATE %s SET %s = %s WHERE %s = %s",
                        TB_NAME, DB_UNREAD_COUNT, 0,
                        DB_ID, contentValues.getAsInteger(DB_ID)));
                getContext().getContentResolver().notifyChange(CONTENT_URI, null);
                return 1;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
        return -1;
    }
}

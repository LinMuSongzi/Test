package com.ifeimo.im.provider;

import android.content.ContentUris;
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
import com.ifeimo.im.service.MsgService;

/**
 * Created by lpds on 2017/1/11.
 */
public class MuccProvider extends BaseProvider {

    public static final String DB_TB_NAME = "tb_mucc";

    public static final String DB_MEMBER_ID = "memberId";
    public static final String DB_CONTENT = "content";
    public static final String DB_SEND_TYPE = "sendType";
    public static final String DB_CREATE_TIME = "createTime";
    public static final String DB_ROOM_ID = "roomid";
    public static final String PROVIDER_NAME = "com.ifeimo.im.db.mucc";
    private static UriMatcher matcher;
    private static final int MUCC = 2;
    private IMDataBaseHelper dataBaseHelper;
    private SQLiteDatabase dbWrite;
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/mucc");

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME, "mucc", MUCC);
    }

    @Override
    public boolean onCreate() {
        dataBaseHelper = new IMDataBaseHelper(getContext());
        dbWrite = dataBaseHelper.getWritableDatabase();
        return dbWrite == null ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case MUCC:
                String sql = "SELECT * FROM " +
                        " tb_mucc  WHERE roomid = ? ORDER BY createTime";
                Cursor cursor = dbWrite.rawQuery(sql, selectionArgs);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case MUCC:
                return "vnd.android.cursor.dir/com.ifeimo.im";
            default:
                throw new IllegalArgumentException("未知uri:" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = -1;
        switch (matcher.match(uri)) {
            case MUCC:
                dbWrite.beginTransaction();
                try {
//                    ChatProvider.memberIDTest(dbWrite, contentValues);
                    rowID = muccInsertTest(dbWrite, contentValues);
                    dbWrite.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.e("222", e.toString());
                } finally {
                    dbWrite.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                if (rowID > 0) {
                    Uri wordUri = ContentUris.withAppendedId(uri, rowID);
                    log(" -------  数据插入成功 -------" + contentValues);
                    tellToCache(contentValues);
                    return wordUri;
                } else if (rowID == -10) {
                    log(" -------  重复数据不插入 -------" + contentValues);
                } else if (rowID == -11) {
                    log(" -------  重复数据,数据更新成功 -------" + contentValues);
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
        if(UserBean.getMemberID().equals(contentValues.getAsString(DB_MEMBER_ID))){
            c.put(CacheMsgListProvide.DB_IS_ME_SEND,true);
        }else{
            c.put(CacheMsgListProvide.DB_IS_ME_SEND,false);
        }
        c.put(CacheMsgListProvide.DB_TYPE,CacheMainMsgItem.ROOM);
        c.put(CacheMsgListProvide.DB_PICURL,contentValues.getAsString(DB_MEMBER_AVATARURL));
        c.put(CacheMsgListProvide.DB_LAST_CONTENT,contentValues.getAsString(DB_CONTENT));
        c.put(CacheMsgListProvide.DB_LAST_CREATETIME,contentValues.getAsString(DB_CREATE_TIME));
        c.put(CacheMsgListProvide.DB_MEMBER_ID, UserBean.getMemberID());
        c.put(CacheMsgListProvide.DB_CACHEID,contentValues.getAsString(DB_ROOM_ID));
        c.put(CacheMsgListProvide.DB_TITLE,contentValues.getAsString(DB_ROOM_ID));
        MsgService.contentResolver.update(CacheMsgListProvide.CONTENT_URI,c,null,null);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        switch (matcher.match(uri)) {
            case MUCC:
                dbWrite.beginTransaction();
                try {
                    if (dbWrite.delete(DB_TB_NAME, s, strings) > 0) {
                        log("删除旧数据！");
                    }
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
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int num = 0;
        switch (matcher.match(uri)) {
            case MUCC:
                dbWrite.beginTransaction();
                try {
                    num = dbWrite.update(DB_TB_NAME, values, selection, selectionArgs);
                    if (num < 1) {
                        log(" ------- 重发失败！！ -------");
                    }
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
//
//
//    private long muccInsertTest(SQLiteDatabase sqLiteDatabase, ContentValues contentValues){
//
//
//
//
//    }

    private long muccInsertTest(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {
        String createtime = contentValues.getAsString(DB_CREATE_TIME);
        String roomid = contentValues.getAsString(DB_ROOM_ID);
        String memberid = contentValues.getAsString(DB_MEMBER_ID);
        if (memberid == null || memberid.equals("")) {
            return -1;
        }

        if (createtime == null || createtime.equals("")) {
            return dbWrite.insert(DB_TB_NAME, null, contentValues);
        } else {
            Cursor cursor = sqLiteDatabase.rawQuery(String.format(
                    "SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ?",
                    DB_TB_NAME, DB_CREATE_TIME, DB_ROOM_ID, DB_MEMBER_ID),
                    new String[]{createtime, roomid, memberid});
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                String avatarUrl = contentValues.getAsString(DB_MEMBER_AVATARURL);
                String nickName = contentValues.getAsString(DB_MEMBER_NICKNAME);
                String oldavatarUrl = cursor.getString(cursor.getColumnIndex(DB_MEMBER_AVATARURL));
                String oldnickName = cursor.getString(cursor.getColumnIndex(DB_MEMBER_NICKNAME));


                if (!avatarUrl.equals(oldavatarUrl) || !nickName.equals(oldnickName)) {

                    ContentValues c = new ContentValues();
                    c.put(DB_MEMBER_AVATARURL, avatarUrl);
                    c.put(DB_MEMBER_NICKNAME, nickName);
                    int id = cursor.getInt(cursor.getColumnIndex(DB_ID));
                    if (sqLiteDatabase.update(DB_TB_NAME, c, String.format(" %s = %s", DB_ID, id), null) > 0) {
                        cursor.close();
                        return -11;
                    }

                }
                return -10;
            } else {
                return dbWrite.insert(DB_TB_NAME, null, contentValues);
            }
        }
    }
}

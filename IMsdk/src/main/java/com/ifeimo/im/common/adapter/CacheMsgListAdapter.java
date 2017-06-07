package com.ifeimo.im.common.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifeimo.im.IMSdk;
import com.ifeimo.im.R;
import com.ifeimo.im.common.adapter.base.ViewHolder;
import com.ifeimo.im.common.bean.CacheMainMsgItem;
import com.ifeimo.im.provider.CacheMsgListProvide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lpds on 2017/1/24.
 */
public class CacheMsgListAdapter extends CursorAdapter {

    private Context context;

    public CacheMsgListAdapter(Context context) {
        super(context, null, false);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contract, null, false);
        ViewHolder holder = new ViewHolder(context, view, null, -1);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String title = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_TITLE));
        String lastcontent = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_LAST_CONTENT));
        String lasttime = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_LAST_CREATETIME));
        final String picurl = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_PICURL));
        final String name = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_NAME));
        final int type = cursor.getInt(cursor.getColumnIndex(CacheMsgListProvide.DB_TYPE));
        final String cacheid = cursor.getString(cursor.getColumnIndex(CacheMsgListProvide.DB_CACHEID));
        int unreadcount = cursor.getInt(cursor.getColumnIndex(CacheMsgListProvide.DB_UNREAD_COUNT));
        final int id = cursor.getInt(cursor.getColumnIndex(CacheMsgListProvide.DB_ID));
        holder.setText(R.id.muc_left_username, title);
        holder.setText(R.id.muc_left_msg, lastcontent);
        holder.setText(R.id.id_time_tv, getConvertTime(lasttime));
        TextView unreadTv = holder.getView(R.id.id_unreandcount_tv);
        if (unreadcount <= 0) {
            unreadTv.setVisibility(View.GONE);
        } else {
            unreadTv.setVisibility(View.VISIBLE);
            holder.setText(R.id.id_unreandcount_tv, unreadcount + "");
        }
        Glide.with(context)
                .load(picurl)
                .dontAnimate()
                .placeholder(R.drawable.logo_round)
                .into((ImageView) holder.getView(R.id.muc_left_face));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    clearPoint(id);
//                    switch (type) {
//                        case CacheMainMsgItem.ROOM:
//                            IMSdk.createMuccRoom(context, cacheid, name);
//                            break;
//                        case CacheMainMsgItem.Main:
//                            IMSdk.createChat(context, cacheid.split("@")[0], name, picurl);
//                            break;
//                        case CacheMainMsgItem.Advertisement:
//                            break;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Deprecated
    private void clearPoint(final int id) {

        new Thread() {
            @Override
            public void run() {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CacheMsgListProvide.DB_ID, id);
                context.getContentResolver().update(CacheMsgListProvide.ClearUnread_URI, contentValues, null, null);
            }
        }.start();


    }


    public String getConvertTime(String lasttime) {
        if (lasttime == null || lasttime.equals("")) {
            return "";
        }
        long lTime = Long.parseLong(lasttime);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        if (calendar.getTimeInMillis() > lTime) {
            if (lTime > calendar.getTimeInMillis() - 1000 * 60 * 60 * 24 &&
                    lTime > calendar.getTimeInMillis() - 1000 * 60 * 60 * 24 * 2) {
                sdf = new SimpleDateFormat("昨天 HH:mm");
            } else {
                sdf = new SimpleDateFormat("yyyy/MM/dd");
            }
        } else {
            sdf = new SimpleDateFormat("HH:mm");
        }
//        sdf.setNumberFormat(NU);
        return sdf.format(new Date(lTime));
    }
}

package com.ifeimo.im.common.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifeimo.im.R;
import com.ifeimo.im.common.popupwindow.AddFriendPopupWindow;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.view.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpds on 2017/2/8.
 */
public abstract class BaseChatCursorAdapter extends CursorAdapter {
    protected IMWindow imWindow;
    protected LayoutInflater layoutInflater;
    protected AddFriendPopupWindow addFriendPopupWindow;

    Map<String, String> time = new HashMap();
    String datelast = "0";

    public BaseChatCursorAdapter(IMWindow context) {
        super(context.getContext(), null, false);
        this.imWindow = context;
        layoutInflater = LayoutInflater.from((Context) imWindow);
        addFriendPopupWindow = new AddFriendPopupWindow((Context) imWindow);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ChatViewHolder holder = new ChatViewHolder();
        View convertView = layoutInflater.inflate(R.layout.mucchat_item, null);
        holder.leftLayout = convertView.findViewById(R.id.muc_left_layout);
        holder.rightLayout = convertView.findViewById(R.id.muc_right_layout);
        holder.leftFace = (RoundedImageView) convertView.findViewById(R.id.muc_left_face);
        holder.rightFace = (RoundedImageView) convertView.findViewById(R.id.muc_right_face);
        holder.leftName = (TextView) convertView.findViewById(R.id.muc_left_username);
        holder.leftMsg = (TextView) convertView.findViewById(R.id.muc_left_msg);
        holder.rightMsg = (TextView) convertView.findViewById(R.id.muc_right_msg);
        holder.reConnectIv = (ImageView) convertView.findViewById(R.id.id_reSendIV);
        holder.id_process = convertView.findViewById(R.id.id_process);
        holder.timeTv = (TextView) convertView.findViewById(R.id.id_msg_tip_time);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        cheackTime(newCursor);
        return super.swapCursor(newCursor);
    }

    protected void cheackTime(Cursor newCursor) {
        if (newCursor != null && newCursor.getCount() > 0) {
            newCursor.moveToFirst();
            time.clear();
            datelast = "0";
            do {
                doWhere(newCursor);
            } while (newCursor.moveToNext());
        }
    }

    protected abstract void doWhere(Cursor lineCursor);

}

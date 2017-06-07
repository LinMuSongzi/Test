package com.ifeimo.im.common.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ifeimo.im.R;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.util.DateFormatUtil;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.framwork.message.MessageManager;
import com.ifeimo.im.provider.BaseProvider;
import com.ifeimo.im.provider.ChatProvider;

/**
 * Created by lpds on 2017/1/11.
 */
public class ChatAdapter extends BaseChatCursorAdapter {
    private String receiverNickName = "手游视界用户";
    private String receiverAvatarUrl;

    public ChatAdapter(IMWindow context) {
        super(context);
    }

    @Override
    public void bindView(View convertView, final Context context, Cursor cursor) {

        final MsgBean msgBean = MsgBean.createByCursor(cursor);
        ChatViewHolder holder = (ChatViewHolder) convertView.getTag();
        if (!UserBean.getMemberID().equals(msgBean.getMemberId())) {
            /// 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msgBean.getContent());
            holder.leftName.setText(receiverNickName);
            String url = receiverAvatarUrl;
            holder.leftFace.setTag(R.id.image_url, url);
            Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.actor)
                    .into(holder.leftFace);
            convertView.setOnClickListener(null);
        } else {
            // 如果是收到发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(Html.fromHtml(msgBean.getContent()));
            String url = UserBean.getAvatarUrl();
            holder.leftFace.setTag(R.id.image_url, url);
            Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.actor)
                    .into(holder.rightFace);
            holder.id_process.setVisibility(View.GONE);
            holder.reConnectIv.setVisibility(View.GONE);
            if (msgBean.getSendType() == BaseProvider.SEND_UNCONNECT) {
                holder.reConnectIv.setVisibility(View.VISIBLE);
                holder.reConnectIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageManager.getInstance().reSendChatMsg(imWindow, msgBean);
                    }
                });
            } else if (msgBean.getSendType() == BaseProvider.SEND_WAITING) {
                holder.id_process.setVisibility(View.VISIBLE);
            }
        }

        holder.timeTv.setVisibility(View.INVISIBLE);
        String formatTime = time.get(msgBean.getCreateTime());
        if (formatTime != null && !formatTime.equals("")) {
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(formatTime);
        }

    }


    public String getReceiverNickName() {
        return receiverNickName;
    }

    public void setReceiverNickName(String receiverNickName) {
        if (receiverNickName == null) {
            return;
        }
        this.receiverNickName = receiverNickName;
    }

    public String getReceiverAvatarUrl() {
        return receiverAvatarUrl;
    }

    public void setReceiverAvatarUrl(String receiverAvatarUrl) {
        this.receiverAvatarUrl = receiverAvatarUrl;
    }


    @Override
    protected void doWhere(Cursor lineCursor) {
        String thisDate = lineCursor.getString(lineCursor.getColumnIndex(ChatProvider.DB_CREATE_TIME));
        String content = lineCursor.getString(lineCursor.getColumnIndex(ChatProvider.DB_CONTENT));
        String formatdate = DateFormatUtil.getBorderDate(thisDate, datelast);

//        Log.e("55555", "(对比的时间 -- 格式化前 = " + datelast + " ,格式化后 = " + DateFormatUtil.getstyleByDateStr(datelast, "yyyy年MM月dd日 HH:mm") +
//                " ),content = " + content + " ,  显示时间 = " +
//                formatdate + "  , (自己的时间 格式化前 = " + thisDate + " ,格式化后 = " + DateFormatUtil.getstyleByDateStr(thisDate, "yyyy年MM月dd日 HH:mm"));
        if (formatdate != null) {
            time.put(thisDate, formatdate);
            datelast = thisDate;
        }
    }
}

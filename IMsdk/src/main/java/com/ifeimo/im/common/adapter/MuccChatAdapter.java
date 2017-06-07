package com.ifeimo.im.common.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ifeimo.im.IMSdk;
import com.ifeimo.im.R;
import com.ifeimo.im.common.bean.MuccMsgBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.callback.OnSendCallBack;
import com.ifeimo.im.common.popupwindow.AddFriendPopupWindow;
import com.ifeimo.im.common.util.DateFormatUtil;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.framwork.message.MessageManager;
import com.ifeimo.im.provider.BaseProvider;
import com.ifeimo.im.provider.MuccProvider;

/**
 * Created by lpds on 2017/1/11.
 */
public class MuccChatAdapter extends BaseChatCursorAdapter implements OnSendCallBack {

    private AddFriendPopupWindow addFriendPopupWindow;

    public MuccChatAdapter(IMWindow context) {
        super(context);
    }

    @Override
    public void bindView(View convertView, final Context context, Cursor cursor) {

        final MuccMsgBean muccMsgBean = MuccMsgBean.createLineByCursor(cursor);
        final ChatViewHolder holder = (ChatViewHolder) convertView.getTag();
        holder.memberID = muccMsgBean.getMemberId();
        if (!UserBean.getMemberID().equals(muccMsgBean.getMemberId())) {
            /// 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(muccMsgBean.getContent());
            holder.leftName.setText(muccMsgBean.getMemberNickName());
            String url = muccMsgBean.getMemberAvatarUrl();
            holder.leftFace.setTag(R.id.image_url, url);
            Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.actor)
                    .into(holder.leftFace);
            holder.leftFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
//                    intent.putExtra("receiverID", muccMsgBean.getMemberId());
//                    intent.putExtra("receiverNickName", muccMsgBean.getMemberNickName());
//                    intent.putExtra("receiverAvatarUrl", muccMsgBean.getMemberAvatarUrl());
//                    view.getContext().startActivity(intent);
                    holder.leftFace.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IMSdk.createChat(imWindow.getContext(), muccMsgBean.getMemberId(),
                                    muccMsgBean.getMemberNickName(), muccMsgBean.getMemberAvatarUrl());
                        }
                    }, 300);
                }
            });
            holder.leftFace.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    addFriendPopupWindow.init().
                            showAsDropDown(view, view.getWidth() + 10, -view.getHeight() / 2 - addFriendPopupWindow.getHeight() / 2);
                    return true;
                }
            });
        } else {
            // 如果是收到发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            if (muccMsgBean.getSendType() == BaseProvider.SEND_UNCONNECT) {
                holder.rightMsg.setText(Html.fromHtml(muccMsgBean.getContent()));
            } else if (muccMsgBean.getSendType() == BaseProvider.SEND_FINISH) {
                holder.rightMsg.setText(muccMsgBean.getContent());
            }
            String url = muccMsgBean.getMemberAvatarUrl();
            holder.leftFace.setTag(R.id.image_url, url);
            Glide.with(context)
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.actor)
                    .into(holder.rightFace);
            holder.leftFace.setOnClickListener(null);
            holder.leftFace.setOnLongClickListener(null);
            holder.id_process.setVisibility(View.GONE);
            holder.reConnectIv.setVisibility(View.GONE);
            if (muccMsgBean.getSendType() == BaseProvider.SEND_UNCONNECT) {
                holder.reConnectIv.setVisibility(View.VISIBLE);
                holder.reConnectIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageManager.getInstance().reSendMuccMsg(imWindow, muccMsgBean, MuccChatAdapter.this);
                    }
                });
            } else if (muccMsgBean.getSendType() == BaseProvider.SEND_WAITING) {
                holder.id_process.setVisibility(View.VISIBLE);
            }
        }

        holder.timeTv.setVisibility(View.INVISIBLE);
        String formatTime = time.get(muccMsgBean.getCreateTime());
        if (formatTime != null && !formatTime.equals("")) {
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(formatTime);
        }


    }

    @Override
    public void callSuccess() {

    }

    @Override
    public void callFail() {

    }

    @Override
    protected void doWhere(Cursor lineCursor) {
        String thisDate = lineCursor.getString(lineCursor.getColumnIndex(MuccProvider.DB_CREATE_TIME));
        String content = lineCursor.getString(lineCursor.getColumnIndex(MuccProvider.DB_CONTENT));
        String formatdate = DateFormatUtil.getBorderDate(thisDate, datelast);

        Log.e("55555", "(对比的时间 -- 格式化前 = "+datelast+" ,格式化后 = " + DateFormatUtil.getstyleByDateStr(datelast, "yyyy年MM月dd日 HH:mm") +
                " ),content = " + content + " ,  显示时间 = " +
                formatdate + "  , (自己的时间 格式化前 = "+thisDate+" ,格式化后 = "  + DateFormatUtil.getstyleByDateStr(thisDate, "yyyy年MM月dd日 HH:mm"));
        if (formatdate != null) {
            time.put(thisDate, formatdate);
            datelast = thisDate;
        }
    }
}

package com.ifeimo.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ifeimo.im.R;
import com.ifeimo.im.common.adapter.MuccChatAdapter;
import com.ifeimo.im.common.bean.chat.BaseChatBean;
import com.ifeimo.im.common.bean.chat.MuccBean;
import com.ifeimo.im.common.bean.MuccMsgBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.util.PManager;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.framwork.connect.IMConnectManager;
import com.ifeimo.im.framwork.message.MessageManager;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smackx.muc.MultiUserChatManager;


/**
 * Created by admin on 2016/12/19.
 */

public class MucChatActivity extends BaseCompatActivity implements
        View.OnClickListener {
    private String roomJID;
    private String roomName;
    private ListView mucList;
    private EditText mucEdit;
    private TextView mucTitle;
    private MuccChatAdapter muccChatAdapter;

    private MuccBean muccBean;
    private MuccMsgBean msgBean;

    private String sendMsg;

    private TextView id_top_right_tv;
    private ImageView id_top_right_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mucchat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (savedInstanceState != null) {
            PManager.getCacheUser(getContext());
            roomJID = savedInstanceState.getString("roomJID");
            roomName = savedInstanceState.getString("roomName");
        } else {
            roomJID = getIntent().getStringExtra("roomJID");
            roomName = getIntent().getStringExtra("roomName");
        }

        id_top_right_tv = (TextView) findViewById(R.id.id_top_right_tv);
        id_top_right_tv.setVisibility(View.VISIBLE);
        id_top_right_tv.setOnClickListener(this);
        id_top_right_tv.setText("修改参数");

        mucEdit = (EditText) findViewById(R.id.muc_et_chat_message);
        mucList = (ListView) findViewById(R.id.muc_chat_content);
        mucTitle = (TextView) findViewById(R.id.conversation_title);
        muccChatAdapter = new MuccChatAdapter(this);
        mucList.setAdapter(muccChatAdapter);

        mucTitle.setText(roomName);
        mucList.setVisibility(View.INVISIBLE);

        new Thread() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MucChatActivity.super.loader = MessageManager.getInstance().loadMuccData(MucChatActivity.this);
                    }
                });
                MucChatActivity.this.join();
            }
        }.start();
        mucTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mucList.setVisibility(View.VISIBLE);
            }
        }, 500);
        setSoft();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("roomJID", roomJID);
        outState.putString("roomName", roomName);
        super.onSaveInstanceState(outState);
        log("------- MUCChatActivity.Class 群聊被回收 数据已缓存 --------- ");
    }

    public void doBack(View v) {
        finish();
    }

    public void sendOnclick(View v) {
        new Thread() {
            @Override
            public void run() {
                sendMsg = mucEdit.getText().toString().trim();
                if (sendMsg.equals("")) {
                    return;
                }
                if (msgBean == null) {
                    msgBean = new MuccMsgBean();
                    msgBean.setRooomID(roomJID);
                    msgBean.setMemberId(UserBean.getMemberID());
                    msgBean.setMemberAvatarUrl(UserBean.getAvatarUrl());
                    msgBean.setMemberNickName(UserBean.getNickName());
                }
                msgBean.setContent(sendMsg);
                MessageManager.getInstance().sendMuccMsg(MucChatActivity.this, muccBean, msgBean);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mucEdit.setText("");
                    }
                });
            }
        }.start();
    }

    @Override
    public void close() {
        MessageManager.getInstance().leaveMuccRoom(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (muccBean != null) {
            if (muccBean.getMultiUserChat() != null) {
                if (!muccBean.getMultiUserChat().isJoined()) {
                    try {
                        muccBean.getMultiUserChat().join(UserBean.getMemberID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public BaseChatBean getBean() {
        return muccBean;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public void loginSucceed() {
        join();
    }

    public void join() {
        try {
            if (muccBean != null) {
                if (muccBean.getMultiUserChat() != null) {
                    if (muccBean.getMultiUserChat().isJoined()) {
                        muccBean.getMultiUserChat().leave();
                        log(" ------ 重新进入群聊 ------- ");
                    } else {
                        log(" ------ 进入群聊 ------- ");
                    }
                    muccBean.getMultiUserChat().join(UserBean.getMemberID());
                }
            } else {
                muccBean = new MuccBean(UserBean.getMemberID(), roomJID,
                        null,
                        MultiUserChatManager.getInstanceFor(IMConnectManager.getInstance().getConnection()).getMultiUserChat(roomJID + "@conference.op.17sysj.com"));
                muccBean.getMultiUserChat().addMessageListener(MessageManager.getInstance());
                join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getType() {
        return IMWindow.MUCCHAT_TYPE;
    }

    @Override
    public AbsListView getListView() {
        return mucList;
    }

    @Override
    public String getRoomId() {
        return roomJID;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }



}

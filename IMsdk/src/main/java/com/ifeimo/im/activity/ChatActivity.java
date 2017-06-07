package com.ifeimo.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ifeimo.im.R;
import com.ifeimo.im.common.Version;
import com.ifeimo.im.common.adapter.ChatAdapter;
import com.ifeimo.im.common.bean.chat.BaseChatBean;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.util.PManager;
import com.ifeimo.im.framwork.message.MessageManager;

/**
 * Created by lpds on 2017/1/13.
 */
public class ChatActivity extends BaseCompatActivity implements OnClickListener {

    private ListView msgListView;
    private EditText mEdit;
    private TextView title;
    private TextView addFriend;
    private ChatAdapter adapter;

    private String receiverID;
    private String receiverNickName;
    private String receiverAvatarUrl;
    //
//    @Deprecated
//    private ChatBean chatBean;
    private MsgBean msgBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (savedInstanceState != null) {
            PManager.getCacheUser(getContext());
            receiverID = savedInstanceState.getString("receiverAvatarUrl", receiverID);
            receiverNickName = savedInstanceState.getString("receiverAvatarUrl", receiverNickName);
            receiverAvatarUrl = savedInstanceState.getString("receiverAvatarUrl", receiverAvatarUrl);
        } else {
            receiverID = getIntent().getStringExtra("receiverID");
            receiverNickName = getIntent().getStringExtra("receiverNickName");
            receiverAvatarUrl = getIntent().getStringExtra("receiverAvatarUrl");
        }

        msgListView = (ListView) findViewById(R.id.lchat_content_1);
        title = (TextView) findViewById(R.id.conversation_title);
        addFriend = (TextView) findViewById(R.id.id_top_right_tv);
        addFriend.setOnClickListener(this);
        addFriend.setText("更多");
        changeTest();

        mEdit = (EditText) findViewById(R.id.et_chat_message);
        adapter = new ChatAdapter(this);
        adapter.setReceiverNickName(receiverNickName);
        adapter.setReceiverAvatarUrl(receiverAvatarUrl);
        msgListView.setAdapter(adapter);

        if (receiverID != null && !receiverID.equals("")) {
            instances();
        }

        setSoft();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("receiverID", receiverID);
        outState.putString("receiverNickName", receiverNickName);
        outState.putString("receiverAvatarUrl", receiverAvatarUrl);
        super.onSaveInstanceState(outState);
        log("------- ChatActivity.Class 单聊被回收 数据已缓存 --------- ");
    }

    @Deprecated
    private void changeTest() {
        addFriend.setVisibility(View.GONE);
        if (Version.getCode(this) == 2) {
            addFriend.setVisibility(View.VISIBLE);
        }
    }

    private void instances() {
        title.setText(receiverNickName);
        new Thread() {
            public void run() {
//                initMsgBean();
                initChat();
            }
        }.start();

        super.loader = MessageManager.getInstance().loadChatData(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        receiverID = getIntent().getStringExtra("receiverID");
        receiverNickName = getIntent().getStringExtra("receiverNickName");
        receiverAvatarUrl = getIntent().getStringExtra("receiverAvatarUrl");
        instances();


    }

    private void initMsgBean() {
//        if (msgBean == null) {
        msgBean = null;
        msgBean = new MsgBean();
        msgBean.setMemberId(UserBean.getMemberID());
        msgBean.setReceiverId(receiverID);
//        }
    }

    private void initChat() {
        MessageManager.getInstance().createChat(this, receiverID, UserBean.getMemberID());
    }

    public void doBack(View view) {
        finish();
    }

    public void sendOnclick(View v) {

        new Thread() {
            @Override
            public void run() {
                String content = mEdit.getText().toString();
                if (!content.equals("")) {
                    initMsgBean();
                    msgBean.setContent(content);
                    msgBean.setCreateTime(System.currentTimeMillis() + "");
                    MessageManager.getInstance().sendChatMsg(ChatActivity.this, msgBean);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mEdit.setText("");
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public String getReceiver() {
        return receiverID;
    }

    @Override
    public BaseChatBean getBean() {
        return null;
    }

    @Override
    public String getKey() {
        return UserBean.getMemberID() + receiverID;
    }

    @Override
    public void close() {
        MessageManager.getInstance().leaveChat(getKey());
    }

    @Override
    public int getType() {
        return CHAT_TYPE;
    }

    @Override
    public AbsListView getListView() {
        return msgListView;
    }

    @Override
    public void loginSucceed() {
        super.loginSucceed();
    }


    @Override
    public void onClick(View view) {

        startActivity(new Intent(this, ContactListActivity.class));

    }
}

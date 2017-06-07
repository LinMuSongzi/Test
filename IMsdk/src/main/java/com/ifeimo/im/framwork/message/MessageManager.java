package com.ifeimo.im.framwork.message;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.CursorAdapter;

import com.ifeimo.im.common.bean.chat.ChatBean;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.common.bean.chat.MuccBean;
import com.ifeimo.im.common.bean.MuccMsgBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.callback.OnSendCallBack;
import com.ifeimo.im.common.util.ConnectUtil;
import com.ifeimo.im.common.util.Jid;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.framwork.interface_im.IMessage;
import com.ifeimo.im.ManagerList;
import com.ifeimo.im.framwork.connect.IMConnectManager;
import com.ifeimo.im.framwork.notification.IMNotificationManager;
import com.ifeimo.im.provider.BaseProvider;
import com.ifeimo.im.provider.ChatProvider;
import com.ifeimo.im.provider.MuccProvider;
import com.ifeimo.im.service.MsgService;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpds on 2017/1/11.
 */
public final class MessageManager implements IMessage {

    public static final String TGA = "XMPP_MessageManager";
    private static final int RECEIVER_MSG = 2;
    private static final int SEND_MSG = 4;
    private static final int RESEN_MSG = 8;

    static MessageManager messageManager;

    private static Handler handler;
    private static final int WAITING_TIME = 7500;

    static {
        messageManager = new MessageManager();
        new Thread() {
            @Override
            public void run() {

                Looper.prepare();

                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        try {
                            switch (msg.what) {
                                case IMWindow.CHAT_TYPE:

                                    if (msg.arg1 == RECEIVER_MSG) {

                                        MsgBean msgBean = MsgBean.buildChatBean((org.jivesoftware.smack.packet.Message) msg.obj);
                                        if (msgBean != null) {
                                            MsgService.insertChat(msgBean);
                                            messageManager.handlerNotifycationChatMsg(msgBean);
                                        }
                                    } else if (msg.arg1 == SEND_MSG) {

                                        if (msg.arg2 == RESEN_MSG) {
                                            MsgService.upDataChat((MsgBean) msg.obj);

                                        } else {
                                            MsgService.insertChat((MsgBean) msg.obj);
                                        }
                                    }

                                    break;
                                case IMWindow.MUCCHAT_TYPE:
                                    if (msg.arg1 == RECEIVER_MSG) {
                                        MuccMsgBean muccMsgBean = MuccMsgBean.buildMuccBean((org.jivesoftware.smack.packet.Message) msg.obj);
                                        if (muccMsgBean != null) {
                                            MsgService.insertMucc(muccMsgBean);
                                        }
                                    } else if (msg.arg1 == SEND_MSG) {

                                        if (msg.arg2 == RESEN_MSG) {
                                            MuccMsgBean muccMsgBean = (MuccMsgBean) msg.obj;
                                            MsgService.deleteMuccById(muccMsgBean);

                                        } else {

                                            MsgService.insertMucc((MuccMsgBean) msg.obj);
                                        }
                                    }
                                    break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.i(TGA, " ----- XML 格式错误 -----" + msg.obj);
                        }
                    }
                };

                Looper.loop();


            }
        }.start();
    }

    private MessageManager() {
        ManagerList.getInstances().addManager(this);
    }

    public static IMessage getInstance() {
        return messageManager;
    }

    private Map<String, ChatBean> chatSet = new HashMap<>();
    private Map<String, MuccBean> muccSet = new HashMap<>();

    private OnMessageReceiver onMessageReceiver;

    @Deprecated
    private void addMucc(String roomName, MuccBean muccBean) {
        muccSet.put(roomName, muccBean);
    }

    public ChatBean createChat(IMWindow context, String receiverID, String memberid) {
        final String key = memberid + receiverID;
        if (chatSet.containsKey(key)) {
            ChatBean chatBean = (ChatBean) chatSet.get(key).clone();
            handler.removeCallbacks(chatSet.get(key).getRunnable());
            Log.i(TGA, "------- 找到缓存单聊。 --------");
            return chatBean;
        } else {
            ChatBean chatBean = new ChatBean(receiverID, memberid, null);
            try {
                initChat(context, chatBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            releaseChat();
            ChatBean c2 = (ChatBean) chatBean.clone();
            chatSet.put(key, c2);

            return chatBean;
        }
    }

    /**
     * 初始化单聊
     *
     * @param context
     * @param chatBean
     */
    private void initChat(IMWindow context, ChatBean chatBean) {
        Chat chat = chatBean.getChat();
        if (chat == null) {
            chat = ChatManager.getInstanceFor(
                    IMConnectManager.getInstance().
                            getConnection()).createChat(Jid.getJid(context.getContext(), chatBean.getAccount()));
            Log.i(TGA, "------- 进入单聊 对方 id = " + chatBean.getAccount() + "  --------");
        }
        chatBean.setChat(chat);
    }

//    public void sendChatMsg(IMWindow imWindow, MsgBean msg) {
//        try {
//            msg.setSendType(BaseProvider.SEND_FINISH);
//            getChatByChatSet(imWindow.getKey()).getChat().sendMessage(msg.getContent());
//            sendMessageToHandler(imWindow.getType(), SEND_MSG, 0, msg);
//        } catch (Exception e) {
//            msg.setSendType(BaseProvider.SEND_UNCONNECT);
//            sendError(imWindow, msg);
//            e.printStackTrace();
//        }
//
//    }

    public void sendChatMsg(final IMWindow imWindow, final MsgBean msg) {
        final MsgBean msgBean = msg;
        try {
            msgBean.setSendType(BaseProvider.SEND_WAITING);
            sendMessageToHandler(imWindow.getType(), SEND_MSG, 0, msgBean);

            getChatByChatSet(imWindow.getKey()).getChat().sendMessage(msgBean.getContent());

            msgBean.setSendType(BaseProvider.SEND_FINISH);
            sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msgBean);

        } catch (Exception e) {
            msgBean.setSendType(BaseProvider.SEND_UNCONNECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msgBean);
                }
            },WAITING_TIME);
            e.printStackTrace();
        }

    }

    public void sendMuccMsg(IMWindow imWindow, MuccBean muccBean, MuccMsgBean msg) {
        try {
            msg.setSendType(ChatProvider.SEND_FINISH);
            muccBean.getMultiUserChat().sendMessage(msg.getContent());
        } catch (Exception e) {
            msg.setSendType(BaseProvider.SEND_UNCONNECT);
            sendError(imWindow, msg);
            e.printStackTrace();
        }
    }

//    public void reSendChatMsg(IMWindow imWindow, MsgBean msg) {
//        try {
//            chatSet.get(imWindow.getKey()).getChat().sendMessage(msg.getContent());
//            msg.setCreateTime(System.currentTimeMillis() + "");
//            msg.setSendType(BaseProvider.SEND_FINISH);
//            sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msg);
//        } catch (Exception e) {
//            Log.i(TGA, " ------- 重发失败！ ------- ");
//            e.printStackTrace();
//        }
//    }

    public void reSendChatMsg(final IMWindow imWindow, MsgBean msg) {
        final MsgBean msgBean = msg;
        try {
            msgBean.setCreateTime(System.currentTimeMillis() + "");
            msgBean.setSendType(BaseProvider.SEND_WAITING);
            sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msg);

            chatSet.get(imWindow.getKey()).getChat().sendMessage(msg.getContent());
            msgBean.setSendType(BaseProvider.SEND_FINISH);
            sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msg);
        } catch (Exception e) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    msgBean.setSendType(BaseProvider.SEND_UNCONNECT);
                    sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msgBean);
                }
            },WAITING_TIME);
            Log.i(TGA, " ------- 重发失败！ ------- ");
            e.printStackTrace();
        }
    }

    public void reSendMuccMsg(final IMWindow imWindow, MsgBean msg, OnSendCallBack onSendCallBack) {
        try {
            msg.setSendType(BaseProvider.SEND_FINISH);
            ((MuccBean) imWindow.getBean()).getMultiUserChat().sendMessage(msg.getContent());
            sendMessageToHandler(imWindow.getType(), SEND_MSG, RESEN_MSG, msg);
            if (onSendCallBack != null) {
                onSendCallBack.callSuccess();
            }
        } catch (Exception e) {
            if (onSendCallBack != null) {
                onSendCallBack.callFail();
            }
            imWindow.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imWindow.showTipToast("无网络,重发失败");
                }
            });
            Log.i(TGA, "  -------- 重发失败！--------");
            e.printStackTrace();

        }
    }

    /**
     * 发送送错误消息
     *
     * @param imWindow
     * @param msg
     */
    private void sendError(final IMWindow imWindow, final MsgBean msg) {
        msg.setCreateTime(System.currentTimeMillis() + "");
//        int m = 0;
//        if (!ConnectUtil.isConnect(imWindow.getContext())) {
//            m = 0;
//        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (IMConnectManager.getInstance().isConnect()) {
                    return;
                }
                sendMessageToHandler(imWindow.getType(), SEND_MSG, 0, msg);
            }
        });


    }

    /**
     * 发送handler去处理消息
     * @param what
     * @param arg1
     * @param arg2
     * @param obj
     */
    private void sendMessageToHandler(int what, int arg1, int arg2, Object obj) {
        Message message = handler.obtainMessage();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.obj = obj;
        handler.sendMessage(message);
    }

    private void handlerNotifycationChatMsg(MsgBean msgBean) {
        IMNotificationManager.getInstances().
                notifyMessageNotification(msgBean);
    }


    @Deprecated
    private void releaseMuccChat() {
    }

    @Deprecated
    private void releaseChat() {
    }

    public Loader loadChatData(final IMWindow imWindow) {

        final Activity context = (Activity) imWindow.getContext();
        final AbsListView absListView = imWindow.getListView();
        final CursorAdapter adapter = (CursorAdapter) absListView.getAdapter();
        Loader loader =  context.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                Log.i(TGA, " ------- 加载消息 --------");
                return new CursorLoader(context, ChatProvider.CONTENT_URI, null, null, new String[]{
                        UserBean.getMemberID(), imWindow.getReceiver()
                }, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Log.i(TGA, " ------- 消息数量 " + cursor.getCount() + " --------");
                adapter.swapCursor(cursor);
                absListView.setSelection(adapter.getCount());
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.i(TGA, " ------- 重置 --------");
                adapter.swapCursor(null);
            }
        });

        return loader;

    }

    public Loader loadMuccData(final IMWindow imWindow) {

        final Activity context = (Activity) imWindow.getContext();
        final AbsListView absListView = imWindow.getListView();
        final CursorAdapter adapter = (CursorAdapter) absListView.getAdapter();
        Loader loader = null;
        loader = context.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                Log.i(TGA, " ------- 加载消息 --------");
                return new CursorLoader(context, MuccProvider.CONTENT_URI, null, null, new String[]{imWindow.getRoomId()}, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Log.i(TGA, " ------- 消息数量 " + cursor.getCount() + " --------");
                adapter.swapCursor(cursor);
                absListView.setSelection(adapter.getCount());
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                Log.i(TGA, " ------- 重置 --------");
                adapter.swapCursor(null);
            }
        });
        return loader;
    }

    public void leaveMuccRoom(IMWindow imWindow) {
        if (imWindow.getType() == IMWindow.MUCCHAT_TYPE) {
            try {

                MuccBean mBean = ((MuccBean) imWindow.getBean());
                if (mBean != null) {
                    MultiUserChat mChat = mBean.getMultiUserChat();
                    if (mChat != null && mChat.isJoined()) {
                        mChat.removeMessageListener(this);
                        mChat.leave();
                        mBean.setMultiUserChat(null);
                    }
                }

                Log.i(TGA, " ------- 离开群聊 --------");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }

        }
    }

    public void leaveChat(String key) {
        Log.i(TGA, " ------- 延迟退出单聊socket --------");
        ChatBean chatBean = getChatByChatSet(key);
        if(chatBean != null) {
            handler.postDelayed(chatBean.getRunnable(), DEFAULT_CACHE_TIME);
        }
    }

    @Override
    public ChatBean getChatByChatSet(String key) {
        if (chatSet.containsKey(key)){
            return chatSet.get(key);
        }
        return null;
    }

    @Deprecated
    public void removeChatSet(String key) {
        if (chatSet.containsKey(key)){
            chatSet.remove(key);
            Log.i(TGA, " ------- 删除缓存"+key+" chatSet.size = "+chatSet.size()+" --------");
        }
    }

    @Override
    @Deprecated
    public void processMessage(org.jivesoftware.smack.packet.Message message) {
        Log.i(TGA, " ------- 2222 --------" + message);
    }

    /**
     * 消息处理
     *
     * @param message
     * @throws SmackException.NotConnectedException
     */
    @Override
    public void processPacket(Stanza message) throws SmackException.NotConnectedException {
        if (message instanceof org.jivesoftware.smack.packet.Message) {
            org.jivesoftware.smack.packet.Message.Type t = ((org.jivesoftware.smack.packet.Message) message).getType();
            switch (t) {
                case groupchat: {
                    Log.i(TGA, " ------ 群聊消息 ------- " + message);
                    sendMessageToHandler(IMWindow.MUCCHAT_TYPE, RECEIVER_MSG, 0, message);
                    if (onMessageReceiver != null) {
                        onMessageReceiver.onMuccReceiver(message);
                    }
                }
                break;
                case chat: {
                    Log.i(TGA, " ------- 单聊消息 " + message + " -------");
                    sendMessageToHandler(IMWindow.CHAT_TYPE, RECEIVER_MSG, 0, message);
                    if (onMessageReceiver != null) {
                        onMessageReceiver.onChatReceiver(message);
                    }
                }
                break;
            }
        } else {
            Log.i(TGA, " ------- 服务器消息 " + message + " -------");
        }

    }


    @Override
    public boolean isInitialized() {
        return messageManager != null;
    }

    @Override
    public void update() {

    }

    @Override
    public void registerOnMessageReceiver(OnMessageReceiver onMessageReceiver) {
        removeOnMessageReceiver();
        this.onMessageReceiver = onMessageReceiver;
    }

    @Override
    public void removeOnMessageReceiver() {
        onMessageReceiver = null;
    }

}

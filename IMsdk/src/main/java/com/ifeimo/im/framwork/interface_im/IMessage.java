package com.ifeimo.im.framwork.interface_im;

import android.content.Loader;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.OnUpdate;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.common.bean.MuccMsgBean;
import com.ifeimo.im.common.bean.chat.ChatBean;
import com.ifeimo.im.common.bean.chat.MuccBean;
import com.ifeimo.im.common.callback.OnSendCallBack;
import com.ifeimo.im.framwork.message.OnMessageReceiver;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.StanzaListener;

/**
 * Created by lpds on 2017/1/17.
 */
public interface IMessage extends StanzaListener, MessageListener, IEmployee, OnUpdate {

    int DEFAULT_CACHE_TIME = 3 * 60 * 1000;

    /**
     * 离开群聊
     *
     * @param imWindow
     */
    void leaveMuccRoom(IMWindow imWindow);

    /**
     * 离开单聊
     * DEFAULT_CACHE_TIME = 3分钟
     * Chat对象 默认缓存 DEFAULT_CACHE_TIME 分钟
     *
     * @param key 缓存的 key  = 发送者id+接受者id；
     */
    void leaveChat(String key);

    /**
     * 获得缓存的单聊的 ChatBean ,不允许修改bean信息
     *
     * @param key
     * @return
     */
    ChatBean getChatByChatSet(String key);

    /**
     * 尽量在删除单聊缓存 ChatBean之前，要调用 ChatBean.getChat().close() 方法；
     *
     * @param key
     */
    void removeChatSet(String key);

    Loader loadChatData(IMWindow imWindow);

    Loader loadMuccData(IMWindow imWindow);

    /**
     * 创建一个单聊
     *
     * @param imWindow
     * @param receiverID 对方用户
     * @param memberid   自己
     * @return
     */
    ChatBean createChat(IMWindow imWindow, String receiverID, String memberid);

    /**
     * 发送群聊消息
     *
     * @param imWindow
     * @param muccBean
     * @param msg
     */
    void sendMuccMsg(IMWindow imWindow, MuccBean muccBean, MuccMsgBean msg);

    /**
     * 发送单聊消息
     *
     * @param imWindow
     * @param msg
     */
    void sendChatMsg(IMWindow imWindow, MsgBean msg);

    /**
     * 重发单聊消息
     *
     * @param imWindow
     * @param msg
     */
    void reSendChatMsg(IMWindow imWindow, MsgBean msg);

    /**
     * 重发群聊消息
     *
     * @param imWindow
     * @param msg
     */
    void reSendMuccMsg(IMWindow imWindow, MsgBean msg, OnSendCallBack onSenddCallBack);

    void registerOnMessageReceiver(OnMessageReceiver onMessageReceiver);

    void removeOnMessageReceiver();

}

package com.ifeimo.im.framwork.connect;

import android.content.Context;
import android.util.Log;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.common.bean.ConnectBean;
import com.ifeimo.im.common.bean.UserBean;
import com.ifeimo.im.common.callback.LoginCallBack;
import com.ifeimo.im.common.callback.LogoutCallBack;
import com.ifeimo.im.common.callback.OnLoginSYSJCallBack;
import com.ifeimo.im.common.util.ConnectUtil;
import com.ifeimo.im.common.util.PManager;
import com.ifeimo.im.framwork.ChatWindowsManage;
import com.ifeimo.im.framwork.interface_im.IConnect;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.ManagerList;
import com.ifeimo.im.OnUpdate;
import com.ifeimo.im.framwork.message.MessageManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lpds on 2017/1/11.
 */
public final class IMConnectManager implements IConnect {

    static IMConnectManager connectManager;
    private XMPPTCPConnectionConfiguration connConfig = null;
    private ReconnectionManager reconnectionManager;
    private ConnectBean connectBean = null;
    private XMPPTCPConnection connection;
    private Semaphore semaphore;
    private DeflaterStanzaFilter deflaterStanzaFilter;
    private StanzaListener stanzaListener;
    private Context service;
    private boolean isSYSJlLogin = false;
    private static final String TGA = "XMPP_IMConnectManager";

    private LoginCallBack loginCallBack;
    private OnLoginSYSJCallBack onLoginSYSJCallBack;
    private LogoutCallBack logoutCallBack;

    private Runnable runConnect = new Runnable() {
        @Override
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startConnection();
            semaphore.release();
        }
    };


    static {
        connectManager = new IMConnectManager();
    }

    public void init(Context context) {
        ManagerList.getInstances().addManager(this);
        service = context;
        semaphore = new Semaphore(1);
        deflaterStanzaFilter = new DeflaterStanzaFilter();
        connectBean = PManager.getConnectConfig(service);
        connConfig = XMPPTCPConnectionConfiguration.builder()
                .setHost(connectBean.getHost())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setPort(connectBean.getPort())
                .setDebuggerEnabled(true)
//                .setSendPresence(true)
                .setServiceName(connectBean.getServiceName())
                .build();

    }

    public static IConnect getInstance() {
        return connectManager;
    }

    private void startConnection(){

        if (UserBean.getMemberID() == null || UserBean.getMemberID().equals("")) {
            PManager.getCacheUser(service);
            if (UserBean.getMemberID() == null || UserBean.getMemberID().equals("")) {
                log(" MemberID = null");
                return;
            }
        }
        if (!ConnectUtil.isConnect(service)) {
            log(" ------ 无网络 ------");
            return;
        }
//        if(co)
        if (isSYSJlLogin = sendSysjService()) {
            if (connection == null || !connection.isConnected()) {
                log(" ------ IM 准备链接 ------");
                connect();
            }
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startConnection();
        }
    }

    public void runConnectThread() {
        if (!ConnectUtil.isConnect(service)) {
            return;
        }
        new Thread(runConnect).start();
    }

    private boolean sendSysjService() {
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        StringBuilder tempParams = new StringBuilder();
        Map<String, String> map = new HashMap<String, String>();
        map.put("avatarUrl", UserBean.getAvatarUrl());
        map.put("nickname", UserBean.getNickName());
        map.put("memberId", UserBean.getMemberID());
        try {
            //处理参数
            int pos = 0;
            for (String key : map.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(map.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("http://192.168.48.185:8080/IM/SetMemberInfo?%s",tempParams.toString());
            //创建一个请求
//                String content = "?memberId=" + a + "&nickname=" + b + "&avatarUrl=" + c;
            Request request = new Request.Builder()
                    .addHeader("Connection", "keep-alive")
                    .addHeader("accept", "*/*")
                    .addHeader("user-agent", "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 6.1; SV1)")
                    .url(requestUrl)
                    .build();
            Response response = null;
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                final String reJson = response.body().string();
                final JSONObject jsonObject = new JSONObject(reJson);
                final Object code = jsonObject.get("code");
                if (!"200".equals(code.toString())) {
                    if (onLoginSYSJCallBack != null) {
                        onLoginSYSJCallBack.callFail(code.toString());
                    }
                    return false;
                } else {
                    if (onLoginSYSJCallBack != null) {
                        onLoginSYSJCallBack.callSuccess();
                    }
                    log(" ------ 登录SYSJ信息 ------ " + jsonObject);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onLoginSYSJCallBack != null) {
                onLoginSYSJCallBack.callFail(null);
            }
            log(" ------ 登录sysj失败 ------ " + e);
        }
        return false;
    }

    private synchronized boolean connect() {
        try {
            if (connection == null || !connection.isConnected()) {
                if (connection == null) {
                    connection = new XMPPTCPConnection(connConfig);
                    connection.addConnectionListener(IMConnectManager.this);
                    connection.connect();
                    reconnectionManager = ReconnectionManager.getInstanceFor(connection);
                    reconnectionManager.setFixedDelay(5);//重联间隔
                    reconnectionManager.enableAutomaticReconnection();

                } else {
                    connection.connect();
                }
            }
            return true;
        } catch (Exception e) {
            log(" ------ 登录IM失败 ------ " + e);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            connect();
        }
        return false;

    }

    public void close() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
        }
    }

    @Override
    public boolean isInitialized() {
        return connectManager != null;
    }

    public class DeflaterStanzaFilter implements StanzaFilter {
        @Override
        public boolean accept(Stanza stanza) {
            return true;
        }

    }


    public void addStanzaListener(StanzaListener stanzaListener, DeflaterStanzaFilter deflaterStanzaFilter) {
        if (connection != null) {
            connection.removeAsyncStanzaListener(stanzaListener);
            connection.addAsyncStanzaListener(stanzaListener, deflaterStanzaFilter);
        }
        this.stanzaListener = stanzaListener;
        this.deflaterStanzaFilter = deflaterStanzaFilter;
    }

    @Override
    public void updateLogin() {
        if (isConnect()) {
            new Thread(){
                @Override
                public void run() {
                    sendSysjService();
                }
            }.start();
        }else{
            runConnectThread();
        }
    }

    @Override
    public void connected(XMPPConnection c) {
        log("------ 连接IM成功 ------");
        login();
    }

    private void login() {
        try {
//                AbstractXMPPConnection connection = (AbstractXMPPConnection) connection;
            if (connection != null && connection.isConnected()) {
                connection.login(UserBean.getMemberID(), UserBean.getMemberID());
                Presence presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.available);
                connection.sendStanza(presence);
                connection.addAsyncStanzaListener(MessageManager.getInstance(), deflaterStanzaFilter);
                log("------ 登陆IM成功 ------");
                loginSucceed();
            } else {

            }
        } catch (SmackException.AlreadyLoggedInException e) {
            log("------ 登陆IM成功 ------");
            loginSucceed();
        } catch (Exception e) {
            e.printStackTrace();
            log("------ 登陆失败 ------");
            if (loginCallBack != null) {
                loginCallBack.callFail();
            }
        }
    }

    private void loginSucceed() {

        final LinkedList<IMWindow> imWindows = ChatWindowsManage.getInstence().getAllIMWindows();
        for(IMWindow imWindow : imWindows){
            imWindow.loginSucceed();
        }
        if (loginCallBack != null) {
            loginCallBack.callSuccess();
        }

        for (IEmployee iEmployee : ManagerList.getInstances().getAllManager()) {
            if (iEmployee instanceof OnUpdate) {
                ((OnUpdate) iEmployee).update();
            }
        }

    }


    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {

    }

    @Override
    public void connectionClosed() {
        log("------ IM连接关闭 ------");
        if (!ConnectUtil.isConnect(service)) {
            return;
        }
//        runConnectThread();
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        log("------ IM连接异常断开 ------");
        if (!ConnectUtil.isConnect(service)) {
            return;
        }
//        runConnectThread();
    }

    @Override
    public void reconnectionSuccessful() {
        login();
        log("------ IM重连成功 ------");
    }

    @Override
    public void reconnectingIn(int seconds) {

    }

    @Override
    public void reconnectionFailed(Exception e) {
        if (loginCallBack != null) {
            loginCallBack.callFail();
        }
        log("------ IM重连失败 ------");
    }

    public XMPPTCPConnection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
            if(logoutCallBack != null){
                logoutCallBack.logoutSuccess();
                return;
            }
        }
    }

    public boolean isConnect() {
        return connection != null && connection.isConnected();
    }

    public void log(String msg) {
        Log.i(TGA, msg);
    }

    public void addLoginListener(LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
    }

    public void removeLoginListener() {
        this.loginCallBack = null;
    }

    @Override
    public void addOnSYSJLoginListener(OnLoginSYSJCallBack onLoginSYSJCallBack) {

        this.onLoginSYSJCallBack = onLoginSYSJCallBack;

    }

    @Override
    public void removeOnSYSJLoginListener() {
        onLoginSYSJCallBack = null;
    }


    @Override
    public void addLogoutCallBack(LogoutCallBack logoutCallBack) {
        this.logoutCallBack = logoutCallBack;
    }

    @Override
    public void removeLogoutCallBack() {
        logoutCallBack = null;
    }

}

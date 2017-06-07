package com.ifeimo.im.framwork.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.ifeimo.im.R;
import com.ifeimo.im.activity.ChatActivity;
import com.ifeimo.im.common.bean.MsgBean;
import com.ifeimo.im.ManagerList;
import com.ifeimo.im.framwork.ChatWindowsManage;
import com.ifeimo.im.framwork.interface_im.IMWindow;
import com.ifeimo.im.service.MsgService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by lpds on 2017/1/16.
 */
@Deprecated
public final class IMNotificationManager implements NotificationProvider<NotifyBean>{

    private static IMNotificationManager notificationManager;
    private NotificationManager notificationManagerServier;
    private Context context;

    private Map<String,Notification> messageNotifications;

    static {
        notificationManager = new IMNotificationManager();
        notificationManager.context = MsgService.service;
        notificationManager.notificationManagerServier = (NotificationManager) notificationManager.context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private IMNotificationManager(){
        ManagerList.getInstances().addManager(this);
    }

    public static IMNotificationManager getInstances(){
        return notificationManager;
    }

    public void notifyMessageNotification(MsgBean accountBean){

        if(ChatWindowsManage.getInstence().isInitialized()) {
            LinkedList<IMWindow> imWindows =  ChatWindowsManage.getInstence().getAllIMWindows();
            for(IMWindow imWindow : imWindows) {
                String receiver = imWindow.getReceiver();
                if (receiver != null) {
                    if (accountBean.getMemberId().equals(receiver)) {
                        return;
                    }
                }
            }
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setContentTitle("手游视界");
        notificationBuilder.setContentText(accountBean.getMemberId()+
                "说："+accountBean.getContent());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_round));
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setTicker("手游视界");
//        notificationBuilder.setContentTitle("")
        notificationBuilder.setSmallIcon(R.drawable.logo_round);
        notificationBuilder.setVibrate(new long[]{0,300});
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationBuilder.setLights(0xFF0000, 3000, 3000);
        Intent intent = new Intent(context, ChatActivity.class);    //点击通知进入的界面
        intent.putExtra("receiverID",accountBean.getMemberId());
        intent.putExtra("receiverNickName",accountBean.getReceiverNickName());
        intent.putExtra("receiverAvatarUrl",accountBean.getReceiverAvatarUrl());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notificationManagerServier.notify(accountBean.getId(),notification);

    }



    @Override
    public Collection<NotifyBean> getNotifications() {
        return null;
    }

    @Override
    public boolean canClearNotifications() {
        return false;
    }

    @Override
    public void clearNotifications() {

    }

    @Override
    public Uri getSound() {
        return null;
    }

    @Override
    public int getStreamType() {
        return 0;
    }

    @Override
    public int getIcon() {
        return 0;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }
}

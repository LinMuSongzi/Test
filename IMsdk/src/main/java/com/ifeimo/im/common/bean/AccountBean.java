package com.ifeimo.im.common.bean;

import android.content.Intent;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.io.Serializable;

/**
 * Created by lpds on 2017/1/17.
 * <p/>
 * 联系人
 */
public class AccountBean implements Serializable {

    int id;
    String memeberid;
    String nickName;
    String remarkName;
    String avatarUrl;
    String title;
    String content;
    String time;
    String status;
    RosterPacket.ItemType type;
    Intent intent;


    public AccountBean(int id, String memeberid, String nickName, String remarkName, String avatarUrl, String title, String content, String time, Intent intent) {
        this.id = id;
        this.memeberid = memeberid;
        this.nickName = nickName;
        this.remarkName = remarkName;
        this.avatarUrl = avatarUrl;
        this.title = title;
        this.content = content;
        this.time = time;
        this.intent = intent;
    }




    public AccountBean() {

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getMemeberid() {
        return memeberid;
    }

    public void setMemeberid(String memeberid) {
        this.memeberid = memeberid;
    }

    public RosterPacket.ItemType getType() {
        return type;
    }

    public void setType(RosterPacket.ItemType type) {
        this.type = type;
    }

    public static AccountBean buildeAccountBean(RosterEntry rosterEntry) {
/**
 * name nickName
 * roster
 * status 在线状态
 * user memberid
 * weakConnect
 */
        AccountBean accountBean = new AccountBean();
        accountBean.setNickName(rosterEntry.getName());
        accountBean.setType(rosterEntry.getType());


        return accountBean;
    }

}

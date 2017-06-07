package com.ifeimo.im.common;

import android.content.Context;
import android.content.Intent;

import com.ifeimo.im.activity.MucChatActivity;

import java.util.Map;

/**
 * Created by lpds on 2017/1/19.
 */
public class IntentManager {


    public static void createIMWindows(Context context, Map<String,String> map){
        Intent intent = new Intent(context, MucChatActivity.class);
        for(String s : map.keySet()){
            intent.putExtra(s,map.get(s));
        }
        context.startActivity(intent);
    }
}

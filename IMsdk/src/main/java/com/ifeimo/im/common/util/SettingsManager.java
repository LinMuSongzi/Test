package com.ifeimo.im.common.util;

import android.media.RingtoneManager;
import android.net.Uri;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.ManagerList;

/**
 * Created by lpds on 2017/1/16.
 */
public class SettingsManager implements IEmployee{

    private static SettingsManager settingsManager;

    static{
        settingsManager = new SettingsManager();
    }

    private SettingsManager(){
        ManagerList.getInstances().addManager(this);
    }

    @Override
    public boolean isInitialized() {
        return true;
    }
}

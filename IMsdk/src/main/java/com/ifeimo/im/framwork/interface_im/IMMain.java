package com.ifeimo.im.framwork.interface_im;

import android.content.Context;

/**
 * Created by lpds on 2017/1/24.
 */
interface IMMain {

    /**
     * 当前上下文
     * @return
     */
    Context getContext();

    void runOnUiThread(Runnable runnable);

}

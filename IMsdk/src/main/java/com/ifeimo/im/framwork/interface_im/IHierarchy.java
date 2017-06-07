package com.ifeimo.im.framwork.interface_im;

import com.ifeimo.im.IEmployee;

import java.util.LinkedList;

/**
 * Created by lpds on 2017/1/19.
 */
public interface IHierarchy extends IEmployee{


    LinkedList<IMWindow> getAllIMWindows();

    IMWindow getFirstWindow();

    void releaseAll();

}

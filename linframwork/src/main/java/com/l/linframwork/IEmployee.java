package com.l.linframwork;


/**
 * Created by lpds on 2017/5/27.
 */
public interface IEmployee extends Initialization {

    boolean hadOfficeholding();

    boolean hadDimission();

    /**
     * 开除员工回调
     */
    void fire();

}

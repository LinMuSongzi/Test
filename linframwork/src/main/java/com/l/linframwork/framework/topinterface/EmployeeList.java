package com.l.linframwork.framework.topinterface;

import com.l.linframwork.IEmployee;

/**
 * Created by lpds on 2017/6/6.
 */
public interface EmployeeList {

    void add(IEmployee iEmployee);

    <T> T get(Class<T> tClass);
}

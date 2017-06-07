package com.l.linframwork.framework;

import com.l.linframwork.IEmployee;
import com.l.linframwork.framework.topinterface.EmployeeList;

import java.util.List;

/**
 * Created by lpds on 2017/6/6.
 */
final class ManagerList implements EmployeeList{

    private static ManagerList managerList;
    static{
        managerList = new ManagerList();
        managerList.add(ThreadManager.getInstances());
        managerList.add(RequestManager.getInstances());
        managerList.add((IEmployee) ActivityBoss.getInstances());
    }

    private List<IEmployee> iEmployees;

    private ManagerList(){
    }

    public static EmployeeList getInstances(){
        return managerList;
    }

    @Override
    public void add(IEmployee iEmployee) {

    }

    @Override
    public <T> T get(Class<T> tClass) {
        return null;
    }
}

package com.ifeimo.im;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lpds on 2017/1/17.
 */
public final class ManagerList {

    static ManagerList managerList;

    Set<IEmployee> managers;

    static {
        managerList = new ManagerList();
    }

    private ManagerList(){
        managers = new HashSet();
    }

    public static ManagerList getInstances(){
        return managerList;
    }

    public  void addManager(IEmployee o){
        if(!managers.contains(o.getClass().getSimpleName())){
            managers.add(o);
        }
    }

    public Set<IEmployee> getAllManager(){

        Set<IEmployee> iEmployees = new HashSet<>();
        iEmployees.addAll(managers);

        return iEmployees;

    }
}

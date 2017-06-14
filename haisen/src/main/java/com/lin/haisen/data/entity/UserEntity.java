package com.lin.haisen.data.entity;


import rx.Subscriber;

/**
 * Created by lpds on 2017/6/7.
 */
public class UserEntity extends BaseEntity {

    String account;
    String password;
    String secretKey;

    public UserEntity() {

    }

    public UserEntity(String account, String password, String secretKey) {
        this.account = account;
        this.password = password;
        this.secretKey = secretKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}

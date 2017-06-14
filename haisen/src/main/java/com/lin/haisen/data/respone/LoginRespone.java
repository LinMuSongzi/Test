package com.lin.haisen.data.respone;

/**
 * Created by lpds on 2017/6/14.
 */
public class LoginRespone {


    /**
     * key : null
     * result : 303
     * result_msg : 账号不存在
     */

    private String key;
    private int result;
    private String result_msg;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    @Override
    public String toString() {
        return "LoginRespone{" +
                "key='" + key + '\'' +
                ", result=" + result +
                ", result_msg='" + result_msg + '\'' +
                '}';
    }
}

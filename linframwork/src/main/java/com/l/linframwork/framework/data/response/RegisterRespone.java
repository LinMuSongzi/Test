package com.l.linframwork.framework.data.response;

/**
 * Created by lpds on 2017/6/7.
 */
public class RegisterRespone {


    /**
     * result : 100
     * result_msg : null
     * data : {"id":25,"account":"99099091","address":null,"name":null,"nick_name":null,"password":"nYTytVljLBMLOxRBqGonpQ==","phone_number":null,"register_time":"\\/Date(1496807748690+0800)\\/","remark":null,"sex":null}
     */

    private int result;
    private Object result_msg;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(Object result_msg) {
        this.result_msg = result_msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 25
         * account : 99099091
         * address : null
         * name : null
         * nick_name : null
         * password : nYTytVljLBMLOxRBqGonpQ==
         * phone_number : null
         * register_time : \/Date(1496807748690+0800)\/
         * remark : null
         * sex : null
         */

        private int id;
        private String account;
        private Object address;
        private Object name;
        private Object nick_name;
        private String password;
        private Object phone_number;
        private String register_time;
        private Object remark;
        private Object sex;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getNick_name() {
            return nick_name;
        }

        public void setNick_name(Object nick_name) {
            this.nick_name = nick_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(Object phone_number) {
            this.phone_number = phone_number;
        }

        public String getRegister_time() {
            return register_time;
        }

        public void setRegister_time(String register_time) {
            this.register_time = register_time;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }
    }
}

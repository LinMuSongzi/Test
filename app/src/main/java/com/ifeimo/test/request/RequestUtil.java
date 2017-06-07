package com.ifeimo.test.request;

import android.util.Log;
import android.widget.TextView;

import com.ifeimo.test.bean.BaseBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lpds on 2017/2/13.
 */
public final class RequestUtil {


    public final static void test1(final Object... o){
        OkHttpUtils.get().tag(o[0]).url("https://www.baidu.com").build().execute(new Callback<BaseBean>() {
            @Override
            public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                BaseBean baseBean = new BaseBean();
                baseBean.setBody(new String(response.body().bytes()));
                return baseBean;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(BaseBean response, int id) {
                ((TextView)o[1]).setText(response.getBody());
            }
        });
    }

    public final static boolean test2(){


            try{

                Response call = OkHttpUtils.get().
                        url("http://114.215.189.100:8080/IM/SetMemberInfo")
                        .addParams("avatarUrl","http://apps.ifeimo.com/Public/Uploads/Member/Avatar/56a0d1b3eef0e.jpg")
                        .addParams("nickname","哈哈")
                        .addParams("memberId","965877").build().execute();
                ResponseBody requestBody = call.body();
                String body = requestBody.string();
                Log.e("121212","ResponseBody = "+body);
                JSONObject jsonObject = new JSONObject(body);
                Log.e("121212","JSONObject = "+jsonObject.toString());
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
    }

}

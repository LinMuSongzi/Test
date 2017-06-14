package com.lin.haisen.ui.fragment;


import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.thread.IExecute;
import com.lin.haisen.R;
import com.lin.haisen.commont.BaseSubscriber;
import com.lin.haisen.data.entity.UserEntity;
import com.lin.haisen.data.respone.LoginRespone;
import com.lin.haisen.request.imp.UserServiceImp;
import com.lin.haisen.ui.activity.UserInfoActivity;
import com.lin.haisen.ui.fragment.support.IActionBar;
import com.lin.haisen.ui.popupwindow.PopupWindowFactory;

import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by lpds on 2017/6/12.
 */
public class LoginFragment extends BaseFragment<IActionBar> implements IActionBar {

    //    @Bind(R.id.user_et)
    EditText user_et;
    //    @Bind(R.id.password_et)
    EditText password_et;
    Button loginBotton;

    PopupWindow progressPopupWindow;

    @Override
    protected void initView() {
        progressPopupWindow = PopupWindowFactory.getProgressPopupWindow(getIActivity());
        progressPopupWindow.setFocusable(false);
        user_et = (EditText) findViewById(R.id.user_et);
        password_et = (EditText) findViewById(R.id.password_et);
        (loginBotton = (Button) findViewById(R.id.id_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                getIActivity().postThread(new IExecute() {
                    @Override
                    public void execute() {

                        final String user = user_et.getText().toString();
                        final String password = password_et.getText().toString();
                        if (StringDdUtil.isNull(user) || StringDdUtil.isNull(password)) {
                            getIActivity().showToast("user or password is null values");
                            return;
                        }


                        UserEntity userEntity = new UserEntity("aasd", user, password);

                        UserServiceImp.login1(userEntity, new BaseSubscriber<LoginRespone>(getIActivity()) {

                            @Override
                            public void onStart() {
                                onStartReuqtest();
                                super.onStart();
                            }

                            @Override
                            protected void next(LoginRespone s) {
                                onEndRequest();
                                if(!StringDdUtil.isNull(s.getKey())) {
                                    v.getContext().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                                }
                                getIActivity().showToast(s.getResult_msg());
                            }

                            @Override
                            protected void error(Throwable throwable) {
                                super.error(throwable);
                                onEndRequest();
                            }

                            @Override
                            protected void completed() {

                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_login;
    }

    @Override
    public IActionBar getT() {
        return this;
    }

    @Override
    public String getTitle() {
        return "Login";
    }

    private void onStartReuqtest() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginBotton.setEnabled(false);
                progressPopupWindow.showAtLocation(loginBotton, Gravity.CENTER,0,0);
            }
        });


    }

    private void onEndRequest() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginBotton.setEnabled(true);
                progressPopupWindow.dismiss();
            }
        });
    }

}

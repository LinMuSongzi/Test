package com.lin.haisen.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.thread.IExecute;
import com.lin.haisen.R;
import com.lin.haisen.commont.BaseSubscriber;
import com.lin.haisen.data.entity.UserEntity;
import com.lin.haisen.request.imp.UserServiceImp;
import com.lin.haisen.ui.popupwindow.PopupWindowFactory;
import com.lin.haisen.ui.fragment.support.IActionBar;

import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by lpds on 2017/6/10.
 */
public class RegisterFragment extends BaseFragment<IActionBar> implements IActionBar {
//
//    @Bind(R.id.user_et)
    EditText user_et;
//    @Bind(R.id.password_et)
    EditText password_et;
    Button registerBotton;

    PopupWindow progressPopupWindow;

    @Override
    protected void initView() {
        progressPopupWindow = PopupWindowFactory.getProgressPopupWindow(getIActivity());
        progressPopupWindow.setFocusable(false);
        user_et = (EditText) findViewById(R.id.user_et);
        password_et= (EditText) findViewById(R.id.password_et);

        (registerBotton = (Button)findViewById(R.id.id_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onclick");
                getIActivity().postThread(new IExecute() {
                    @Override
                    public void execute() {

                        final String user = user_et.getText().toString();
                        final String password = password_et.getText().toString();
                        if(StringDdUtil.isNull(user) || StringDdUtil.isNull(password)){
                            getIActivity().showToast("user or password is null values");
                            return;
                        }

                        UserEntity userEntity = new UserEntity("1",user,password);

                        UserServiceImp.register(userEntity,new BaseSubscriber<JsonObject>(getIActivity()) {
                            @Override
                            public void onStart() {
                                super.onStart();
                                onStartReuqtest();
                            }

                            @Override
                            protected void error(Throwable throwable) {
                                super.error(throwable);
                                onEndRequest();
                            }

                            @Override
                            protected void next(JsonObject s) {
//                                System.out.println("********* "+s+" ************");

                            }

                            @Override
                            protected void completed() {
                                onEndRequest();
//                                System.out.println("********* completed ************");
                            }

                        });
                    }
                });
            }
        });
    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_register;
    }

    @Override
    public String getTitle() {
        return "Register";
    }

    @Override
    public IActionBar getT() {
        return this;
    }

    private void onStartReuqtest() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registerBotton.setEnabled(false);
                progressPopupWindow.showAtLocation(registerBotton, Gravity.CENTER,0,0);
            }
        });


    }

    private void onEndRequest() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registerBotton.setEnabled(true);
                progressPopupWindow.dismiss();
            }
        });
    }

}

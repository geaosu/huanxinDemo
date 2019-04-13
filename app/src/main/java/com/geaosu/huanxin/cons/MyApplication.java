package com.geaosu.huanxin.cons;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHuanXinSDK();

    }

    /**
     * 初始化环信sdk
     */
    private void initHuanXinSDK() {
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoLogin(true);//自动登录
        emOptions.setDeleteMessagesAsExitGroup(true);//退出群组后删除聊天记录

        EMClient.getInstance().init(this, emOptions);
    }
}

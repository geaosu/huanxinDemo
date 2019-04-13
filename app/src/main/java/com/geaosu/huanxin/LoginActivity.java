package com.geaosu.huanxin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;


    private EditText mUser;
    private EditText mPwd;
    private TextView mLogin;
    private TextView mNewUser;
    private ImageView mShowPwd;
    private RadioButton mSavePad;
    private RadioButton mAutoLogin;
    private boolean mPwdIsShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mBack.setVisibility(View.GONE);
        mTitle.setText("用户登录");

        mUser = (EditText) findViewById(R.id.user);
        mPwd = (EditText) findViewById(R.id.pwd);
        mLogin = (TextView) findViewById(R.id.login);
        mNewUser = (TextView) findViewById(R.id.newUser);

        mShowPwd = (ImageView) findViewById(R.id.showPwd);
        if (mPwdIsShow) {
            mShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_show));
        } else {
            mShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_hide));
        }
        mSavePad = (RadioButton) findViewById(R.id.savePad);
        mAutoLogin = (RadioButton) findViewById(R.id.autoLogin);


        mBack.setOnClickListener(this);
        mShowPwd.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mNewUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showPwd:
                if (mPwdIsShow) {
                    //设置密码不可见
                    mPwdIsShow = false;
                    mShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_hide));
                    mPwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    //设置密码可见
                    mPwdIsShow = true;
                    mShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_show));
                    mPwd.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.login:
                final String userName = mUser.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String pwd = mPwd.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }



                //登录
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                }).start();

                showLoading("登录中");
                EMClient.getInstance().login(userName, pwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        cancelLoading();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        cancelLoading();
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

                break;
            case R.id.newUser:
                startActivity(new Intent(LoginActivity.this, RegisterNewUserActivity.class));
                break;
        }
    }
}

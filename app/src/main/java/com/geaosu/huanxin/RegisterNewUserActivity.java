package com.geaosu.huanxin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


public class RegisterNewUserActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private EditText mUserName;
    private ImageView mClearUserName;
    private EditText mPwd;
    private ImageView mClearPwd;
    private EditText mPwd2;
    private ImageView mClearPwd2;
    private TextView mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        initView();
        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(this);
        mClearUserName.setOnClickListener(this);
        mClearPwd.setOnClickListener(this);
        mClearPwd2.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText("新用户注册");

        mUserName = (EditText) findViewById(R.id.userName);
        mClearUserName = (ImageView) findViewById(R.id.clearUserName);
        mPwd = (EditText) findViewById(R.id.pwd);
        mClearPwd = (ImageView) findViewById(R.id.clearPwd);
        mPwd2 = (EditText) findViewById(R.id.pwd2);
        mClearPwd2 = (ImageView) findViewById(R.id.clearPwd2);
        mRegister = (TextView) findViewById(R.id.register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clearUserName:
                mUserName.setText("");
                break;
            case R.id.clearPwd:
                mPwd.setText("");
                break;
            case R.id.clearPwd2:
                mPwd2.setText("");
                break;
            case R.id.register:
                String userName = mUserName.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                String pwd2 = mPwd2.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    showToast("用户名为空");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    return;
                }

                if (TextUtils.isEmpty(pwd2)) {
                    showToast("确认密码不能为空");
                    return;
                }


                if (!pwd.equals(pwd2)) {
                    showToast("两次输入的密码不一致");
                    return;
                }
                try {
                    register(userName, pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 注册账号
     *
     * @param userName
     * @param pwd
     */
    private void register(final String userName, final String pwd) {
        showLoading("正在注册");
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(userName, pwd);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterNewUserActivity.this.isFinishing()) {
                                cancelLoading();
                            }
                            //保存当前新注册的用户名
                            //DbHelper.getInstance().save(userName);
                            showToast("注册成功");
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterNewUserActivity.this.isFinishing())
                                cancelLoading();
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                showToast("网络异常，请检查网络");
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                showToast("用户名已存在");
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                showToast("注册失败，无权限");
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                showToast("用户名不合法");
                            } else if (errorCode == EMError.EXCEED_SERVICE_LIMIT) {
                                showToast("注册数量已达上限,请升级企业版");
                            } else {
                                showToast("注册失败");
                            }
                        }
                    });
                }
            }
        }).start();


    }

}

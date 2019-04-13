package com.geaosu.huanxin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;


    private EditText etUserName;
    private ImageView ivClearUserName;
    private EditText etPwd;
    private TextView tvLogin;
    private TextView tvNewUser;
    private ImageView ivShowPwd;
    private ImageView ivClearPwd;
    private CheckBox cbSavePwd;
    private CheckBox cbAutoLogin;

    private boolean mPwdIsShow = false;
    private String mUserName;
    private String mPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }


    private void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mBack.setVisibility(View.GONE);
        mTitle.setText("用户登录");

        etUserName = (EditText) findViewById(R.id.userName);
        ivClearUserName = (ImageView) findViewById(R.id.clearUserName);
        etPwd = (EditText) findViewById(R.id.pwd);
        tvLogin = (TextView) findViewById(R.id.login);
        tvNewUser = (TextView) findViewById(R.id.newUser);

        ivClearPwd = (ImageView) findViewById(R.id.clearPwd);
        ivShowPwd = (ImageView) findViewById(R.id.showPwd);
        if (mPwdIsShow) {
            ivShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_show));
        } else {
            ivShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_hide));
        }

        cbSavePwd = (CheckBox) findViewById(R.id.cbSavePwd);
        cbAutoLogin = (CheckBox) findViewById(R.id.cbAutoLogin);


        mBack.setOnClickListener(this);
        ivClearUserName.setOnClickListener(this);
        ivClearPwd.setOnClickListener(this);
        ivShowPwd.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);
    }

    private void initListener() {

        //用户名: 将光标移动到内容最后面
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!StringUtiles.isEnStr(etUserName.getText().toString().trim())) {
//                    showToast("只能输入英文字母");
//                    return;
//                }
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etUserName.setSelection(s.length());                  //将光标移至文字末尾
                mUserName = etUserName.getText().toString().trim();
            }
        });

        //密码框: 将光标移动到内容最后面
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPwd.setSelection(s.length());
                mPwd = etPwd.getText().toString().trim();
            }
        });

        //保存密码
        cbSavePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("geaosu", cbSavePwd.getText().toString() + "  ---->>  选中状态: selected = " + isChecked);

            }
        });

        //自动登录
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("geaosu", cbAutoLogin.getText().toString() + "  ---->>  选中状态: selected = " + isChecked);

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearPwd:
                etPwd.setText("");
                break;
            case R.id.clearUserName:
                etUserName.setText("");
                break;
            case R.id.showPwd:
                if (mPwdIsShow) {
                    //设置密码不可见
                    mPwdIsShow = false;
                    ivShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_hide));
                    //从密码可见模式变为密码不可见模式
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //设置密码可见
                    mPwdIsShow = true;
                    ivShowPwd.setImageDrawable(getDrawable(R.drawable.pwd_show));
                    //从密码不可见模式变为密码可见模式
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.login:
                if (TextUtils.isEmpty(mUserName)) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mPwd)) {
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
                EMClient.getInstance().login(mUserName, mPwd, new EMCallBack() {
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

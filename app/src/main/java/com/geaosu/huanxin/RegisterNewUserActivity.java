package com.geaosu.huanxin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterNewUserActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mBack;
    private TextView mTitle;

    private EditText mUser;
    private EditText mPwd;
    private EditText mPwd2;
    private TextView mRegister;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        initView();
    }

    private void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText("新用户注册");

        mUser = (EditText) findViewById(R.id.user);
        mPwd = (EditText) findViewById(R.id.pwd);
        mPwd2 = (EditText) findViewById(R.id.pwd2);
        mRegister = (TextView) findViewById(R.id.register);

        mBack.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.register:

                break;
        }
    }

}

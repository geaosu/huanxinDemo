package com.geaosu.huanxin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    private Dialog mDialog;
    private TextView mLoadingTitle;
    private ImageView mLoadingImage;

    protected void showToast(String msg) {
        cancelToast();
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.show();
    }

    protected void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    protected void showLoading(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog == null || mLoadingTitle == null || mLoadingImage == null) {
                    View view;
                    view = LayoutInflater.from(BaseActivity.this).inflate(R.layout.loading_layout, null, false);
                    mLoadingTitle = (TextView) view.findViewById(R.id.tvLoadingTitle);
                    mLoadingImage = (ImageView) view.findViewById(R.id.ivLoadingImage);
                    mDialog = new Dialog(BaseActivity.this, R.style.dialog_transparent);
                    mDialog.setCancelable(false);
                    mDialog.setContentView(view);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                                finish();
                            }
                            return false;
                        }
                    });
                }
                if (msg == null) {
                    mLoadingTitle.setVisibility(View.GONE);
                } else {
                    mLoadingTitle.setVisibility(View.VISIBLE);
                    mLoadingTitle.setText(msg);
                }
                Animation animation = new RotateAnimation(0, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);
                animation.setRepeatCount(-1);
                animation.setInterpolator(new LinearInterpolator());
                mLoadingImage.startAnimation(animation);
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        });
    }

    protected void cancelLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mDialog.isShowing()) {
                    if (mLoadingImage != null) {
                        mLoadingImage.clearAnimation();
                    }
                    mDialog.dismiss();
                }
            }
        });
    }
}

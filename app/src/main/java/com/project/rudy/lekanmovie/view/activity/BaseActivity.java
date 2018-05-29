package com.project.rudy.lekanmovie.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.rudy.lekanmovie.utils.Configs;
import com.tencent.smtt.sdk.QbSdk;

import java.io.Serializable;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {

    protected static final String PARAM_1 = "param_1";
    protected static final String PARAM_2 = "param_2";
    protected static final String OBJ_1 = "obj_1";
    protected static final int REQUEST_CODE_1 = 0x22;

    private static final String[] PARAMS = {PARAM_1, PARAM_2};
    private static final String[] OBJECTS = {OBJ_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar();
        initX5WebView();
        //Bmob.initialize(this, Configs.BOMB_APPLICATION_ID);
    }


    /**
     * Activity跳转导航
     *
     * @param activity 目标Activity.class
     */
    protected void navigate(Class activity) {
        startActivity(new Intent(this, activity));
    }

    public static void navigate(Context activity, Class toActivity, @NonNull Object... params) {
        activity.startActivity(assembleIntentWithParam(new Intent(activity, toActivity), params));
    }

    private static Intent assembleIntentWithParam(Intent intent, @NonNull Object... params) {
        int p_i = 0;
        int o_i = 0;

        for (Object obj : params) {
            if (obj instanceof Integer) {
                intent.putExtra(PARAMS[p_i], (int) obj);
            } else if (obj instanceof Boolean) {
                intent.putExtra(PARAMS[p_i], (boolean) obj);
            } else if (obj instanceof Float) {
                intent.putExtra(PARAMS[p_i], (float) obj);
            } else if (obj instanceof Double) {
                intent.putExtra(PARAMS[p_i], (double) obj);
            } else if (obj instanceof String) {
                intent.putExtra(PARAMS[p_i], (String) obj);
            } else if (obj instanceof Long) {
                intent.putExtra(PARAMS[p_i], (long) obj);
            } else if (obj instanceof Parcelable) {
                intent.putExtra(OBJECTS[o_i], (Parcelable) obj);
            } else if (obj instanceof Serializable) {
                intent.putExtra(OBJECTS[o_i], (Serializable) obj);
            }

            if (obj instanceof Integer || obj instanceof Boolean || obj instanceof Float
                    || obj instanceof Double || obj instanceof String || obj instanceof Long) {
                p_i++;
            } else if (obj instanceof Parcelable || obj instanceof Serializable) {
                o_i++;
            }
        }

        return intent;
    }

    /**
     * 带水波动画的Activity跳转
     */
    @SuppressLint("NewApi")
    protected void navigateWithRippleCompat(final Activity activity, final Intent intent,
                                            final View triggerView, @ColorRes int color) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat option = ActivityOptionsCompat.makeClipRevealAnimation(triggerView, 0, 0,
                    triggerView.getMeasuredWidth(), triggerView.getMeasuredHeight());
            ActivityCompat.startActivity(activity, intent, option.toBundle());

            return;
        }

        int[] location = new int[2];
        triggerView.getLocationInWindow(location);
        final int cx = location[0] + triggerView.getWidth() / 2;
        final int cy = location[1] + triggerView.getHeight() / 2;
        final ImageView view = new ImageView(activity);
        view.setImageResource(color);
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        decorView.addView(view, w, h);
        int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(500);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                activity.startActivity(intent);
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                decorView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        decorView.removeView(view);
                    }
                }, 500);
            }
        });
        anim.start();
    }

    /**
     * 检测系统版本并使状态栏全透明
     */
    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
package org.rhona.theme.core;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.*;
import android.widget.FrameLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rhona.theme.R;
import org.rhona.theme.eventBus.OnThemeEvent;
import org.rhona.theme.utils.AppTools;
import org.rhona.theme.utils.IConstant;
import org.rhona.theme.utils.OsUtil;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BaseThemeActivity extends SkinCompatActivity {
    private FrameLayout mFrameLayoutContent;
    private View mViewStatusBarPlace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_status_bar);
        mViewStatusBarPlace = findViewById(R.id.view_status_bar_place);
        mFrameLayoutContent = findViewById(R.id.frame_layout_content_place);

        ViewGroup.LayoutParams params = mViewStatusBarPlace.getLayoutParams();
        params.height = AppTools.getStatusBarHeight(this);
        mViewStatusBarPlace.setLayoutParams(params);
        try {
            setImmersiveStatusBar(SkinCompatManager.getInstance().getCurSkinName(), 0xffffffff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, mFrameLayoutContent, false);
        mFrameLayoutContent.addView(contentView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param fontIconDark        夜间模式的时候 状态栏的文字颜色必须是白色的,
     * @param statusBarPlaceColor 状态栏的颜色
     */
    protected void setImmersiveStatusBar(String fontIconDark, int statusBarPlaceColor) {
        boolean isNight = false;
        if (IConstant.NIGHTNAME.equalsIgnoreCase(fontIconDark)) {
            isNight = true;
        }
        setTranslucentStatus();
        if (!isNight) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || OsUtil.isMIUI()
                    || OsUtil.isFlyme()) {
                setStatusBarFontIconDark(true);
            } else {
                statusBarPlaceColor = 0xffcccccc;
            }
        }
        setStatusBarPlaceColor(statusBarPlaceColor);
    }

    protected void setStatusBarPlaceColor(int statusColor) {
        if (mViewStatusBarPlace != null) {
            if (TextUtils.isEmpty(SkinCompatManager.getInstance().getCurSkinName())) {//白天模式
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mViewStatusBarPlace.setBackground(((ViewGroup) getWindow().getDecorView()).getChildAt(0).getBackground());
                } else {
                    mViewStatusBarPlace.setBackgroundColor(statusColor);
                }
            } else {//夜间模式
                mViewStatusBarPlace.setBackground(((ViewGroup) getWindow().getDecorView()).getChildAt(0).getBackground());
            }
        }
    }


    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThemeEvent(final OnThemeEvent event) {
        SkinCompatManager.getInstance().loadSkin();
        SkinCompatManager.getInstance().loadSkin(event.getThemeName(), new SkinCompatManager.SkinLoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                setImmersiveStatusBar(event.getThemeName(), Color.parseColor("#00ffffff"));
            }

            @Override
            public void onFailed(String errMsg) {

            }
        });
    }
}

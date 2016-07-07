package com.android.mvpp2p.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.android.mvpp2p.AppManager;
import com.android.mvpp2p.MyApplication;
import com.android.mvpp2p.R;
import com.android.mvpp2p.injector.component.ActivityComponent;
import com.android.mvpp2p.injector.component.ApplicationComponent;
import com.android.mvpp2p.injector.module.ActivityModule;
import com.android.mvpp2p.utils.ResourceUtil;
import com.android.mvpp2p.utils.SettingPrefUtil;
import com.android.mvpp2p.utils.StatusBarUtil;
import com.android.mvpp2p.utils.ThemeUtil;

/**
 * Created by sll on 2016/3/9.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent mActivityComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        initInjector();
        initUiAndListener();
        AppManager.getAppManager().addActivity(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private void initTheme() {
        int theme;
        try {
            theme = getPackageManager().getActivityInfo(getComponentName(), 0).theme;
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        if (theme != R.style.AppThemeLaunch) {
            theme = ThemeUtil.themeArr[SettingPrefUtil.getThemeIndex(this)][
                    SettingPrefUtil.getNightModel(this) ? 1 : 0];
        }
        setTheme(theme);
    }

    /**
     * 设置view
     */
    public abstract int initContentView();

    /**
     * 注入Injector
     */
    public abstract void initInjector();

    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener();

    /**
     * is applyStatusBarTranslucency
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * set status bar translucency
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract boolean isApplyStatusBarColor();

    /**
     * use SystemBarTintManager
     */
    public void setStatusBarColor(boolean on) {
        if (on) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
        }
    }

    public void reload() {
        Log.i("重启", "重启中");
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        Log.i("重启", "重启OK");
    }

    public int getStatusBarHeight() {
        return ResourceUtil.getStatusBarHeight(this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}

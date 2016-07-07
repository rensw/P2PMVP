package com.android.mvpp2p.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseSwipeBackActivity;
import com.android.mvpp2p.injector.HasComponent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseSwipeBackActivity implements HasComponent<SettingComponent> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    SettingComponent mSettingComponent;

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int initContentView() {
        return R.layout.base_content_toolbar_layout;
    }

    @Override
    public void initInjector() {
        mSettingComponent = DaggerSettingComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build();
        mSettingComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingFragment()).commit();
    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    public boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public SettingComponent getComponent() {
        return mSettingComponent;
    }
}

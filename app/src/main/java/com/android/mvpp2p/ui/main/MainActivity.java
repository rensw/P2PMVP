package com.android.mvpp2p.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.mvpp2p.R;
import com.android.mvpp2p.injector.HasComponent;
import com.android.mvpp2p.ui.BaseActivity;
import com.android.mvpp2p.utils.ResourceUtil;
import com.android.mvpp2p.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements View.OnClickListener, MainContract.View, HasComponent<MainComponent> {


    MainComponent mMainComponent;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Inject
    MainPresenter mPresenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        mMainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule()).mainModule(new MainModule(this))
                .build();
        mMainComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, ResourceUtil.getThemeColor(this), 0);
        mPresenter.attachView(this);
    }

    @Override
    public void onBackPressed() {
        mPresenter.exits();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }


    @Override
    public MainComponent getComponent() {
        return mMainComponent;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void closeDrawers() {

    }
}

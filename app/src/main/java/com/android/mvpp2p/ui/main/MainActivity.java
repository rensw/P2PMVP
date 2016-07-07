package com.android.mvpp2p.ui.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.mvpp2p.Constants;
import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseActivity;
import com.android.mvpp2p.injector.HasComponent;
import com.android.mvpp2p.ui.product.ProductFragment;
import com.android.mvpp2p.ui.setting.SettingActivity;
import com.android.mvpp2p.utils.ResourceUtil;
import com.android.mvpp2p.utils.RxBus;
import com.android.mvpp2p.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    Fragment mContent;
    Observable observable;

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
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, ResourceUtil.getThemeColor(this), 0);
        mContent = new ProductFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content,
                        mContent)
                .commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mPresenter.NavigationItemSelected(item);
                return true;
            }
        });
        observable = RxBus.get().register(Constants.CHANGE_THEME, MainActivity.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                reload();
            }
        });
        mPresenter.attachView(this);
    }

    @Override
    public void onBackPressed() {
        mPresenter.exits();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(observable!=null){
            RxBus.get().unregister(Constants.CHANGE_THEME,observable);
        }
        mPresenter.detachView();
    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    public boolean isApplyStatusBarColor() {
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
        drawerLayout.closeDrawers();
    }

    @Override
    public void showFragment(Fragment fragment) {
        if (mContent != fragment) {
            mContent = fragment;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
        }
    }

    @Override
    public void intentSeting() {
        SettingActivity.startActivity(this);
    }

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }
}

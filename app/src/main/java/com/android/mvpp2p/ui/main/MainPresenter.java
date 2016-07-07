package com.android.mvpp2p.ui.main;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.android.mvpp2p.AppManager;
import com.android.mvpp2p.R;
import com.android.mvpp2p.api.HttpApi;
import com.android.mvpp2p.injector.PerActivity;
import com.android.mvpp2p.ui.information.InformationFragment;
import com.android.mvpp2p.ui.product.ProductFragment;
import com.android.mvpp2p.utils.ToastUtil;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by apple on 16/7/5.
 */
@PerActivity
public class MainPresenter implements MainContract.Presenter {

    Context mContext;
    private MainContract.View mMainView;
    private Subscription mSubscription;
    private HttpApi mHttpApi;

    @Inject
    public MainPresenter(HttpApi mHttpApi, Context mContext) {
        this.mHttpApi = mHttpApi;
        this.mContext = mContext;
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {
        mMainView = view;
    }

    @Override
    public void detachView() {

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mMainView = null;
    }

    @Override
    public void exits() {
        if (isCanExit()) {
            AppManager.getAppManager().AppExit(mContext);
        }
    }

    @Override
    public void NavigationItemSelected(MenuItem item) {
        Fragment mFragment = null;
        if (item.getItemId() == R.id.nav_companyLoan) {//特权
            mFragment = new ProductFragment();
        }
        if (item.getItemId() == R.id.nav_infomation) {//资讯
            mFragment = new InformationFragment();
        }
        if (item.getItemId() == R.id.nav_manage) {//理财标的
            mFragment = new ProductFragment();
        }
        if (item.getItemId() == R.id.nav_my) {//我的账户
            mFragment = new ProductFragment();
        }
        if (item.getItemId() == R.id.nav_plat) {//搜平台
            mFragment = new ProductFragment();
        }
        if (item.getItemId() == R.id.nav_setting) {//搜平台
            mMainView.intentSeting();
        }
        if (mFragment != null) {
            mMainView.setTitle(item.getTitle());
            mMainView.showFragment(mFragment);
            mMainView.closeDrawers();
        }
    }

    public long mExitTime = 0;

    public boolean isCanExit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
            ToastUtil.showToast("再按一次退出程序");
            return false;
        }
        return true;
    }
}

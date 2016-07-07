package com.android.mvpp2p.ui.information;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.mvpp2p.R;
import com.android.mvpp2p.base.BaseFragment;
import com.android.mvpp2p.ui.HotNews.HotNewsFragment;
import com.android.mvpp2p.ui.main.MainComponent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/7/5.
 */
public class InformationFragment extends BaseFragment implements InformationContract.View {

    @Inject
    InformationPresenter mInformationPresenter;
    @Bind(R.id.tab_FindFragment_title)
    TabLayout tabFindFragmentTitle;
    @Bind(R.id.vp_FindFragment_pager)
    ViewPager vpFindFragmentPager;
    String[] list_title;

    @Override
    public void initInjector() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_information;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        list_title = getResources().getStringArray(R.array.information_titles);
        vpFindFragmentPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        tabFindFragmentTitle.setupWithViewPager(vpFindFragmentPager);
        tabFindFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        mInformationPresenter.attachView(this);

        showContent(true);
    }

    @Override
    public void initData() {

    }

    public void onDestroy() {
        super.onDestroy();
        mInformationPresenter.detachView();
        ButterKnife.unbind(this);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list_title.length;
        }

        @Override
        public Fragment getItem(int position) {
            return HotNewsFragment.newInstance(position+ 10);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return list_title[position];
        }
    }
}

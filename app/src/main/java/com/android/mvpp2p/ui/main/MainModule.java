package com.android.mvpp2p.ui.main;

import dagger.Module;

/**
 * Created by apple on 16/7/5.
 */
@Module public class MainModule {
    private MainActivity mActivity;

    public MainModule(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

}

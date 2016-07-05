package com.android.mvpp2p.injector.component;

import android.app.Activity;

import com.android.mvpp2p.injector.PerActivity;
import com.android.mvpp2p.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by sll on 2016/3/8.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  Activity getActivity();
}

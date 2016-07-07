package com.android.mvpp2p.ui.setting;

/**
 * Created by apple on 16/7/5.
 */

import com.android.mvpp2p.injector.PerActivity;
import com.android.mvpp2p.injector.component.ApplicationComponent;
import com.android.mvpp2p.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by sll on 2016/5/13.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        ActivityModule.class
})
public interface SettingComponent {

    void inject(SettingActivity activity);

    void inject(ColorsDialogFragment colorsDialogFragment);


}


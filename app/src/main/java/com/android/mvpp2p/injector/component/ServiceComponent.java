package com.android.mvpp2p.injector.component;

import android.app.Service;

import com.android.mvpp2p.injector.PerService;
import com.android.mvpp2p.injector.module.ServiceModule;

import dagger.Component;

/**
 * Created by sll on 16/03/16.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = { ServiceModule.class })
public interface ServiceComponent {

  Service getServiceContext();

}

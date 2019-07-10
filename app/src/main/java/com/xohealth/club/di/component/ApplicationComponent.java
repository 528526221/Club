package com.xohealth.club.di.component;



import com.xohealth.club.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xulc on 2018/11/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    FragmentComponent.Builder buildFragmentComponent();
    ActivityComponent.Builder buildActivityComponent();
}

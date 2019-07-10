package com.xohealth.club.di.module;

import android.content.Context;

import com.xohealth.club.di.component.ActivityComponent;
import com.xohealth.club.di.component.FragmentComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xulc on 2018/11/16.
 */
@Module(subcomponents = {ActivityComponent.class,FragmentComponent.class})
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideApplicationContext(){
        return context;
    }
}

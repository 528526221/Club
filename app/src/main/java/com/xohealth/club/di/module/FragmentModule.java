package com.xohealth.club.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xulc on 2018/11/16.
 */

@Module
public class FragmentModule {
    @Provides
    public String providePackageName(Context context){
        return context.getPackageName();
    }
}

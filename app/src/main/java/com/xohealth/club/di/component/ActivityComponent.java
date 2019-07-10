package com.xohealth.club.di.component;





import com.xohealth.club.di.module.ActivityModule;
import com.xohealth.club.di.scope.PerActivity;
import com.xohealth.club.ui.login.LoginActivity;
import com.xohealth.club.ui.bindmobile.BindMobileActivity;
import com.xohealth.club.ui.completinginfo.CompletingInfoActivity;
import com.xohealth.club.ui.main.MainActivity;
import com.xohealth.club.ui.register.RegisterActivity;
import com.xohealth.club.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Created by xulc on 2018/11/16.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity activity);
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(BindMobileActivity activity);
    void inject(CompletingInfoActivity activity);

    @Subcomponent.Builder
    interface Builder{
        ActivityComponent build();
        Builder activityModule(ActivityModule activityModule);
    }
}

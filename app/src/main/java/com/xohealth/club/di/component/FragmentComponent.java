package com.xohealth.club.di.component;




import com.xohealth.club.di.module.FragmentModule;
import com.xohealth.club.di.scope.PerFragment;
import com.xohealth.club.ui.discover.DiscoverFragment;
import com.xohealth.club.ui.home.HomeFragment;
import com.xohealth.club.ui.mall.MallFragment;
import com.xohealth.club.ui.message.MessageFragment;
import com.xohealth.club.ui.mine.MineFragment;

import dagger.Subcomponent;

/**
 * Created by xulc on 2018/11/16.
 */
@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(HomeFragment fragment);
    void inject(DiscoverFragment fragment);
    void inject(MallFragment fragment);
    void inject(MessageFragment fragment);
    void inject(MineFragment fragment);

    @Subcomponent.Builder
    interface Builder{
        FragmentComponent build();
        Builder fragmentModule(FragmentModule fragmentModule);
    }
}

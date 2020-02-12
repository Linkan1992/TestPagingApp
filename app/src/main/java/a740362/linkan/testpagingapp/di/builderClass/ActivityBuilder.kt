package a740362.linkan.testpagingapp.di.builderClass

import a740362.linkan.testpagingapp.ui.activity.main.MainActivity
import a740362.linkan.testpagingapp.ui.activity.main.MainActivityModule
import a740362.linkan.testpagingapp.ui.activity.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideMainActivity(): MainActivity


}
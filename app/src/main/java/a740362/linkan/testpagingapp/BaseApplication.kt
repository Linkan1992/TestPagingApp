package a740362.linkan.testpagingapp

import a740362.linkan.testpagingapp.di.component.AppComponent
import a740362.linkan.testpagingapp.di.component.DaggerAppComponent
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    private val appComponent : AppComponent by lazy { DaggerAppComponent.builder()
        .application(this)
        .build() }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent;
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        appComponent.inject(this);

    }

}
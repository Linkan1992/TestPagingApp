package a740362.linkan.testpagingapp.di.component

import android.app.Application
import a740362.linkan.testpagingapp.di.builderClass.ActivityBuilder
import a740362.linkan.testpagingapp.di.module.AppModule
import a740362.linkan.testpagingapp.di.module.NetworkModule
import a740362.linkan.testpagingapp.di.module.PersistenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        PersistenceModule::class,
        NetworkModule::class,
        ActivityBuilder::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent

    }

    override fun inject(instance: DaggerApplication)
}
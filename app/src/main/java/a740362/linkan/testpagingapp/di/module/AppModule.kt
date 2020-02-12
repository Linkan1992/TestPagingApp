package a740362.linkan.testpagingapp.di.module

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import a740362.linkan.testpagingapp.R
import a740362.linkan.testpagingapp.data.network.ApiHelper
import a740362.linkan.testpagingapp.data.network.AppApiHelper
import a740362.linkan.testpagingapp.data.persistence.db.AppDbHelper
import a740362.linkan.testpagingapp.data.persistence.db.DbHelper
import a740362.linkan.testpagingapp.data.persistence.pref.AppPrefHelper
import a740362.linkan.testpagingapp.data.persistence.pref.PrefHelper
import a740362.linkan.testpagingapp.di.annotation.CoroutineScopeIO
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }


    @Provides
    @CoroutineScopeIO
    internal fun provideCoroutineScopeIO() : CoroutineScope = CoroutineScope(Dispatchers.IO)


    @Provides
    @Singleton
    internal fun provideDBHelper(appDbHelper: AppDbHelper) : DbHelper = appDbHelper


    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper) : ApiHelper = appApiHelper


    @Provides
    @Singleton
    internal fun providePrefHelper(appPrefHelper: AppPrefHelper) : PrefHelper = appPrefHelper


}
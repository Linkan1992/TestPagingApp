package a740362.linkan.testpagingapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import a740362.linkan.testpagingapp.data.persistence.dao.AppDatabase
import a740362.linkan.testpagingapp.data.persistence.dao.TrendingDao
import a740362.linkan.testpagingapp.di.annotation.DatabaseInfo
import a740362.linkan.testpagingapp.di.annotation.PreferenceInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule{

    @Provides
    @PreferenceInfo
    internal fun providePrefName() : String{
        return "TrendingEmailPref"
    }

    @Provides
    @DatabaseInfo
    internal fun provideDBName() : String{
        return "TrendingUser.db"
    }

    @Provides
    @Singleton
    internal fun providePreference(@PreferenceInfo prefName : String, application : Application) : SharedPreferences{
        return application.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    internal fun provideAppDatabase(application : Application, @DatabaseInfo dbName : String) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    internal fun provideTrendingDao(database : AppDatabase) : TrendingDao {
        return database.trendingDao()
    }


}
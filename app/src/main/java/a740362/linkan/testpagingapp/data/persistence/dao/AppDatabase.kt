package a740362.linkan.testpagingapp.data.persistence.dao

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun trendingDao() : TrendingDao

}
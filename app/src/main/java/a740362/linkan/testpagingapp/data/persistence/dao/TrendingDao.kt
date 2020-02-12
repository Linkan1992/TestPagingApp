package a740362.linkan.testpagingapp.data.persistence.dao

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(trendingUser: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultipleListUser(userList: List<User>)

    @Delete
    fun deleteUser(trendingUser : User)

    @Query("DELETE FROM trending_user")
    fun deleteUserRecord()

    @Query("SELECT * FROM trending_user")
    fun loadAllUser(): List<User>

    @Query("SELECT * FROM trending_user  WHERE id >:userId ORDER BY id ASC LIMIT :limit")
    fun paginateUserData(userId : Int, limit : Int) : List<User>

}
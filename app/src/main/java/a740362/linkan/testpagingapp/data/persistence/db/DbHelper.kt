package a740362.linkan.testpagingapp.data.persistence.db

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import a740362.linkan.testpagingapp.data.network.base.Result
import androidx.lifecycle.LiveData

interface DbHelper {

    fun insertRepo(user : User)

    fun insertMultipleListUser(userList: List<User>)

   // fun loadAllUser(): LiveData<List<User>>

    fun paginateUser(id : Int, limit : Int)

    fun deleteUser(user : User)

    fun deleteUserRecord(userList: List<User>)

    fun getDbLiveData() : LiveData<Result<TrendingEmailUser>>

}
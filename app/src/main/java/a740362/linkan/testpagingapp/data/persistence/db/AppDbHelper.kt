package a740362.linkan.testpagingapp.data.persistence.db

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.data.persistence.dao.AppDatabase
import a740362.linkan.testpagingapp.di.annotation.CoroutineScopeIO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppDbHelper @Inject
constructor(private val appDatabase: AppDatabase, @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope) :
    DbHelper {

    private val trendingUserLiveData: MutableLiveData<Result<TrendingEmailUser>> by lazy { MutableLiveData<Result<TrendingEmailUser>>() }

    override fun insertRepo(user: User) {

    }


    override fun insertMultipleListUser(userList: List<User>) {
        ioCoroutineScope.launch {
            appDatabase.trendingDao().insertMultipleListUser(userList = userList)
        }
    }


    override fun paginateUser(id: Int, limit: Int) {
        ioCoroutineScope.launch {
            val isEmpty = appDatabase.trendingDao().paginateUserData(id, limit).isEmpty()

            if (isEmpty) {
                trendingUserLiveData.postValue(Result.Error("No Data Available"))
            } else
                trendingUserLiveData.postValue(
                    Result.Success(
                        data = TrendingEmailUser(dataList = appDatabase.trendingDao().paginateUserData(id, limit)),
                        isNetworkCall = false
                    )
                )
        }
    }


    override fun deleteUser(user: User) {

    }


    override fun deleteUserRecord(userList: List<User>) {
        ioCoroutineScope.launch {
            appDatabase.trendingDao().deleteUserRecord()
            if (appDatabase.trendingDao().loadAllUser().isEmpty())
                appDatabase.trendingDao().insertMultipleListUser(userList = userList)
        }
    }

    override fun getDbLiveData(): LiveData<Result<TrendingEmailUser>> {
        return trendingUserLiveData
    }


}
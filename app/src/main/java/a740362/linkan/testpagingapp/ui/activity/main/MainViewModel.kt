package a740362.linkan.testpagingapp.ui.activity.main

import androidx.lifecycle.LiveData
import a740362.linkan.testpagingapp.base.BaseViewModel
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.User
import a740362.linkan.testpagingapp.data.network.ApiHelper
import a740362.linkan.testpagingapp.data.persistence.db.DbHelper
import a740362.linkan.testpagingapp.data.persistence.pref.PrefHelper
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.util.AppConstants
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {

    private var hasMoreAvailableData = false

    var syncDbCache = false

    fun getMoreData(): Boolean {
        return hasMoreAvailableData
    }

    private val userObservableList = ObservableArrayList<User>()

    //  private val trendingUserLiveData: LiveData<Result<TrendingEmailUser>> = apiHelper.getUserLiveData()


    val mTrendingUserLiveData: LiveData<Result<TrendingEmailUser>>
        get() = trendingUserLiveData


    private val trendingUserLiveData: MediatorLiveData<Result<TrendingEmailUser>> = MediatorLiveData()


    init {

        val serverSource = apiHelper.getUserLiveData()
        trendingUserLiveData.addSource(serverSource, Observer {
            trendingUserLiveData.postValue(it)
        })

        val dbSource = dbHelper.getDbLiveData()
        trendingUserLiveData.addSource(dbSource, Observer {
            trendingUserLiveData.postValue(it)
        })

    }


    fun fetchDataFromCacheOrServer(
        isOnline: Boolean = false,
        isRefreshed: Boolean = false,
        page_num: Int = 1,
        per_page: Int = AppConstants.ITEM_COUNT_PER_PAGE
    ) {
        if (isRefreshed) {
            apiHelper.fetchTrendingUser(isRefreshed, page_num, per_page)
            return
        }

        if (isOnline)
            apiHelper.fetchTrendingUser(isRefreshed, page_num, per_page)
        else
            paginateFromCache(offsetId = 0)
    }

    fun paginateFromCacheOrServer(
        isOnline: Boolean = false,
        offsetId: Int,
        page_num: Int = 1,
        per_page: Int = AppConstants.ITEM_COUNT_PER_PAGE
    ) {
        if (isOnline)
            apiHelper.serverPagination(page_num, per_page)
        else
            paginateFromCache(offsetId = offsetId)
    }


    fun paginateFromCache(
        offsetId: Int = 0,
        per_page: Int = AppConstants.ITEM_COUNT_PER_PAGE
    ) {
        dbHelper.paginateUser(offsetId, per_page)
    }


    // pull to refresh
    val pullToRefreshListener: SwipeRefreshLayout.OnRefreshListener
        get() = SwipeRefreshLayout.OnRefreshListener { fetchDataFromCacheOrServer(isRefreshed = true) }


    val mUserObservableList: ObservableArrayList<User>
        get() = userObservableList


    fun setUserList(userList: List<User>) {

        hasMoreAvailableData = userList.isNotEmpty()

        userObservableList.clear()
        userObservableList.addAll(userList)
    }


    fun performDbSyncing(userList: List<User>) {

        if (!syncDbCache) {
            // Deleting all previous cache
            dbHelper.deleteUserRecord(userList)
        }

        if (syncDbCache) {
            // caching to DB
            dbHelper.insertMultipleListUser(userList)
        }

        syncDbCache = true

    }


}
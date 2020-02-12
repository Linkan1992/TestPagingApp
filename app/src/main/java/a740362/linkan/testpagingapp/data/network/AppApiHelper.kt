package a740362.linkan.testpagingapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.network.base.BaseRepository
import a740362.linkan.testpagingapp.data.network.base.Result
import a740362.linkan.testpagingapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class AppApiHelper @Inject
constructor(
    private val apiService: ApiService,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : BaseRepository(), ApiHelper {

    private val trendingUserLiveData: MutableLiveData<Result<TrendingEmailUser>> by lazy { MutableLiveData<Result<TrendingEmailUser>>() }


    override fun fetchTrendingUser(isRefreshed: Boolean, page_num: Int, per_page: Int) {

        ioCoroutineScope.launch {

            trendingUserLiveData.postValue(Result.Loading(isRefreshed = isRefreshed))

            val responseList: Result<TrendingEmailUser> = makeApiCall(
                call = { apiService.fetchTrendingUser(page_num, per_page).await()}
            )

            trendingUserLiveData.postValue(responseList)

        }

    }

    override fun getUserLiveData(): LiveData<Result<TrendingEmailUser>> {
        return trendingUserLiveData
    }


    override fun serverPagination(page_num: Int, per_page: Int) {

        ioCoroutineScope.launch {

            val responseList: Result<TrendingEmailUser> = makeApiCall(
                call = { apiService.fetchTrendingUser(page_num, per_page).await()}
            )

            trendingUserLiveData.postValue(responseList)

        }
    }


}
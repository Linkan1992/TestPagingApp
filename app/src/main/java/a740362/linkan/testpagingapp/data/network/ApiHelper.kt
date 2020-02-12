package a740362.linkan.testpagingapp.data.network

import androidx.lifecycle.LiveData
import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import a740362.linkan.testpagingapp.data.network.base.Result

interface ApiHelper{

    fun fetchTrendingUser(isRefreshed : Boolean, page_num : Int, per_page : Int)

    fun getUserLiveData() : LiveData<Result<TrendingEmailUser>>

    fun serverPagination(page_num : Int, per_page : Int)

}
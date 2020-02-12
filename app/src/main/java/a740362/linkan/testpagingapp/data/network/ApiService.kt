package a740362.linkan.testpagingapp.data.network

import a740362.linkan.testpagingapp.data.entity.api.trendingEmail.TrendingEmailUser
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("users")
    fun fetchTrendingUser(@Query("page") page : Int,
                          @Query("per_page") per_page : Int) : Deferred<Response<TrendingEmailUser>>

}
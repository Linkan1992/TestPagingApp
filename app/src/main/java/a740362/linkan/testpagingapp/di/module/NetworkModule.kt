package a740362.linkan.testpagingapp.di.module


import a740362.linkan.testpagingapp.BuildConfig
import a740362.linkan.testpagingapp.data.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    internal fun provideApiService( retrofit: Retrofit) : ApiService = createService(retrofit, ApiService::class.java)


    private fun<T> createService( retrofit: Retrofit, clazz : Class<T>) : T =  retrofit.create(clazz)


    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient,  gsonConverter : GsonConverterFactory): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }



    @Provides
    @Singleton
    internal fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor)  : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()


    @Provides
    @Singleton
    internal fun provideLoggingInt() : HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }
    }

    @Provides
    @Singleton
    internal fun provideHeaderInt() : Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("key", "value")
                .build()
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    internal fun provideConverter(gson : Gson) : GsonConverterFactory = GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    internal fun provideGson() : Gson = GsonBuilder().create()


}
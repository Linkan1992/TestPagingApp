package a740362.linkan.testpagingapp.data.network.base

import java.lang.Exception

sealed class Result<out T : Any> {

    data class Paging(val isPaging : Boolean = true) : Result<Nothing>()

    data class Loading(val isRefreshed : Boolean = false) : Result<Nothing>()

    data class Success<out T : Any>(val data : T, val isNetworkCall : Boolean = true) : Result<T>()

    data class Error(val message : String?) : Result<Nothing>()

    data class Retry(val isRetry : Boolean = true) : Result<Nothing>()

}
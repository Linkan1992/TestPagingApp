package a740362.linkan.testpagingapp.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import a740362.linkan.testpagingapp.data.network.base.Result

abstract class BaseViewModel : ViewModel() {

    protected val loading = ObservableBoolean(false);

    protected val refreshLoader = ObservableBoolean(false);


    /**
     * Method Return value as Actual Data
     */
    protected fun <T : Any> applyDataTransformation(apiLiveData: LiveData<Result<T>>): LiveData<T> {

        return Transformations.map(apiLiveData) { createData(result = it) }
    }


    private fun <T : Any> createData(result: Result<T>): T? {

        var data: T? = null

        when (result) {
            is Result.Loading -> {
                if (result.isRefreshed) setRefreshed(true) else setLoading(true)
            }
            is Result.Success -> {
                data = result.data
                setRefreshed(false)
                setLoading(false)
            }
            is Result.Error -> {
                setRefreshed(false)
                setLoading(false)
            }
        }

        return data;
    }


    /**
     * Method Return value as Result Wrapper
     */
    protected fun <T : Any> applyResultTransformation(apiLiveData: LiveData<Result<T>>): LiveData<Result<T>> {

        return Transformations.map(apiLiveData) { createResultData(result = it) }
    }

    fun <T : Any> createResultData(result: Result<T>): Result<T> {

        var data: Result<T> = result

        when (result) {
            is Result.Loading -> {
                if (result.isRefreshed) setRefreshed(true) else setLoading(true)
            }
            is Result.Success -> {
                data = Result.Success(result.data)
                setRefreshed(false)
                setLoading(false)
            }
            is Result.Error -> {
                setRefreshed(false)
                setLoading(false)
            }
        }

        return data;
    }


    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    fun setRefreshed(isRefreshed: Boolean) {
        refreshLoader.set(isRefreshed)
    }

    fun getRefreshLoading(): ObservableBoolean {
        return refreshLoader;
    }

    fun getAppLoading(): ObservableBoolean {
        return loading;
    }

}
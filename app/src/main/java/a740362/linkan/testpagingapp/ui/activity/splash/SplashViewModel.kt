package a740362.linkan.testpagingapp.ui.activity.splash

import a740362.linkan.testpagingapp.base.BaseViewModel
import a740362.linkan.testpagingapp.data.network.ApiHelper
import a740362.linkan.testpagingapp.data.persistence.pref.PrefHelper
import a740362.linkan.testpagingapp.util.AppConstants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import a740362.linkan.testpagingapp.data.network.base.Result

class SplashViewModel(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val ioCoroutineScope: CoroutineScope
) : BaseViewModel() {


    private val statusLiveData: MutableLiveData<Result<String>> by lazy { MutableLiveData<Result<String>>() }

    val mStatusLiveData: LiveData<Result<String>>
        get() = statusLiveData

    init {
        ioCoroutineScope.launch {

            delay(AppConstants.SPLASH_TIME_IN_MILLIS)

                statusLiveData.postValue(Result.Success("Success"))

        }
    }


}